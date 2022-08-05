package HelpUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
 
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
 
public class DemoCustomTab extends JPanel {
 
    JTabbedPaneHD customJTabbedPane;
 
    /** JPanel contain a JLabel and a JButton to close */
    public DemoCustomTab(JTabbedPaneHD customJTabbedPane) {
        this.customJTabbedPane = customJTabbedPane;
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBorder(new EmptyBorder(5, 2, 2, 2));
        setOpaque(false);
        addLabel();
        add(new CustomButton("x"));
    }
 
    private void addLabel() {
        JLabel label = new JLabel() {
            /** set text for JLabel, it will title of tab */
            public String getText() {
                int index = customJTabbedPane.tabbedPane
                        .indexOfTabComponent(DemoCustomTab.this);
                if (index != -1) {
                    return customJTabbedPane.tabbedPane.getTitleAt(index);
                }
                return null;
            }
        };
        /** add more space between the label and the button */
        label.setBorder(new EmptyBorder(0, 0, 0, 10));
        add(label);
    }
 
    class CustomButton extends JButton implements MouseListener {
        public CustomButton(String text) {
            int size = 15;
            setText(text);
            
            setPreferredSize(new Dimension(size, size));
 
            setToolTipText("Đóng Tab");
 
            /** set transparent */
            setContentAreaFilled(false);
 
            /** set border for button */
            setBorder(new EtchedBorder());
            /** don't show border */
            setBorderPainted(false);
 
            setFocusable(false);
 
            /** add event with mouse */
            addMouseListener(this);
 
        }
 
        /** when click button, tab will close */
        @Override
        public void mouseClicked(MouseEvent e) {
            if(JOptionPane.showConfirmDialog(this, "Xác nhận xóa hóa đơn!","",JOptionPane.YES_NO_OPTION)!=0) return;
            int index = customJTabbedPane.tabbedPane
                    .indexOfTabComponent(DemoCustomTab.this);
            if (index != -1) {
                customJTabbedPane.removeTab(index);
                System.out.println(customJTabbedPane.lstTB.size());
            }
        }
 
        @Override
        public void mousePressed(MouseEvent e) {
        }
 
        @Override
        public void mouseReleased(MouseEvent e) {
        }
 
        /** show border button when mouse hover */
        @Override
        public void mouseEntered(MouseEvent e) {
            setBorderPainted(true);
            setForeground(Color.RED);
        }
 
        /** hide border when mouse not hover */
        @Override
        public void mouseExited(MouseEvent e) {
            setBorderPainted(false);
            setForeground(Color.BLACK);
        }
    }
}