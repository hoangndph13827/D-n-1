/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HELP;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Admin
 */
public class TestJButton extends JFrame{
  private JPanel topPanel;
  private JTable table;
  private JScrollPane scrollPane;
  private String[] columns = new String[3];
  private String[][] data = new String[3][3];
  JSpinner spinner = new JSpinner();
  public TestJButton()
  {
    setSize(300,150);
    topPanel = new JPanel();
    topPanel.setLayout(new BorderLayout());
    
    this.add(topPanel);
    columns = new String[] {"Id", "Name", "Action"};
    data = new String[][]{
      {"1","Thomas"},
      {"2","Jean"},
      {"3","Yohan"}
    };
    DefaultTableModel model = new DefaultTableModel(data,columns);
    table = new JTable();
    table.setModel(model);
    table.getColumn("Action").setCellRenderer(new SpinnerRenderer());
    table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
    table.setEnabled(false);
    scrollPane = new JScrollPane(table);
    topPanel.add(scrollPane,BorderLayout.CENTER);  
    
   
  }
  /////////////////////////////////////////////////////////////////////////////////////////////////////
class SpinnerRenderer extends JButton implements TableCellRenderer{
    public SpinnerRenderer() {
        setOpaque(true);
    }
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
      setText((value == null) ? "1" : value.toString());
      return this;
    }
  }

////////////////////////////////////////////////////////////////////////////////////////
  class ButtonEditor extends DefaultCellEditor 
  {
    
    public ButtonEditor(JCheckBox checkBox)
    {
      super(checkBox);
    }
    public Component getTableCellEditorComponent(JTable table, Object value,
    boolean isSelected, int row, int column) 
    {
      return spinner;
    }
    
  }
  public static void main(String args[])
  {
    TestJButton f = new TestJButton();
    f.setVisible(true);
  }
    
    
}
