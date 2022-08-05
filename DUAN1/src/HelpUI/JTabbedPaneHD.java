package HelpUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

public class JTabbedPaneHD extends JPanel {

    JTabbedPane tabbedPane;
    public List<Vector> lstTB = new ArrayList<>();
    int numTabs;
    int index = 1;
    private JPopupMenu mn;
    public JScrollPane sc;

    public JTabbedPaneHD(JPopupMenu mn) {
        this.mn = mn;
        this.setOpaque(false);
        setLayout(new GridLayout());
        createGUI();
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public int getPaneCount() {
        return tabbedPane.getTabCount();
    }

    public int getIndexSelected() {
        return tabbedPane.getSelectedIndex();
    }

    public List<String> getIdSanpham() {
        return (List<String>) lstTB.get(getIndexSelected()).get(1);
    }

    public List<String> getIdSanphamAt(int index) {
        List<String> lst = (List<String>) lstTB.get(index).get(1);
        return lst;
    }

    public JTable getSelectedTable() {
        return (JTable) lstTB.get(getIndexSelected()).get(0);
    }

    public JTable getTableAt(int index) {
        return (JTable) lstTB.get(index).get(0);
    }

    private void createGUI() {
        createJTabbedPane();
        tabbedPane.setOpaque(false);
        add(tabbedPane);
    }

    /**
     * create JTabbedPane contain 2 tab
     */
    private void createJTabbedPane() {
        /* create JTabbedPane */
        try {
             tabbedPane = new JTabbedPane(JTabbedPane.TOP,
                JTabbedPane.SCROLL_TAB_LAYOUT);

        /* add first tab */
        tabbedPane.add(createJScrollPane(), "Hóa đơn " + String.valueOf(index),
                numTabs++);
        tabbedPane.setTabComponentAt(0, new DemoCustomTab(this));

        /* add tab to add new tab when click */
        tabbedPane.add(new JPanel(), "+", numTabs++);
        tabbedPane.addChangeListener(changeListener);
        index++;
        } catch (Exception e) {
        }
    }

    private JScrollPane createJScrollPane() {
        JTable tb = new JTable(0, 5);
        tb.setModel(new DefaultTableModel(null,
                new String[]{
                    "Tên sách", "Giá(VND)", "Số lượng", "Giảm giá(%)", "Thành tiền"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tb.setSelectionBackground(new Color(0, 102, 51));
        tb.setRowHeight(25);
        tb.setComponentPopupMenu(mn);
        Vector vt = new Vector();
        vt.add(0, tb);
        List<String> lst = new ArrayList<>();
        vt.add(1, lst);
        lstTB.add(vt);
        JScrollPane sc = new JScrollPane(tb);
        
        return sc;
    }

    ChangeListener changeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (tabbedPane.getSelectedIndex() != getPaneCount() - 1) {
                return;
            }
            for (int i = 0; i < getPaneCount() - 1; i++) {
                if (getTableAt(i).getRowCount() < 1) {
                    tabbedPane.removeChangeListener(changeListener);
                    tabbedPane.setSelectedIndex(getIndexSelected() - 1);
                    tabbedPane.addChangeListener(changeListener);
                    JOptionPane.showMessageDialog(tabbedPane, tabbedPane.getTitleAt(i) + " chưa có dữ liệu. Hãy sử dụng!");
                    return;
                }
            }
            addNewTab();
        }
    };

    public void addNewTab() {
        int index = numTabs - 1;
        if (tabbedPane.getSelectedIndex() == index) {
            /* if click new tab */
 /* add new tab */
            tabbedPane.removeChangeListener(changeListener);
            tabbedPane.add(createJScrollPane(), "Hóa đơn " + String.valueOf(this.index),
                    index);
            tabbedPane.setTabComponentAt(index, new DemoCustomTab(this));

            tabbedPane.setSelectedIndex(index);
            tabbedPane.addChangeListener(changeListener);
            numTabs++;
            this.index++;
        }
        System.out.println("Tsize "+ lstTB.size());
        System.out.println("Tnum " +numTabs);
    }

    public void removeTab(int index) {
        //  if(JOptionPane.showConfirmDialog(this, "", "", JOptionPane.YES_NO_OPTION)!=0){ return;}
        tabbedPane.remove(index);
        lstTB.remove(index);
        numTabs--;
        if (index == numTabs - 1 && index > 0) {
            tabbedPane.setSelectedIndex(numTabs - 2);
        } else {
            tabbedPane.setSelectedIndex(index);
        }
        
        if (numTabs <= 1) {
            addNewTab();
        }
        System.out.println("Xsize "+ lstTB.size());
        System.out.println("Xnum " +numTabs);
        tabbedPane.setSelectedIndex(numTabs-1);
    }

}
