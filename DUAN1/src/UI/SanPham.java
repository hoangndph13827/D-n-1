/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import DAO.SachDAO;
import DAO.SachTheLoaiDAO;
import DAO.TacGiaDAO;
import DAO.TheLoaiDAO;
import HELP.ImgIO;
import MODEL.CTSachModel;
import MODEL.TacGiaModel;
import MODEL.TheLoaiModel;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;



public class SanPham extends javax.swing.JPanel {
    private TheLoaiDAO tlDao= new TheLoaiDAO();
    private SachDAO sDao= new SachDAO();
    private TacGiaDAO tgDao= new TacGiaDAO();
    private CTSachModel cts;
    public SanPham(CTSachModel s) {
        initComponents();
        cts=s;
        lbImg.setIcon(ImgIO.readIcon(s.getHinh(),100,150));
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
        lbViTri.setText("-"+s.getVitri());
        lbTap.setText("-"+s.getTap());
        lbSL.setText("SL: "+s.getSoLuong());
    }
    public CTSachModel getCTSach(){
        return cts;
    }
    
    public JLabel getLBSL(){
        return lbSL;
    }
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbTap = new javax.swing.JLabel();
        lbAdd = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbTen = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbTacGia = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lbLoai = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lbViTri = new javax.swing.JLabel();
        lbSL = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lbImg = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        jLabel4.setText("jLabel4");

        setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));

        lbTap.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lbTap.setText("-");

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabel1.setText("Tên sách:");

        lbTen.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lbTen.setText("-");
        lbTen.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabel2.setText("Tập:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabel3.setText("Tác giả:");

        lbTacGia.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lbTacGia.setText("-");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabel6.setText("Thể loại:");

        lbLoai.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lbLoai.setText("-");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabel7.setText("Vị trí:");

        lbViTri.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        lbViTri.setText("-");

        lbSL.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        lbSL.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(16, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lbTacGia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbSL, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbTap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbViTri, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbLoai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbSL))
                .addGap(3, 3, 3)
                .addComponent(lbTen)
                .addGap(3, 3, 3)
                .addComponent(jLabel2)
                .addGap(1, 1, 1)
                .addComponent(lbTap, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel6)
                .addGap(3, 3, 3)
                .addComponent(lbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel3)
                .addGap(3, 3, 3)
                .addComponent(lbTacGia)
                .addGap(3, 3, 3)
                .addComponent(jLabel7)
                .addGap(3, 3, 3)
                .addComponent(lbViTri)
                .addGap(27, 27, 27)
                .addComponent(lbAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel3, java.awt.BorderLayout.LINE_END);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 9, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 197, Short.MAX_VALUE)
        );

        add(jPanel4, java.awt.BorderLayout.LINE_START);

        jPanel5.setBackground(new java.awt.Color(204, 255, 255));
        jPanel5.setToolTipText("");
        jPanel5.add(lbImg);

        add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 166, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void lbAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbAddMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_lbAddMouseClicked

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public javax.swing.JLabel lbAdd;
    private javax.swing.JLabel lbImg;
    private javax.swing.JLabel lbLoai;
    private javax.swing.JLabel lbSL;
    private javax.swing.JLabel lbTacGia;
    private javax.swing.JLabel lbTap;
    private javax.swing.JLabel lbTen;
    private javax.swing.JLabel lbViTri;
    // End of variables declaration//GEN-END:variables
}
