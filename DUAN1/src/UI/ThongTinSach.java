/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package UI;

import DAO.SachDAO;
import DAO.TacGiaDAO;
import DAO.TheLoaiDAO;
import HELP.ImgIO;
import MODEL.CTSachModel;
import MODEL.TacGiaModel;
import MODEL.TheLoaiModel;
import java.awt.Color;

/**
 *
 * @author Admin
 */
public class ThongTinSach extends javax.swing.JDialog {
    private TheLoaiDAO tlDao= new TheLoaiDAO();
    private SachDAO sDao= new SachDAO();
    private TacGiaDAO tgDao= new TacGiaDAO();
    /**
     * Creates new form ThongTinSach
     */
    private CTSachModel cts=new CTSachModel();
    public ThongTinSach(java.awt.Frame parent, boolean modal ,CTSachModel s) {
        super(parent, modal);
        cts.setSoLuong(s.getSoLuong());
        cts.setId(s.getId());
        cts.setGia(s.getGia());
        cts.setId_sach(s.getId_sach());
        cts.setTap(s.getTap());
        setUndecorated(true);
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        lbImg.setIcon(ImgIO.readIcon(s.getHinh(), 120,191));
        lbAdd.setIcon(ImgIO.readIcon("add.png", 10,10));
        String tl="-";
        String tacGia="-";
        for(TheLoaiModel x: tlDao.findByIdSach(s.getId_sach())){
            tl=tl+x.getTenTL()+",";
        }
        lbLoai.setText(tl.substring(0, tl.lastIndexOf(",")));
        lbTen.setText("-"+sDao.findById(s.getId_sach()).getTenSach());
        for(TacGiaModel x: tgDao.findByIdSach(s.getId_sach())){
            tacGia= tacGia+x.getTenTG()+",";
        }
        try {
            lbTacGia.setText(tacGia.substring(0, tacGia.lastIndexOf(",")));
        } catch (Exception e) {
        }
        lbTap.setText("-"+s.getTap());
        spSL.setModel(new javax.swing.SpinnerNumberModel(1, 1,s.getSoLuong() , 1));
    }

    public CTSachModel getCTSach(){
        return cts;
    }
    
    public void setCTSach(){
        cts.setSoLuong((int) spSL.getValue());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbTen = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbTap = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbLoai = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbTacGia = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbAdd = new javax.swing.JLabel();
        spSL = new javax.swing.JSpinner();
        lbClose = new javax.swing.JLabel();
        lbImg = new javax.swing.JLabel();
        backGrou = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(204, 255, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setBackground(new java.awt.Color(0, 102, 51));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Thông tin sách");
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 335, 35));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabel3.setText("Tên sách:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 41, 49, -1));

        lbTen.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lbTen.setText("-");
        lbTen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().add(lbTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 57, 121, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabel4.setText("Tập:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 73, 25, -1));

        lbTap.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lbTap.setText("-");
        getContentPane().add(lbTap, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 89, 121, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabel6.setText("Thể loại:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 105, 39, -1));

        lbLoai.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lbLoai.setText("-");
        getContentPane().add(lbLoai, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 121, 121, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabel5.setText("Tác giả:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 137, 39, -1));

        lbTacGia.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lbTacGia.setText("-");
        getContentPane().add(lbTacGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 153, 136, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabel7.setText("Số lượng");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 169, 56, -1));

        lbAdd.setBackground(new java.awt.Color(0, 102, 51));
        lbAdd.setForeground(new java.awt.Color(255, 255, 255));
        lbAdd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbAdd.setText("Thêm");
        lbAdd.setOpaque(true);
        lbAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbAddMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbAddMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbAddMouseExited(evt);
            }
        });
        getContentPane().add(lbAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 206, 121, 24));

        spSL.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));
        getContentPane().add(spSL, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 180, 121, -1));

        lbClose.setBackground(new java.awt.Color(0, 102, 51));
        lbClose.setForeground(new java.awt.Color(255, 255, 255));
        lbClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbClose.setText("Đóng");
        lbClose.setOpaque(true);
        lbClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbCloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbCloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbCloseMouseExited(evt);
            }
        });
        getContentPane().add(lbClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 236, 121, 24));
        getContentPane().add(lbImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 41, 149, 219));

        backGrou.setBackground(new java.awt.Color(153, 255, 255));
        backGrou.setOpaque(true);
        getContentPane().add(backGrou, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 34, 335, 250));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbAddMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAddMouseEntered
        // TODO add your handling code here:
        lbAdd.setBackground(new Color(0,153,153));
        lbAdd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    }//GEN-LAST:event_lbAddMouseEntered

    private void lbAddMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAddMouseExited
        // TODO add your handling code here:
        lbAdd.setBackground(new Color(0,102,51));
        lbAdd.setBorder(null);
    }//GEN-LAST:event_lbAddMouseExited

    private void lbCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbCloseMouseClicked
        // TODO add your handling code here:
        cts=null;
        dispose();
    }//GEN-LAST:event_lbCloseMouseClicked

    private void lbCloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbCloseMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lbCloseMouseEntered

    private void lbCloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbCloseMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lbCloseMouseExited

    private void lbAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAddMouseClicked
        // TODO add your handling code here:
        setCTSach();
        dispose();
    }//GEN-LAST:event_lbAddMouseClicked

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
            java.util.logging.Logger.getLogger(ThongTinSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongTinSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongTinSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongTinSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ThongTinSach dialog = new ThongTinSach(new javax.swing.JFrame(), true,new CTSachModel());
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backGrou;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    public javax.swing.JLabel lbAdd;
    public javax.swing.JLabel lbClose;
    private javax.swing.JLabel lbImg;
    private javax.swing.JLabel lbLoai;
    private javax.swing.JLabel lbTacGia;
    private javax.swing.JLabel lbTap;
    private javax.swing.JLabel lbTen;
    private javax.swing.JSpinner spSL;
    // End of variables declaration//GEN-END:variables
}