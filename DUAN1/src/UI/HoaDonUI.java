/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import DAO.CTHoaDonDAO;
import DAO.CTSachDAO;
import DAO.GiamGiaDAO;
import DAO.HDKhachHangDAO;
import DAO.HoaDonDAO;
import DAO.KhachHangDAO;
import DAO.SachDAO;
import DAO.TuiDiemDAO;
import DAO.UserDAO;
import HELP.ImgIO;
import HelpUI.JTabbedPaneHD;
import MODEL.CTHoaDonModel;
import MODEL.CTSachModel;
import MODEL.HDKhachHangModel;
import MODEL.HoaDonModel;
import MODEL.KhachHangModel;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class HoaDonUI extends javax.swing.JPanel {

    private Webcam webcam;
    private WebcamPanel panel;
    private HoaDonDAO hDao = new HoaDonDAO();
    private UserDAO uDao = new UserDAO();
    private CTSachDAO ctsdao = new CTSachDAO();
    private SachDAO sDAO = new SachDAO();
    private CTHoaDonDAO cthdDao = new CTHoaDonDAO();
    private GiamGiaDAO ggDAO = new GiamGiaDAO();
    private final JTabbedPaneHD TabbedModel;
    private TuiDiemDAO tdDao = new TuiDiemDAO();
    private List<KhachHangModel> lstKH = new ArrayList<>();
    private int diem = 0;

    /**
     * Creates new form HoaDonUI
     */
    public HoaDonUI() {
        initComponents();
        this.TabbedModel = new JTabbedPaneHD(menuSP);
        init();
        camera();
        pnHD.add(TabbedModel);
        pnKH.setVisible(false);
    }

    public void camera() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Dimension size = WebcamResolution.QVGA.getSize();
                    webcam = Webcam.getWebcams().get(0);
                    webcam.setViewSize(size);
                    panel = new WebcamPanel(webcam);
                    panel.setSize(pnWebCam.getSize());
                    pnWebCam.add(panel);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Result result = null;
                    BufferedImage image = null;
                    if (webcam.isOpen()) {
                        image = webcam.getImage();
                    }
                    if (image == null) {
                        continue;
                    }
                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    try {
                        result = new MultiFormatReader().decode(bitmap);
                    } catch (NotFoundException e) {
                        lbTB.setText("Không có kết quả");
                        continue;
                    }
                    if (result != null) {
                        lbTB.setText(result.getText());
                        CTSachModel cts = ctsdao.findById(result.getText());
                        if (cts == null) {
                            continue;
                        }
                        try {
                            ThongTinSach tt = new ThongTinSach(null, true, cts);
                            tt.setVisible(true);
                            fillTable(tt.getCTSach());
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }).start();
    }

    public void sach() {
        try {
            List<CTSachModel> s = ctsdao.findByKeyWord("%" + jTextField1.getText() + "%");
            if (s == null) {
                return;
            }
            int index = 0;
            pnSP.setSize(s.size() * 160, 160);
            pnSP.removeAll();
            pnSP.setLayout(new GridLayout(1, s.size()));
            for (int i = 0; i < s.size(); i++) {
                CTSachModel smod;
                try {
                    smod = s.get(index);
                    int slSP = tinhSL(smod.getId(), smod.getSoLuong());
                    if (slSP < 1) {
                        continue;
                    }
                    smod.setSoLuong(slSP);
                    SanPham pn = new SanPham(smod);
                    pn.lbAdd.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            int sl = tinhSL(smod.getId(), ctsdao.findById(smod.getId()).getSoLuong());
                            smod.setSoLuong(sl);
                            ThongTinSach tt = new ThongTinSach(null, true, smod);
                            tt.setVisible(true);
                            if (tt.getCTSach() == null) {
                                return;
                            }
                            sl = sl - tt.getCTSach().getSoLuong();
                            pn.getLBSL().setText("SL:" + (sl));
                            fillTable(tt.getCTSach());
                            if (sl < 1) {
                                pnSP.add(new JPanel());
                                pnSP.remove(pn);
                                repaint();
                                validate();
                                return;
                            }
                        }
                    });
                    pn.setSize((int) pnSP.getPreferredSize().getWidth() / 4, 0);
                    pnSP.add(pn);
                    index++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            for (int i = s.size(); i < 3; i++) {
                pnSP.add(new JPanel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int tinhSL(String id_cts, int sl, int... indexTableRow) {
        try {
            int x = TabbedModel.getPaneCount() - 1;
            for (int i = 0; i < x; i++) {
                List<String> rowCount = TabbedModel.getIdSanphamAt(i);
                if (rowCount.size() == 0) {
                    return sl;
                }
                int j = 0;
                for (String a : rowCount) {
                    if (id_cts.equals(a)) {
                        if (indexTableRow.length > 0) {
                            if (indexTableRow[0] == i && indexTableRow[1] == j) {
                                continue;
                            }
                        }
                        sl = sl - Integer.valueOf(TabbedModel.getTableAt(i).getValueAt(j, 2) + "");
                    }
                    j++;
                }
            }
            return sl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void fillTable(CTSachModel model) {
        JTable tb = TabbedModel.getSelectedTable();
        List<String> lst = TabbedModel.getIdSanpham();
        try {
            if (model == null) {
                return;
            }
            int index = 0;
            for (String x : lst) {
                if (model.getId().equals(x)) {
                    tb.setValueAt(Integer.valueOf(tb.getValueAt(index, 2) + "") + model.getSoLuong(), index, 2);
                    return;
                }
                index++;
            }
            DefaultTableModel modTB = (DefaultTableModel) tb.getModel();
            int gg = 0;
            try {
                gg = ggDAO.findByIdCTSSach(model.getId());
            } catch (Exception e) {
            }
            modTB.addRow(new Object[]{
                sDAO.findById(model.getId_sach()).getTenSach() + "-" + model.getTap(), fomartNumber((int) model.getGia()), model.getSoLuong(), gg, fomartNumber((int) (model.getSoLuong() * model.getGia() * (100 - gg) / 100))
            });
            tb.addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusGained(java.awt.event.FocusEvent evt) {
                    eventTB(evt);
                }
            });
            lst.add(model.getId());
            TabbedModel.lstTB.get(TabbedModel.getTabbedPane().getSelectedIndex()).add(1, lst);
        } catch (Exception e) {
        } finally {
            tinhTienTheoSL(tb);
        }
    }

    public void eventTB(java.awt.event.FocusEvent evt) {
        try {
            tinhTienTheoSL(TabbedModel.getSelectedTable());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tinhTienTheoSL(JTable tb) {
        try {
            tinhTienThanhToan();
            DefaultTableModel modTB = (DefaultTableModel) tb.getModel();
            int row = tb.getRowCount();
            for (int index = 0; index < row; index++) {
                int gia = reFomartNumber(modTB.getValueAt(index, 1) + "");
                int sl = 1;
                try {
                    sl = Integer.valueOf(tb.getValueAt(index, 2) + "");
                } catch (Exception e) {
                    JOptionPane.showConfirmDialog(this, "Số lượng là số nguyên");
                }
                double giamGia = Double.valueOf(tb.getValueAt(index, 3) + "");
                tb.setValueAt(fomartNumber((int) (gia * (100 - giamGia) * sl / 100)), index, 4);
            }
            tinhTienThanhToan();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void insert() {
        try {
            HoaDonModel hd = new HoaDonModel();
            int tt = reFomartNumber(lbPT.getText());
            hd.setThanhTien(tt);
            int indexTab = TabbedModel.getIndexSelected();
            JTable tb = (JTable) TabbedModel.lstTB.get(indexTab).get(0);
            List<String> lst = (List<String>) TabbedModel.lstTB.get(indexTab).get(1);
            DefaultTableModel model = (DefaultTableModel) tb.getModel();
            if (model == null) {
                return;
            }
            int a = hDao.insert(hd);
            int idMax = cthdDao.selectId();
            if (a > 0) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    cthdDao.insert(new CTHoaDonModel(String.valueOf(lst.get(i)),
                            Integer.valueOf(model.getValueAt(i, 2) + ""),
                            idMax,
                            reFomartNumber(model.getValueAt(i, 1) + ""),
                            Double.valueOf(model.getValueAt(i, 3) + "")));
                }
                JOptionPane.showMessageDialog(this, "Tạo thành công!");
                updateCTSach(TabbedModel.getIndexSelected());
            }
            KhachHangModel kh = lstKH.get(indexTab);
            if (kh != null) {
                HDKhachHangDAO hdkhDao = new HDKhachHangDAO();
                KhachHangDAO khDao= new KhachHangDAO();
                hdkhDao.insert(new HDKhachHangModel(idMax, kh.getId()));
                kh.setDiemHT(kh.getDiemHT() - Integer.valueOf( snDiemDoi.getValue()+""));
                khDao.updateDiem(kh);
                pnKH.setVisible(false);
                lbGG.setText("0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int reFomartNumber(String money) {
        String gia = money + "";
        gia = gia.replace(",", "");
        return Integer.valueOf(gia);
    }

    public void init() {
        sach();
        itemTab.setText("X Bỏ hóa đơn đang xem");
        lbTK.setIcon(ImgIO.readIcon("TimKiem.png", 20, 20));
        lbNothing.setIcon(ImgIO.readIcon("nothing.jpg", 150, 150));
        lbThanhToan.setIcon(ImgIO.readIcon("thanhToan.png", 40, 40));
        TabbedModel.getTabbedPane().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tinhTienThanhToan();
                int index = TabbedModel.getIndexSelected();
                if (lstKH.size() < TabbedModel.getPaneCount() - 1) {
                    lstKH.add(null);
                    return;
                }
                if (lstKH.get(index) == null) {
                    pnKH.setVisible(false);
                    lbGG.setText("0");
                    return;
                }
                KhachHangModel kh = lstKH.get(index);
                lbDiem.setText(kh.getDiemHT() + "");
                snDiemDoi.setValue(kh.getId_td());
                pnKH.setVisible(true);
            }
        });
        lstKH.add(null);
        diem = (int) tdDao.select().get(0).getTiendoi();
    }

    public void clearForm() {
        lbTH.setText("0");
        txtTienDua.setText("0");
        lbPT.setText("0");
        lbTL.setText("0");
    }

    private void updateCTSach(int index) {
        List<String> lst = TabbedModel.getIdSanpham();
        for (String x : lst) {
            ctsdao.updateSL(new CTSachModel(x, tinhSL(x, ctsdao.findById(x).getSoLuong())));
        }
    }

    public boolean check() {
        String kd = txtTienDua.getText();
        if (kd.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập Tiền khách đưa!");
            return true;
        }
        try {

            if (TabbedModel.getSelectedTable().getRowCount() < 1) {
                JOptionPane.showMessageDialog(this, "Hóa đơn chưa có gì!");
                return true;
            }

            if (Integer.valueOf(reFomartNumber(txtTienDua.getText())) < reFomartNumber(lbPT.getText())) {
                JOptionPane.showMessageDialog(this, "Thiếu tiền!");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    private void setKhachHang(KhachHangModel model) {
        lbDiem.setText(model.getDiemHT() + "");
        lbTenKH.setText(model.getTenKH());
    }

    private void tinhTienThanhToan() {
        try {
            JTable tb = TabbedModel.getSelectedTable();
            DefaultTableModel model = (DefaultTableModel) tb.getModel();
            int tong = 0;
            for (int i = 0; i < model.getRowCount(); i++) {
                int a = reFomartNumber(model.getValueAt(i, 4) + "");
                tong = tong + a;
            }
            lbTH.setName(tong + "");
            lbTH.setText(fomartNumber(tong));
            int giamGia = reFomartNumber(lbGG.getText());
            lbPT.setText(fomartNumber(tong - giamGia));
            lbPT.setName((tong - giamGia) + "");
            traLai();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String fomartNumber(int tong) {
        String th = tong + "";
        String fomart = "";
        int dodai = th.length();
        for (int i = 0; i <= dodai / 3; i++) {
            int vtDau = dodai - (i + 1) * 3 < 0 ? 0 : dodai - (i + 1) * 3;
            int vtKT = dodai - 3 * i;
            String phay = ",";
            if (vtDau == 0) {
                phay = "";
            }
            fomart = phay + th.substring(vtDau, vtKT) + fomart;
        }
        return fomart  ;
    }

    private void traLai() {
        try {
            int tkd = reFomartNumber(txtTienDua.getText());
            int pt = Integer.valueOf(lbPT.getName());
            lbTL.setText((tkd - pt) > 0 ? fomartNumber(tkd - pt) + "" : "0");
        } catch (Exception e) {
            lbTL.setText("Hãy nhập số!");
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        itemTab = new javax.swing.JMenuItem();
        menuSP = new javax.swing.JPopupMenu();
        itemSP = new javax.swing.JMenuItem();
        itemSP2 = new javax.swing.JMenuItem();
        jPanel9 = new javax.swing.JPanel();
        lbNothing = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        pnHD = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pnSP = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        lbTK = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        pnWebCam = new javax.swing.JPanel();
        lbTB = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lbThanhToan = new javax.swing.JLabel();
        lbTheTV = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        pnKH = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lbGG = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        lbTenKH = new javax.swing.JLabel();
        lbDiem = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        snDiemDoi = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbPT = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTienDua = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        lbTL = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbTH = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbTheTV1 = new javax.swing.JLabel();

        jPopupMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPopupMenu1MouseClicked(evt);
            }
        });

        itemTab.setText("jMenuItem1");
        itemTab.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                itemTabMouseClicked(evt);
            }
        });
        itemTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemTabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(itemTab);

        itemSP.setText("Sửa số lượng sách");
        itemSP.setToolTipText("");
        itemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSPActionPerformed(evt);
            }
        });
        menuSP.add(itemSP);

        itemSP2.setText("Bỏ dòng đang chọn");
        itemSP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemSP2ActionPerformed(evt);
            }
        });
        menuSP.add(itemSP2);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 1, true));

        lbNothing.setText("sjja");
        lbNothing.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel9.add(lbNothing);

        setBackground(new java.awt.Color(0, 102, 51));
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setLayout(new java.awt.BorderLayout(5, 5));

        jPanel6.setBackground(new java.awt.Color(0, 102, 51));

        pnHD.setBackground(new java.awt.Color(255, 255, 255));
        pnHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnHDMouseClicked(evt);
            }
        });
        pnHD.setLayout(new java.awt.GridLayout(1, 0));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnHD, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnHD, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(0, 102, 51));

        pnSP.setBackground(new java.awt.Color(255, 255, 255));
        pnSP.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        pnSP.setAutoscrolls(true);
        pnSP.setLayout(new java.awt.GridLayout(1, 3));
        jScrollPane1.setViewportView(pnSP);

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));

        jPanel8.setBackground(new java.awt.Color(0, 102, 51));
        jPanel8.setOpaque(false);
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel8.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 380, 20));

        lbTK.setBackground(new java.awt.Color(0, 102, 51));
        lbTK.setForeground(new java.awt.Color(255, 255, 255));
        lbTK.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTK.setOpaque(true);
        lbTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbTKMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbTKMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbTKMouseExited(evt);
            }
        });
        jPanel8.add(lbTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 20));

        jPanel2.add(jPanel8);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 267, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(0, 35, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 237, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel3.setText("Bán hàng");
        jPanel3.add(jLabel3);

        add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(204, 255, 255));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        pnWebCam.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnWebCamLayout = new javax.swing.GroupLayout(pnWebCam);
        pnWebCam.setLayout(pnWebCamLayout);
        pnWebCamLayout.setHorizontalGroup(
            pnWebCamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 212, Short.MAX_VALUE)
        );
        pnWebCamLayout.setVerticalGroup(
            pnWebCamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 122, Short.MAX_VALUE)
        );

        lbTB.setText("Không xác định");

        lbThanhToan.setBackground(new java.awt.Color(0, 102, 51));
        lbThanhToan.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        lbThanhToan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbThanhToan.setText("Thanh toán");
        lbThanhToan.setOpaque(true);
        lbThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbThanhToanMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbThanhToanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbThanhToanMouseExited(evt);
            }
        });

        lbTheTV.setBackground(new java.awt.Color(0, 102, 51));
        lbTheTV.setForeground(new java.awt.Color(255, 255, 255));
        lbTheTV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTheTV.setText("Sử dụng thẻ thành viên");
        lbTheTV.setOpaque(true);
        lbTheTV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbTheTVMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbTheTVMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbTheTVMouseExited(evt);
            }
        });

        jPanel7.setBackground(new java.awt.Color(204, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        jLabel1.setText("Tiền khách đưa:");

        pnKH.setOpaque(false);

        jLabel4.setText("Giảm giá(VND):");

        lbGG.setText("0");

        jLabel6.setText("Tổng điểm:");

        lbTenKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTenKH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTenKH.setText("Tên khách hàng");

        lbDiem.setText("diểm");

        jLabel10.setText("Điểm đổi");

        snDiemDoi.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        snDiemDoi.setOpaque(false);
        snDiemDoi.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                snDiemDoiStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("x");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
        });

        jLabel5.setText("VND");

        javax.swing.GroupLayout pnKHLayout = new javax.swing.GroupLayout(pnKH);
        pnKH.setLayout(pnKHLayout);
        pnKHLayout.setHorizontalGroup(
            pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(pnKHLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnKHLayout.createSequentialGroup()
                        .addGroup(pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pnKHLayout.createSequentialGroup()
                                .addComponent(lbGG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel5))
                            .addComponent(snDiemDoi, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(lbDiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnKHLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lbTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        pnKHLayout.setVerticalGroup(
            pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnKHLayout.createSequentialGroup()
                .addGroup(pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnKHLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lbDiem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(snDiemDoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(pnKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbGG)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lbPT.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbPT.setText("0");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Phải trả: ");

        txtTienDua.setBackground(new java.awt.Color(204, 255, 255));
        txtTienDua.setText("0");
        txtTienDua.setActionCommand("<Not Set>");
        txtTienDua.setBorder(null);
        txtTienDua.setOpaque(false);
        txtTienDua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTienDuaActionPerformed(evt);
            }
        });
        txtTienDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienDuaKeyReleased(evt);
            }
        });

        jLabel8.setText("Trả lại:");

        lbTL.setText("0");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Tiền hàng: ");

        lbTH.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbTH.setText("0");

        jLabel11.setText("VND");

        jLabel12.setText("VND");

        jLabel13.setText("VND");

        jLabel14.setText("VND");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pnKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(lbTL, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lbPT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTienDua, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbTH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(50, 50, 50))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(pnKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtTienDua, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel12))
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11)
                        .addComponent(lbTH, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel13)
                    .addComponent(lbPT, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(lbTL))
                    .addComponent(jLabel14))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        lbTheTV1.setBackground(new java.awt.Color(0, 102, 51));
        lbTheTV1.setForeground(new java.awt.Color(255, 255, 255));
        lbTheTV1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTheTV1.setText("Đăng kí thành viên");
        lbTheTV1.setOpaque(true);
        lbTheTV1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbTheTV1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbTheTV1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbTheTV1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(lbTheTV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTheTV1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lbThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(33, 33, 33)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbTB, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pnWebCam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lbTB)
                .addGap(4, 4, 4)
                .addComponent(pnWebCam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTheTV1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbTheTV, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        add(jPanel4, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void jPopupMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPopupMenu1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jPopupMenu1MouseClicked

    private void itemTabMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemTabMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_itemTabMouseClicked

    private void itemTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemTabActionPerformed

    }//GEN-LAST:event_itemTabActionPerformed

    private void lbThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThanhToanMouseClicked
        // TODO add your handling code here:
        if (check()) {
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Xác nhận thanh toán " + TabbedModel.getTabbedPane().getTitleAt(TabbedModel.getTabbedPane().getSelectedIndex()), "", JOptionPane.YES_NO_OPTION) != 0) {
            return;
        }
        try {
            insert();
            clearForm();

            TabbedModel.removeTab(TabbedModel.getIndexSelected());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_lbThanhToanMouseClicked

    private void lbTheTVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTheTVMouseClicked
        // TODO add your handling code here:
        QuetKhachHang qkh = new QuetKhachHang(null, true);
        qkh.getLbOK().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pnKH.setVisible(true);
                KhachHangModel kh = qkh.getKhachHang();
                lbTenKH.setText(kh.getTenKH());
                lbDiem.setText(kh.getDiemHT() + "");
                snDiemDoi.setModel(new javax.swing.SpinnerNumberModel(0, 0, kh.getDiemHT(), 1));
                qkh.dispose();
                qkh.getWebCam().close();
                lstKH.set(TabbedModel.getIndexSelected(), kh);
            }
        });
        qkh.setVisible(true);
    }//GEN-LAST:event_lbTheTVMouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        sach();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void itemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSPActionPerformed
        // TODO add your handling code here:
        try {
            int indexTable = TabbedModel.getIndexSelected();
            JTable tb = TabbedModel.getSelectedTable();
            int indexRow = tb.getSelectedRow();
            CTSachModel cts = ctsdao.findById(TabbedModel.getIdSanphamAt(indexTable).get(indexRow));
            cts.setSoLuong(tinhSL(cts.getId(), cts.getSoLuong(), indexTable, indexRow));
            ThongTinSach tt = new ThongTinSach(null, true, cts);
            tt.setVisible(true);
            if(tt.getCTSach()==null) return;
            tb.setValueAt(tt.getCTSach().getSoLuong(), indexRow, 2);
            sach();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }//GEN-LAST:event_itemSPActionPerformed

    private void lbThanhToanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThanhToanMouseEntered
        // TODO add your handling code here:
        hieuUngButtonEN(lbThanhToan);
    }//GEN-LAST:event_lbThanhToanMouseEntered

    private void lbThanhToanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbThanhToanMouseExited
        // TODO add your handling code here:
        hieuUngButtonEX(lbThanhToan);
    }//GEN-LAST:event_lbThanhToanMouseExited

    private void lbTheTVMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTheTVMouseEntered
        // TODO add your handling code here:
        hieuUngButtonEN(lbTheTV);
    }//GEN-LAST:event_lbTheTVMouseEntered

    private void lbTheTVMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTheTVMouseExited
        // TODO add your handling code here:
        hieuUngButtonEX(lbTheTV);
    }//GEN-LAST:event_lbTheTVMouseExited

    private void itemSP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemSP2ActionPerformed
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(pnHD, "Xác nhận xóa dòng đang chọn?", "", JOptionPane.YES_NO_OPTION) != 0) {
            return;
        }
        JTable tb = TabbedModel.getSelectedTable();
        try {
            DefaultTableModel model = (DefaultTableModel) tb.getModel();
            TabbedModel.getIdSanpham().remove(tb.getSelectedRow());
            model.removeRow(tb.getSelectedRow());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pnHD, "Chọn dòng cần xóa!");
        }
    }//GEN-LAST:event_itemSP2ActionPerformed

    private void pnHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnHDMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pnHDMouseClicked

    private void lbTKMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTKMouseExited
        // TODO add your handling code here:
        hieuUngButtonEX(lbTK);
    }//GEN-LAST:event_lbTKMouseExited

    private void lbTKMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTKMouseEntered
        // TODO add your handling code here:
        hieuUngButtonEN(lbTK);
    }//GEN-LAST:event_lbTKMouseEntered

    private void lbTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTKMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbTKMouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
        sach();
    }//GEN-LAST:event_jTextField1KeyReleased

    private void txtTienDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienDuaKeyReleased
        // TODO add your handling code here:
        try {
            int chuoi= reFomartNumber(txtTienDua.getText());
            txtTienDua.setText(fomartNumber(chuoi));
        } catch (Exception e) {
            e.printStackTrace();
        }
        traLai();
    }//GEN-LAST:event_txtTienDuaKeyReleased

    private void txtTienDuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTienDuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTienDuaActionPerformed

    private void snDiemDoiStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_snDiemDoiStateChanged
        // TODO add your handling code here:
        try {
            lbGG.setText(fomartNumber((int) snDiemDoi.getValue() * diem) + "");
            tinhTienThanhToan();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_snDiemDoiStateChanged

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        if (JOptionPane.showConfirmDialog(this, "Xác nhận bỏ thẻ khách hàng!") != 0) {
            return;
        }
        lstKH.set(TabbedModel.getIndexSelected(), null);
        pnKH.setVisible(false);
        lbGG.setName("0");
        tinhTienThanhToan();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        // TODO add your handling code here:
        hieuUngButtonEN(jLabel2);
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        // TODO add your handling code here:
        hieuUngButtonEX(jLabel2);
    }//GEN-LAST:event_jLabel2MouseExited

    private void lbTheTV1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTheTV1MouseClicked
        // TODO add your handling code here:
        new dkiThanhVien().setVisible(true);
    }//GEN-LAST:event_lbTheTV1MouseClicked

    private void lbTheTV1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTheTV1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lbTheTV1MouseEntered

    private void lbTheTV1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbTheTV1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lbTheTV1MouseExited

    public void hieuUngButtonEN(JLabel lb) {
        lb.setBackground(new Color(0, 153, 153));
        lb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    }

    public void hieuUngButtonEX(JLabel lb) {
        lb.setBackground(new Color(0, 102, 51));
        lb.setBorder(null);
    }

//    public void delete() {
//        try {
//            lstTB.remove(tabHD.getSelectedIndex());
//            tabHD.remove(tabHD.getSelectedIndex());
//            if (tabHD.getTabCount() < 1) {
//                createHD();
//            }
//        } catch (Exception e) {
//        }
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem itemSP;
    private javax.swing.JMenuItem itemSP2;
    private javax.swing.JMenuItem itemTab;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lbDiem;
    private javax.swing.JLabel lbGG;
    private javax.swing.JLabel lbNothing;
    private javax.swing.JLabel lbPT;
    private javax.swing.JLabel lbTB;
    private javax.swing.JLabel lbTH;
    private javax.swing.JLabel lbTK;
    private javax.swing.JLabel lbTL;
    private javax.swing.JLabel lbTenKH;
    private javax.swing.JLabel lbThanhToan;
    private javax.swing.JLabel lbTheTV;
    private javax.swing.JLabel lbTheTV1;
    private javax.swing.JPopupMenu menuSP;
    private javax.swing.JPanel pnHD;
    private javax.swing.JPanel pnKH;
    private javax.swing.JPanel pnSP;
    private javax.swing.JPanel pnWebCam;
    private javax.swing.JSpinner snDiemDoi;
    private javax.swing.JTextField txtTienDua;
    // End of variables declaration//GEN-END:variables
}
