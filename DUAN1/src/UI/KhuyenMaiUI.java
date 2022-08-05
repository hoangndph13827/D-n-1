/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import DAO.GiamGiaDAO;
import DAO.TheLoaiDAO;
import DAO.TheLoaiGiamGiaDAO;
import MODEL.GiamGiaModel;
import MODEL.TheLoaiGiamGiaModel;
import MODEL.TheLoaiModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class KhuyenMaiUI extends javax.swing.JPanel {

    TheLoaiDAO tlDao = new TheLoaiDAO();
    List<TheLoaiModel> lst;
    List<TheLoaiModel> lstTheLoaiDuocChon = new ArrayList<>();
    GiamGiaDAO ggDao = new GiamGiaDAO();
    TheLoaiGiamGiaDAO tlggDao = new TheLoaiGiamGiaDAO();
    List<GiamGiaModel> lstgg;
    List<GiamGiaModel> lstGGTB;

    /**
     * Creates new form KhuyenMaiUI
     */
    public KhuyenMaiUI() {
        initComponents();
       // pnApDungTL.setVisible(false);
        init();
        fillListTheLoai();
        fillTable();
        fillListSapDienRa();
    }

    private boolean check(){
            Date ngayBD = dcNgayBD.getDate();
            Date ngayKT = dcNgayKT.getDate();
            String ten = txtTen.getText();
            int gg = sdGiamGia.getValue();
            if(ngayBD==null){
                JOptionPane.showMessageDialog(this,"Chưa có ngày bắt đầu!");
                return true;
            }
            if(ngayKT==null){
                JOptionPane.showMessageDialog(this,"Chưa có ngày kết thúc!");
                return true;
            }
            if(ten.isEmpty()){
                JOptionPane.showMessageDialog(this,"Chưa có tên chương trình!");
                return true;
            }
            if(gg==0){
                JOptionPane.showMessageDialog(this,"Giảm giá đang bằng 0!");
                return true;
            }
            if(lstTheLoaiDuocChon.size()<1){
                JOptionPane.showMessageDialog(this,"Chưa có thể loại giảm giá!");
                return true;
            }
        return false;
    }
    private void init() {
        dcNgayBD.setMinSelectableDate(java.util.Calendar.getInstance().getTime());
    }

    private void clearForm(){
        btUpdate.setText("Insert");
        txtTen.setText("");
        dcNgayBD.setDate(null);
        dcNgayKT.setDate(null);
        lstTheLoaiDuocChon = new ArrayList<>();
        fillListAD();
    }

    public void fillListTheLoai() {
        String timKiem = txtTimKiem.getText();
        lst = tlDao.findByKyeWord("%" + timKiem + "%");
        String[] arr = new String[lst.size()];
        int index = 0;
        for (TheLoaiModel x : lst) {
            arr[index++] = x.getTenTL();
        }
        jLstTheLoai.setListData(arr);
    }

    public void fillListAD() {
        String[] arr = new String[lstTheLoaiDuocChon.size()];
        int index = 0;
        for (TheLoaiModel x : lstTheLoaiDuocChon) {
            arr[index++] = x.getTenTL();
        }
        JlstAD.setListData(arr);
    }

    private void insert() {
        try {
            Date ngayBD = dcNgayBD.getDate();
            Date ngayKT = dcNgayKT.getDate();
            String ten = txtTen.getText();
            int gg = sdGiamGia.getValue();
            int row = ggDao.insert(new GiamGiaModel(0, gg, ngayBD, ngayKT, ten, true));
            if (row < 1) {
                return;
            }
            int idMax = 0;
            try {
                idMax = ggDao.selectIdMax();
            } catch (Exception e) {
                return;
            }
            int row2 = 0;
            for (TheLoaiModel x : lstTheLoaiDuocChon) {
                row2 = row2 + tlggDao.insert(new TheLoaiGiamGiaModel(idMax, x.getId()));
            }
            if (row2 > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tb.getModel();
        model.setRowCount(0);
        lstGGTB = ggDao.selectDangApDung();
        for (GiamGiaModel x : lstGGTB) {
            model.addRow(new Object[]{
                x.getTenChuongTrnh(),
                x.getGiamGia(),
                x.getNgayApDung(), 
                x.getNgayKetThuc(),
                x.isTrangThai() ? "Đang hoạt động" : "Ngừng hoạt động"
            });
        }
        pnApDungTL.setVisible(false);
    }

    private void setForm() {
        btUpdate.setText("Update");
        int index = jListSapDienRa.getSelectedIndex();
        if (index < 0) {
            return;
        }
        GiamGiaModel gg = lstgg.get(index);
        txtTen.setText(gg.getTenChuongTrnh());
        dcNgayBD.setDate(gg.getNgayApDung());
        dcNgayKT.setDate(gg.getNgayKetThuc());
        sdGiamGia.setValue((int) gg.getGiamGia());
        lstTheLoaiDuocChon = theLoaiDuocGiamGia(gg.getId());
        fillListAD();
    }

    private void fillListSapDienRa() {
        lstgg = ggDao.selectSapDienRa();
        String[] arr = new String[lst.size()];
        int index = 0;
        for (GiamGiaModel x : lstgg) {
            arr[index++] = x.getNgayApDung() + " | " + x.getTenChuongTrnh();
        }
        jListSapDienRa.setListData(arr);
    }

    private List<TheLoaiModel> theLoaiDuocGiamGia(int id_gg) {
        List<TheLoaiModel> lst = new ArrayList<>();
        List<TheLoaiGiamGiaModel> lstTL_GG = tlggDao.findByIdGG(id_gg);
        for (TheLoaiGiamGiaModel x : lstTL_GG) {
            System.out.println(x.getId_TL());
            lst.add(tlDao.findByIdTL(x.getId_TL()));

        }
        return lst;
    }

    private void update() {
        try {
        Date ngayBD = dcNgayBD.getDate();
        Date ngayKT = dcNgayKT.getDate();
        String ten = txtTen.getText();
        GiamGiaModel gg= lstgg.get(jListSapDienRa.getSelectedIndex());
        gg.setTenChuongTrnh(txtTen.getText());
        gg.setGiamGia(sdGiamGia.getValue());
        gg.setNgayApDung(dcNgayBD.getDate());
        gg.setNgayKetThuc(dcNgayKT.getDate());
        int row = ggDao.update(gg);
        System.out.println(row);
        if (row < 1) {
            return;
        }
        int row2 = 0;
        tlggDao.delete(gg.getId());
        for (TheLoaiModel x : lstTheLoaiDuocChon) {
            row2 = tlggDao.insert(new TheLoaiGiamGiaModel(gg.getId(), x.getId()));
            System.out.println("r  "+ row2);
        }
        if (row2 > 0) {
            JOptionPane.showMessageDialog(this, "Update thành công");
        }
        } catch (Exception e) {
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

        puTable = new javax.swing.JPopupMenu();
        mnNgung = new javax.swing.JMenuItem();
        mnTiepTuc = new javax.swing.JMenuItem();
        mnNgungAll = new javax.swing.JMenuItem();
        mnTiepTucAll = new javax.swing.JMenuItem();
        jPanel4 = new javax.swing.JPanel();
        pnApDungTL = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jListCT = new javax.swing.JList<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        btHoatDong = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListSapDienRa = new javax.swing.JList<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tb = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLstTheLoai = new javax.swing.JList<>();
        txtTimKiem = new javax.swing.JTextField();
        lbGiamGia = new javax.swing.JLabel();
        btUpdate = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        dcNgayBD = new com.toedter.calendar.JDateChooser();
        jScrollPane4 = new javax.swing.JScrollPane();
        JlstAD = new javax.swing.JList<>();
        sdGiamGia = new javax.swing.JSlider();
        dcNgayKT = new com.toedter.calendar.JDateChooser();
        txtTen = new javax.swing.JTextField();

        mnNgung.setText("jMenuItem1");
        puTable.add(mnNgung);

        mnTiepTuc.setText("jMenuItem2");
        puTable.add(mnTiepTuc);

        mnNgungAll.setText("jMenuItem3");
        puTable.add(mnNgungAll);

        mnTiepTucAll.setText("jMenuItem4");
        puTable.add(mnTiepTucAll);

        setLayout(new java.awt.BorderLayout(5, 5));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("Áp dụng cho các thể loại ");

        jListCT.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane5.setViewportView(jListCT);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("x");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        btHoatDong.setText("Ngừng hoạt động");
        btHoatDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHoatDongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnApDungTLLayout = new javax.swing.GroupLayout(pnApDungTL);
        pnApDungTL.setLayout(pnApDungTLLayout);
        pnApDungTLLayout.setHorizontalGroup(
            pnApDungTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnApDungTLLayout.createSequentialGroup()
                .addGroup(pnApDungTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnApDungTLLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnApDungTLLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btHoatDong)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator1)
        );
        pnApDungTLLayout.setVerticalGroup(
            pnApDungTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnApDungTLLayout.createSequentialGroup()
                .addGroup(pnApDungTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnApDungTLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btHoatDong))
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Chương trình sắp diễn ra");

        jListSapDienRa.setVerifyInputWhenFocusTarget(false);
        jListSapDienRa.setVisibleRowCount(0);
        jListSapDienRa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListSapDienRaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jListSapDienRa);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3)
                .addGap(0, 0, 0))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnApDungTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pnApDungTL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(jPanel4, java.awt.BorderLayout.LINE_END);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Quản lý khuyến mãi");
        jPanel2.add(jLabel2);

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên chương trình", "Giảm giá", "Ngày bắt đầu", "Ngày kết thúc", "Trạng thái"
            }
        ));
        tb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tb);

        jLabel3.setText("Tên chương trình");

        jLabel4.setText("Ngày bắt đầu ");

        jLabel5.setText("Ngày kết thúc");

        jLabel7.setText("Thể loại áp dụng");

        jLstTheLoai.setPreferredSize(null);
        jLstTheLoai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLstTheLoaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jLstTheLoai);

        txtTimKiem.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyReleased(evt);
            }
        });

        lbGiamGia.setText("Mức giảm(0%)");

        btUpdate.setText("Thêm");
        btUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btUpdateActionPerformed(evt);
            }
        });

        jButton2.setText("Làm sạch");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel3.setOpaque(false);

        dcNgayBD.setDateFormatString("dd-MM-yyyy");
        dcNgayBD.setOpaque(false);
        dcNgayBD.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dcNgayBDPropertyChange(evt);
            }
        });

        JlstAD.setAlignmentX(1.0F);
        JlstAD.setAlignmentY(1.0F);
        JlstAD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JlstADMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(JlstAD);
        JlstAD.setSize(222, 25);

        sdGiamGia.setValue(0);
        sdGiamGia.setOpaque(false);
        sdGiamGia.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sdGiamGiaStateChanged(evt);
            }
        });

        dcNgayKT.setDateFormatString("dd-MM-yyyy");
        dcNgayKT.setOpaque(false);

        txtTen.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTen)
            .addComponent(dcNgayBD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(dcNgayKT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(sdGiamGia, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(dcNgayBD, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addGap(16, 16, 16)
                .addComponent(dcNgayKT, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sdGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGap(5, 5, 5))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lbGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(65, 65, 65)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiem)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lbGiamGia, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jScrollPane1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        fillListTheLoai();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void jLstTheLoaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLstTheLoaiMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            if (jLstTheLoai.getSelectedIndex() < 0) {
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "Xác nhận thêm " + jLstTheLoai.getSelectedValue()) != 0) {
                return;
            }
            lstTheLoaiDuocChon.add(lst.get(jLstTheLoai.getSelectedIndex()));
            fillListAD();
        }
    }//GEN-LAST:event_jLstTheLoaiMouseClicked

    private void JlstADMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JlstADMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            if (JlstAD.getSelectedIndex() < 0) {
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "Xác nhận bỏ " + JlstAD.getSelectedValue()) != 0) {
                return;
            }
            lstTheLoaiDuocChon.remove(JlstAD.getSelectedIndex());
            fillListAD();
        }
    }//GEN-LAST:event_JlstADMouseClicked

    private void sdGiamGiaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sdGiamGiaStateChanged
        // TODO add your handling code here:
        lbGiamGia.setText("Mức giảm(" + sdGiamGia.getValue() + "%)");
    }//GEN-LAST:event_sdGiamGiaStateChanged

    private void btUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdateActionPerformed
        // TODO add your handling code here:
        if(check()) return;
        try {
            if (btUpdate.getText().equals("Update")) {
                update();
                System.out.println("Update");
                clearForm();
                
            } else {
                insert();
                System.out.println("Insert");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fillTable();
            fillListSapDienRa();
        }
    }//GEN-LAST:event_btUpdateActionPerformed

    private void tbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) tb.getModel();
        int index = tb.getSelectedRow();
        pnApDungTL.setVisible(true);
        GiamGiaModel gg = lstGGTB.get(index);
        List<TheLoaiModel> lst = theLoaiDuocGiamGia(gg.getId());

        String[] arr = new String[lst.size()];
        int index1 = 0;
        for (TheLoaiModel x : lst) {
            arr[index1++] = x.getTenTL();
        }
        if (gg.isTrangThai()) {
            btHoatDong.setText("Ngừng hoạt động");
        } else {
            btHoatDong.setText("Hoạt động");
        }
        jListCT.setListData(arr);
    }//GEN-LAST:event_tbMouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        pnApDungTL.setVisible(false);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jListSapDienRaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListSapDienRaMouseClicked
        // TODO add your handling code here:
        
        if (evt.getClickCount() == 2) {
            if(JOptionPane.showConfirmDialog(this, "Bạn có muốn sửa","",JOptionPane.YES_NO_OPTION)!=0) return;
            setForm();
        }
    }//GEN-LAST:event_jListSapDienRaMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        clearForm();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btHoatDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHoatDongActionPerformed
        // TODO add your handling code here:
        try {
            if (btHoatDong.getText().equals("Ngừng hoạt động")) {
                if (JOptionPane.showConfirmDialog(this, "Xác nhận dừng hoạt động!", "", JOptionPane.YES_NO_OPTION) != 0) {
                    return;
                }
                int index = tb.getSelectedRow();
                GiamGiaModel gg = lstGGTB.get(index);
                gg.setTrangThai(false);
                ggDao.updateTrangThai(gg);
                JOptionPane.showMessageDialog(this, "Đã dừng");
                btHoatDong.setText("Hoạt động");
            } else {
                if (JOptionPane.showConfirmDialog(this, "Xác nhận hoạt động trở lại!", "", JOptionPane.YES_NO_OPTION) != 0) {
                    return;
                }
                int index = tb.getSelectedRow();
                GiamGiaModel gg = lstGGTB.get(index);
                gg.setTrangThai(true);
                ggDao.updateTrangThai(gg);
                JOptionPane.showMessageDialog(this, "Đã hoạt động");
            }
            fillTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btHoatDongActionPerformed

    private void dcNgayBDPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dcNgayBDPropertyChange
        // TODO add your handling code here:
        try {
            dcNgayKT.setMinSelectableDate(dcNgayBD.getDate());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_dcNgayBDPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> JlstAD;
    private javax.swing.JButton btHoatDong;
    private javax.swing.JButton btUpdate;
    private com.toedter.calendar.JDateChooser dcNgayBD;
    private com.toedter.calendar.JDateChooser dcNgayKT;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList<String> jListCT;
    private javax.swing.JList<String> jListSapDienRa;
    private javax.swing.JList<String> jLstTheLoai;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbGiamGia;
    private javax.swing.JMenuItem mnNgung;
    private javax.swing.JMenuItem mnNgungAll;
    private javax.swing.JMenuItem mnTiepTuc;
    private javax.swing.JMenuItem mnTiepTucAll;
    private javax.swing.JPanel pnApDungTL;
    private javax.swing.JPopupMenu puTable;
    private javax.swing.JSlider sdGiamGia;
    private javax.swing.JTable tb;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
