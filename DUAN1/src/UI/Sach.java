/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UI;

import DAO.CTSachDAO;
import DAO.NXBDAO;
import DAO.NgonNguDAO;
import DAO.SachDAO;
import DAO.SachTgDAO;
import DAO.SachTheLoaiDAO;
import DAO.TacGiaDAO;
import DAO.TheLoaiDAO;
import HELP.ImgIO;
import HELP.XImages;
import MODEL.CTSachModel;
import MODEL.CTSachModel;
import MODEL.NXBModel;
import MODEL.NgonNguModel;
import MODEL.SachModel;
import MODEL.SachTGModel;
import MODEL.SachTheLoaiModel;
import MODEL.TacGiaModel;
import MODEL.TheLoaiModel;
import MODEL.UserModel;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mr.Right
 */
public class Sach extends javax.swing.JFrame {

    JFileChooser fileChooser = new JFileChooser();

    List<TacGiaModel> lstTG;
    List<TacGiaModel> lstTacGiaDuocChon = new ArrayList<>();
    List<TheLoaiModel> lstTheLoaiDuocChon = new ArrayList<>();
    List<TheLoaiModel> lstTL;
    NgonNguDAO nndao = new NgonNguDAO();
    NXBDAO nxbdao = new NXBDAO();
    TacGiaDAO tgdao = new TacGiaDAO();
    TheLoaiDAO tldao = new TheLoaiDAO();
    CTSachDAO ctsdao = new CTSachDAO();
    SachDAO sdao = new SachDAO();
    List<Vector> lstData = new ArrayList<>();
    int index;
    int id_sach;
    private boolean tgTL;
    private SachTgDAO stgDao = new SachTgDAO();
    private SachTheLoaiDAO stlDao = new SachTheLoaiDAO();

    public Sach() {
        initComponents();
        init();
       
    }

    public void init() {
        setLocationRelativeTo(null);
        fillComboNgonNgu();
        fillComboNXB();
        
        fillTable();
    }

    public TacGiaModel getForm() {
        TacGiaModel tg = new TacGiaModel();
        tg.setTenTG(txtTimKiem.getText());
        return tg;
    }

    public void fillTable() {
        lstData.clear();
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);
        try {
            List<CTSachModel> list = ctsdao.select();
            for (CTSachModel cts : list) {
                SachModel sach = sdao.findById(cts.getId_sach());
                List<TheLoaiModel> tl = tldao.findByIdCts(cts.getId());
                List<TacGiaModel> tg = tgdao.findByIdCts(cts.getId());
                NXBModel nxb = nxbdao.findById(cts.getId_NXB());
                NgonNguModel nn = nndao.findById(cts.getId_NN());
                String chuoiTL = "";
                String chuoiTG = "";
                for (TheLoaiModel x : tl) {
                    chuoiTL = chuoiTL + x.getTenTL() + ",";
                }
                for (TacGiaModel x : tg) {
                    chuoiTG = chuoiTG + x.getTenTG() + ",";
                }
                model.addRow(new Object[]{
                    cts.getId(), sach.getTenSach(), cts.getSoLuong(), cts.getGia(), cts.getTap(), cts.getVitri(),
                    nxb, nn, chuoiTL, cts.getHinh(), chuoiTG
                });
                Vector vt = new Vector();
                vt.add(cts);
                vt.add(tl);
                vt.add(tg);
                vt.add(nxb);
                vt.add(nn);
                vt.add(sach);
                lstData.add(vt);
            }
            model.fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Loi");
        }
    }

    public void fillComboNgonNgu() {
        try {
            DefaultComboBoxModel<NgonNguModel> model = (DefaultComboBoxModel) CbbNN.getModel();
            model.removeAllElements();
            List<NgonNguModel> list = nndao.select();
            for (NgonNguModel ngonNgu : list) {
                model.addElement(ngonNgu);
            }
        } catch (Exception e) {
        }
    }

    public void fillComboNXB() {
        try {
            DefaultComboBoxModel<NXBModel> model = (DefaultComboBoxModel) CbbNXB.getModel();
            model.removeAllElements();
            List<NXBModel> list = nxbdao.select();
            for (NXBModel nXBModel : list) {
                model.addElement(nXBModel);
            }
        } catch (Exception e) {
        }
    }

    public void chonAnh() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImages.save(file);// lưu hình vào thư mục logos
            ImageIcon icon = XImages.read(file.getName(), lblAnh);// đọc hình từ logos
            lblAnh.setIcon(ImgIO.readImg(file, lblAnh));
            lblAnh.setToolTipText(file.getName());// giữ tên hình trong tooltip
        }
    }

    public SachModel getFormsach() {
        SachModel sm = sdao.findByKeyWord(txtTenSach.getText() + "");
        return sm;
    }

    private void fillJlist() {
        String timKiem = txtTimKiem.getText();
        int a = 0;
        if (tgTL) {
            lstTG = tgdao.selectByKeyWord("%" + timKiem + "%");
            String[] arrTG = new String[lstTG.size()];
            for (TacGiaModel x : lstTG) {
                arrTG[a++] = x.getTenTG();
            }
            jlist.setListData(arrTG);
        } else {
            lstTL = tldao.selectByKeyWord("%" + timKiem + "%");
            String[] arrTL = new String[lstTL.size()];
            for (TheLoaiModel x : lstTL) {
                arrTL[a++] = x.getTenTL();
            }
            jlist.setListData(arrTL);
        }

    }

    public CTSachModel getCTsach() {
        CTSachModel ctsach = new CTSachModel();
        DefaultComboBoxModel cbbNXB = (DefaultComboBoxModel) CbbNXB.getModel();
        NXBModel nxb = (NXBModel) cbbNXB.getElementAt(CbbNXB.getSelectedIndex());
        DefaultComboBoxModel cbbNN = (DefaultComboBoxModel) CbbNN.getModel();
        NgonNguModel nn = (NgonNguModel) cbbNN.getElementAt(CbbNN.getSelectedIndex());
        ctsach.setId(txtIdCtS.getText());
        ctsach.setGia(Double.parseDouble(txtGia.getText()));
        ctsach.setSoLuong((int) spSoLuong.getValue());
        ctsach.setTap(txtTap.getText());
        ctsach.setVitri(txtViTri.getText());
        ctsach.setTrangThai("Còn bán");
        ctsach.setId_NXB(nxb.getId());
        ctsach.setId_NN(nn.getID());
        ctsach.setHinh(lblAnh.getToolTipText());
        return ctsach;
    }

    public void edit() {
        Vector vt = lstData.get(index);
        setTacGia((List<TacGiaModel>) vt.get(2));
        setTheLoai((List<TheLoaiModel>) vt.get(1));
        setCtsach((CTSachModel) vt.get(0));
        SachModel s = (SachModel) vt.get(5);
        txtTenSach.setText(s.getTenSach());
        NXBModel nxb = (NXBModel) vt.get(3);
        NgonNguModel nn = (NgonNguModel) vt.get(4);
        CbbNXB.setSelectedItem(nxb.getTenNXB());
        CbbNN.setSelectedItem(nn.getNgonNgu());
        
    }

    private void setTacGia(List<TacGiaModel> lst) {
        CbbTG.removeAllItems();
        lstTacGiaDuocChon.clear();
        for (TacGiaModel x : lst) {
            CbbTG.addItem(x.getTenTG());
            lstTacGiaDuocChon.add(x);
        }
    }

    private void setTheLoai(List<TheLoaiModel> lst) {
        CbbTL.removeAllItems();
        lstTheLoaiDuocChon.clear();
        for (TheLoaiModel x : lst) {
            CbbTL.addItem(x.getTenTL());
            lstTheLoaiDuocChon.add(x);
        }
    }

    private void setCtsach(CTSachModel model) {
        txtIdCtS.setText(model.getId());
        spSoLuong.setValue(model.getSoLuong());
        txtTap.setText(model.getTap() + "");
        txtViTri.setText(model.getVitri());
        txtGia.setText(model.getGia() + "");
        if (model.getHinh() != null) {
            lblAnh.setToolTipText(model.getHinh());
            lblAnh.setIcon(XImages.read(model.getHinh(), lblAnh));

        }
    }

    public void insert() {
        try {
            int chon = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm ");
            CTSachModel model = getCTsach();

            int row = sdao.insert(new SachModel(txtTenSach.getText(), 1, null));
            int id_s = sdao.selectMaxId();
            if (row > 0) {
                model.setId_sach(id_s);
            } else {
                return;
            }
            stgDao.delete(id_s);
            stlDao.delete(id_s);
            ctsdao.insert(model);
            for (TacGiaModel tg : lstTacGiaDuocChon) {
                stgDao.insert(new SachTGModel(id_s, tg.getID_TacGia()));
            }
            System.out.println("TL' " + lstTheLoaiDuocChon.size());
            for (TheLoaiModel tl : lstTheLoaiDuocChon) {
                stlDao.insert(new SachTheLoaiModel(id_s, tl.getId()));
            }
            System.out.println(model.toString());
            this.fillTable();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//   public void insert (){
//        try {
//           int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn thêm");
//            CTSachModel model = getCTsach();
//            int row = sdao.insert(new SachModel(txtTenSach.getText(), 0, null));
//            if (row > 0) {
//                model.setId_sach(sdao.selectMaxId());
//            }
//            ctsdao.insert(model);
//            
//            this.fillTable();
//            JOptionPane.showMessageDialog(this, "Thêm thành công");
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//   }
    public void update() {
        try {
            int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn cập nhật");
            if (chon == JOptionPane.YES_OPTION) {
                SachModel s = (SachModel) lstData.get(index).get(5);
                CTSachModel model = getCTsach();
                stgDao.delete(s.getId());
                stlDao.delete(s.getId());
                for (TacGiaModel tg : lstTacGiaDuocChon) {
                    stgDao.insert(new SachTGModel(s.getId(), tg.getID_TacGia()));
                }
                for (TheLoaiModel tl : lstTheLoaiDuocChon) {
                    stlDao.insert(new SachTheLoaiModel(s.getId(), tl.getId()));
                }
                ctsdao.update(model);
                int row = sdao.update(new SachModel(txtTenSach.getText(), 1, null));
                this.fillTable();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không ?");
            String mauser = tblSach.getValueAt(index, 0).toString();
            ctsdao.delete(mauser);
//            sdao.delete(Integer.parseInt(mauser));
            fillTable();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        txtTenSach.setText("");
        txtViTri.setText("");
        spSoLuong.setValue(0);
        CbbNN.setSelectedIndex(0);
        CbbNXB.setSelectedIndex(0);
        CbbTG.setSelectedIndex(0);
        CbbTL.setSelectedIndex(0);
        txtGia.setText("");
        txtTap.setText("");
        txtIdCtS.setText("");

    }

    public TheLoaiModel getFormTheLoai() {
        TheLoaiModel tl = new TheLoaiModel();

        tl.setTenTL(txtTimKiem.getText());
        return tl;
    }

    public void themTG() {
        TacGiaModel tg = getForm();
        try {

            tgdao.insert(tg);
            this.fillJlist();
            JOptionPane.showMessageDialog(this, "Thêm thành Công!!");
            //this.clearForm();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Thêm Thất Bại!!");
        }
    }

    public void themTL() {

        TheLoaiModel tl = getFormTheLoai();
        try {
            tldao.insert(tl);
            this.fillTable();
            JOptionPane.showMessageDialog(this, "Thêm Thành công!!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Thêm thất bại!!!");
        }

    }

    public void NgonNgu() {
        new UI.NgonNgu(this, true).setVisible(true);
    }

    public void TheLoai() {
        new TheLoaiUI_1(this, true).setVisible(true);
    }

    public void TacGia() {
        new TacGiaUI(this, true).setVisible(true);
    }

    public void NXB() {
        new NhaXuatBan(this, true).setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        txtTap = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtViTri = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        spSoLuong = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        CbbNN = new javax.swing.JComboBox<>();
        txtIdCtS = new javax.swing.JTextField();
        lblAnh = new javax.swing.JLabel();
        CbbTG = new javax.swing.JComboBox<>();
        CbbNXB = new javax.swing.JComboBox<>();
        CbbTL = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlist = new javax.swing.JList<>();
        txtTimKiem = new javax.swing.JTextField();
        lbTieuDe = new javax.swing.JLabel();
        btnThemTG = new javax.swing.JButton();
        btnThemTL = new javax.swing.JButton();
        btnTacGia = new javax.swing.JButton();
        btnTheLoai = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jButton1 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JToolBar.Separator();
        jButton3 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JToolBar.Separator();
        jButton4 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sách", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 30))); // NOI18N

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Tên Sách", "Số Lượng", "Giá", "Tập", "Vị Trí", "NXB", "Ngôn Ngữ", "Thể Loại", "Hình", "Tác giả"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSach);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Tên Sách:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Số Lượng:");

        btnThem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Save.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Update.png"))); // NOI18N
        jButton6.setText("Sửa");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Delete.png"))); // NOI18N
        jButton7.setText("Xóa");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/New.png"))); // NOI18N
        jButton8.setText("Làm Mới");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Tập:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Ngôn Ngữ:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("Vị trí:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel14.setText("Giá:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("NXB");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Tác giả");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Thể Loại");

        CbbNN.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CbbNN.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CbbNNItemStateChanged(evt);
            }
        });
        CbbNN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbbNNActionPerformed(evt);
            }
        });

        lblAnh.setText("jLabel7");
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });

        CbbTG.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CbbTGItemStateChanged(evt);
            }
        });
        CbbTG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbbTGActionPerformed(evt);
            }
        });

        CbbNXB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        CbbNXB.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CbbNXBItemStateChanged(evt);
            }
        });
        CbbNXB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbbNXBActionPerformed(evt);
            }
        });

        CbbTL.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                CbbTLItemStateChanged(evt);
            }
        });
        CbbTL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CbbTLActionPerformed(evt);
            }
        });

        jlist.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jlistAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jlist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jlistMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jlist);

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

        lbTieuDe.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lbTieuDe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTieuDe.setText("Chọn thẻ loại");

        btnThemTG.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemTG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Save.png"))); // NOI18N
        btnThemTG.setText("ThêmTG");
        btnThemTG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTGActionPerformed(evt);
            }
        });

        btnThemTL.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemTL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Save.png"))); // NOI18N
        btnThemTL.setText("ThêmTL");
        btnThemTL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(txtTimKiem)
                        .addGap(26, 26, 26))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnThemTL)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnThemTG)))
                        .addGap(24, 24, 24))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbTieuDe, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(lbTieuDe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThemTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemTG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
        );

        btnTacGia.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTacGia.setText("+");
        btnTacGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTacGiaActionPerformed(evt);
            }
        });

        btnTheLoai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTheLoai.setText("+");
        btnTheLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTheLoaiActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Mã:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Hình:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(257, 257, 257)
                .addComponent(btnThem)
                .addGap(72, 72, 72)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(jButton8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(spSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(txtIdCtS, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(CbbNN, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTap, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CbbTG, 0, 149, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(CbbTL, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(CbbNXB, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtViTri))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 939, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CbbTG, txtViTri});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CbbNXB, CbbTL, txtGia});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(CbbTL, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnTheLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtIdCtS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(35, 35, 35)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnTacGia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(CbbTG, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(36, 36, 36)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(CbbNXB, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(spSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(30, 30, 30)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTap, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addComponent(txtViTri))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(CbbNN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        jToolBar1.setBorder(null);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);
        jToolBar1.setFocusable(false);
        jToolBar1.setPreferredSize(new java.awt.Dimension(100, 100));
        jToolBar1.add(jSeparator1);

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("NXB");
        jButton1.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);
        jToolBar1.add(jSeparator2);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("Tác Giả");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);
        jToolBar1.add(jSeparator5);

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton3.setText("Thể Loại");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator6);

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton4.setText("Ngôn ngữ");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setPreferredSize(new java.awt.Dimension(100, 100));
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);
        jToolBar1.add(jSeparator3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 174, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void CbbNNItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CbbNNItemStateChanged
        // TODO add your handling code here:
        //cbbNN();
    }//GEN-LAST:event_CbbNNItemStateChanged

    private void CbbNNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbbNNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CbbNNActionPerformed

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        // TODO add your handling code here:
        chonAnh();

    }//GEN-LAST:event_lblAnhMouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        NXB();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void CbbTGItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CbbTGItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_CbbTGItemStateChanged

    private void CbbTGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbbTGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CbbTGActionPerformed

    private void CbbNXBItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CbbNXBItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_CbbNXBItemStateChanged

    private void CbbNXBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbbNXBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CbbNXBActionPerformed

    private void CbbTLItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_CbbTLItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_CbbTLItemStateChanged

    private void CbbTLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CbbTLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CbbTLActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        update();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        // TODO add your handling code here:
        index = tblSach.getSelectedRow();
        edit();
    }//GEN-LAST:event_tblSachMouseClicked

    private void btnTheLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTheLoaiActionPerformed
        // TODO add your handling code here:
        lbTieuDe.setText("Chọn thể loại");
        tgTL = false;

        fillJlist();
    }//GEN-LAST:event_btnTheLoaiActionPerformed

    private void btnTacGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTacGiaActionPerformed
        // TODO add your handling code here:
        lbTieuDe.setText("Chọn tác giả");
        tgTL = true;

        fillJlist();
    }//GEN-LAST:event_btnTacGiaActionPerformed

    private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyReleased
        // TODO add your handling code here:
        fillJlist();
    }//GEN-LAST:event_txtTimKiemKeyReleased

    private void jlistMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jlistMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int index = jlist.getSelectedIndex();
            if (tgTL) {
                if (JOptionPane.showConfirmDialog(this, "Thêm tác giả cho sách") != 0) {
                    return;
                }
                TacGiaModel tg = lstTG.get(index);
                CbbTG.addItem(tg.getTenTG());
                lstTacGiaDuocChon.add(tg);
            } else {
                if (JOptionPane.showConfirmDialog(this, "Thêm thể loại cho sách") != 0) {
                    return;
                }
                TheLoaiModel tl = lstTL.get(index);
                CbbTL.addItem(tl.getTenTL());
                lstTheLoaiDuocChon.add(tl);
            }

        }
    }//GEN-LAST:event_jlistMouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        delete();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnThemTGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTGActionPerformed
        // TODO add your handling code here:
        themTG();
    }//GEN-LAST:event_btnThemTGActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed

    private void jlistAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jlistAncestorAdded
        // TODO add your handling code here:

    }//GEN-LAST:event_jlistAncestorAdded

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        TacGia();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        TheLoai();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        NgonNgu();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnThemTLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTLActionPerformed
        // TODO add your handling code here:
        themTL();
    }//GEN-LAST:event_btnThemTLActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sach().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CbbNN;
    private javax.swing.JComboBox<String> CbbNXB;
    private javax.swing.JComboBox<String> CbbTG;
    private javax.swing.JComboBox<String> CbbTL;
    private javax.swing.JButton btnTacGia;
    private javax.swing.JButton btnTheLoai;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemTG;
    private javax.swing.JButton btnThemTL;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator5;
    private javax.swing.JToolBar.Separator jSeparator6;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JList<String> jlist;
    private javax.swing.JLabel lbTieuDe;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JSpinner spSoLuong;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtIdCtS;
    private javax.swing.JTextField txtTap;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtViTri;
    // End of variables declaration//GEN-END:variables
}
