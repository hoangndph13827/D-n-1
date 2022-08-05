/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HELP;

import UI.SendMail;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Mr.Right
 */
public class Chay {

    Thread t;

    public void thread() {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 20; i++) {
                    if (i == 20) {
                        int hoi = JOptionPane.showConfirmDialog(null, "Bạn có muốn gửi báo cáo!", "Thông báo", JOptionPane.YES_OPTION, JOptionPane.NO_OPTION);
                        if (hoi == JOptionPane.YES_OPTION) {
                            new SendMail().setVisible(true);
                        }
                    }
                    try {
                        t.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Chay.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });
    }
}
