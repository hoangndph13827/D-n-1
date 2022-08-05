package UI;


//import HELP.Auth;
import java.awt.Color;
import java.sql.Connection;
import java.util.concurrent.locks.Condition;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Provider;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;

public class GuiEmail extends javax.swing.JFrame {

    String noidung;

    public GuiEmail() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

   
  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lbcheckMa = new javax.swing.JLabel();
        lbten = new javax.swing.JLabel();
        blcheck = new javax.swing.JLabel();
        txtgmailgui = new javax.swing.JTextField();
        txtmatkhau = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtnoidung = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form quên mật khẩu");
        setMaximumSize(new java.awt.Dimension(535, 351));
        setMinimumSize(new java.awt.Dimension(535, 370));
        setPreferredSize(new java.awt.Dimension(535, 370));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(550, 351));
        jPanel1.setMinimumSize(new java.awt.Dimension(550, 351));
        jPanel1.setPreferredSize(new java.awt.Dimension(550, 351));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbcheckMa.setForeground(new java.awt.Color(255, 0, 0));
        lbcheckMa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lbcheckMaMousePressed(evt);
            }
        });
        jPanel1.add(lbcheckMa, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 300, 20));
        jPanel1.add(lbten, new org.netbeans.lib.awtextra.AbsoluteConstraints(497, 346, 7, 17));
        jPanel1.add(blcheck, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 579, 372, -1));
        jPanel1.add(txtgmailgui, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 270, -1));

        txtmatkhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmatkhauActionPerformed(evt);
            }
        });
        jPanel1.add(txtmatkhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 270, -1));

        jLabel1.setText("Email gửi:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel2.setText("Mật khẩu:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        jLabel5.setText("Email nhận:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        txtemail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtemailActionPerformed(evt);
            }
        });
        jPanel1.add(txtemail, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 270, -1));

        jButton1.setText("SEND");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, -1, -1));

        txtnoidung.setColumns(20);
        txtnoidung.setRows(5);
        jScrollPane1.setViewportView(txtnoidung);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, 270, -1));

        jLabel4.setText("Nội dung:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 535, 350));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbcheckMaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbcheckMaMousePressed

    }//GEN-LAST:event_lbcheckMaMousePressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String email = txtemail.getText();
        String emailgui = txtgmailgui.getText();
        String password = txtmatkhau.getText();
        try {
            noidung = txtnoidung.getText();
            String host = "smtp.gmail.com";
            String user = txtgmailgui.getText();
            String pass = txtmatkhau.getText();
            String to = txtemail.getText();
            String subject = "Đặt lại mã";
            String message = "Cửa hàng sách Tiền Phong: " + noidung;
            boolean sessionDebug = false;
            Properties pros = System.getProperties();
            pros.put("mail.smtp.starttls.enable", "true");
            pros.put("mail.smtp.host", "host");
            pros.put("mail.smtp.port", "465");
            pros.put("mail.smtp.auth", "true");
            pros.put("mail.smtp.ssl.trust", "*");
            pros.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            pros.put("mail.smtp.socketFactory.fallback", "false");

            Session mailSession = Session.getDefaultInstance(pros);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(user));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setText(message);
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            JOptionPane.showMessageDialog(null, "code has been send to the email");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtmatkhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmatkhauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmatkhauActionPerformed

    private void txtemailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtemailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtemailActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GuiEmail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blcheck;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbcheckMa;
    private javax.swing.JLabel lbten;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtgmailgui;
    private javax.swing.JTextField txtmatkhau;
    private javax.swing.JTextArea txtnoidung;
    // End of variables declaration//GEN-END:variables
}
