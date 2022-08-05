/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI;

import DAO.ThongKeDAO;
import HELP.DateHelper;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Mr.Right
 */
public class ThongKe_BaoCao extends javax.swing.JPanel {

    /**
     * Creates new form ThongKe_BaoCao
     */
    ThongKeDAO dao = new ThongKeDAO();
    int index;

    public ThongKe_BaoCao() {
        initComponents();
        init();
        pntheoquy.setVisible(false);
        pnThang.setVisible(false);

    }

    public void init() {
        filltableNhanVien();
        filltableThoiGian();
        filltableSanPham();
        soLuongBan();
        doanhThu();
        tonghoadon();
        loiNhuan();
        nam();
        bieudoSanpham();
        bieudonhanvien();
        bieuDoThoiGian();

    }

    public void filltableNhanVienTheoNgay() {
        try {
            DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
            model.setRowCount(0);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            String d1 = sdf.format(dKetThuc.getDate());
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
            String d = sdf2.format(dBatDau.getDate());
            List<Object[]> list = dao.getNhanVientheongay(d, d1);
            for (Object[] objects : list) {
                model.addRow(objects);
            }
            bieudonhanvien();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filltableNhanVien() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getNhanVien();
        for (Object[] objects : list) {
            model.addRow(objects);
        }
    }

    public void filltableNhanVienTheoThang(int batdau, int ketthuc) {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getNhanvientheothang(batdau, ketthuc);
        for (Object[] objects : list) {
            model.addRow(objects);
        }
        bieudonhanvien();
    }

    public void filltableSanPhamTheoThang(int batdau, int ketthuc) {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getSanPhamtheothang(batdau, ketthuc);
        for (Object[] objects : list) {
            model.addRow(objects);
        }
        bieudoSanpham();
    }

    public void filltableSanPhamTheoNgay() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String d1 = sdf.format(dKetThuc.getDate());
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
        String d = sdf2.format(dBatDau.getDate());
        List<Object[]> list = dao.getSanPhamtheongay(d, d1);
        for (Object[] objects : list) {
            model.addRow(objects);
        }
        bieudoSanpham();
    }

    public void filltableSanPham() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getsanpham();
        for (Object[] objects : list) {
            model.addRow(objects);
        }
    }

    public void filltableThoiGianTheoThang(int batdau, int ketthuc) {
        DefaultTableModel model = (DefaultTableModel) tblThoiGian.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getThoiGiantheothang(batdau, ketthuc);
        for (Object[] objects : list) {
            model.addRow(objects);
        }
        bieuDoThoiGian();
    }

    public void filltableThoiGianTheoNgay() {
        DefaultTableModel model = (DefaultTableModel) tblThoiGian.getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String d1 = sdf.format(dKetThuc.getDate());
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
        String d = sdf2.format(dBatDau.getDate());
        List<Object[]> list = dao.getThoiGiantheongay(d, d1);
        for (Object[] objects : list) {
            model.addRow(objects);
        }
        bieuDoThoiGian();
    }

    public void filltableThoiGian() {
        DefaultTableModel model = (DefaultTableModel) tblThoiGian.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getThoiGian();
        for (Object[] objects : list) {
            model.addRow(objects);
        }
    }

    public void nam() {
        DateFormat sdf = new SimpleDateFormat("yyyy");
        String d1 = sdf.format(new Date());
        jLabel3.setText("(Năm " + d1 + ")");
    }

    public void soLuongBan() {
        try {
            int sl = dao.SoLuongBan(DateHelper.stringa(new Date(), "MM/dd/yyyy"));
            lblSLBan.setText(sl + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tonghoadon() {
        try {
            int sl = dao.TongHoaDon(DateHelper.stringa(new Date(), "MM/dd/yyyy"));
            lbldoanhThu.setText(sl + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doanhThu() {
        try {
            double sl = dao.doanhThu(DateHelper.stringa(new Date(), "MM/dd/yyyy"));
            lblDoanhThu.setText(sl + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loiNhuan() {
        try {
            double sl = dao.loiNhuan(DateHelper.stringa(new Date(), "yyyy"));
            lblLoiNhuan.setText(sl + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void giamGia() {
        try {
            double sl = dao.giamGia(DateHelper.stringa(new Date(), "yyyy"));
            lblGiamGIa.setText(sl + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void combo() {
        try {
            if (cboLoaiThoiGian4.getSelectedItem().equals("Theo ngày")) {
                pntheongay.setVisible(true);
                pntheoquy.setVisible(false);
                pnThang.setVisible(false);

            }
            if (cboLoaiThoiGian4.getSelectedItem().equals("Theo tháng")) {
                pntheongay.setVisible(false);
                pntheoquy.setVisible(false);
                pnThang.setVisible(true);
            }
            if (cboLoaiThoiGian4.getSelectedItem().equals("Theo quý")) {
                pntheoquy.setVisible(true);
                pntheongay.setVisible(false);
                pnThang.setVisible(false);

            }
        } catch (Exception e) {
        }
    }

    public void bieudoSanpham() {
        DefaultCategoryDataset dateset = new DefaultCategoryDataset();
        for (int i = 0; i < tblSanPham.getRowCount(); i++) {
            dateset.setValue(Double.parseDouble(tblSanPham.getValueAt(i, 2).toString()), "Doanh thu", tblSanPham.getValueAt(i, 0).toString());

        }
        JFreeChart chart = ChartFactory.createBarChart("Thống kê sản phẩm", "tên sản phẩm", "Doanh thu", dateset, PlotOrientation.HORIZONTAL, true, true, true);

        CategoryPlot p = chart.getCategoryPlot();
        chart.getTitle().setPaint(Color.pink);
        chart.setBackgroundPaint(Color.white);
        p.setRangeGridlinePaint(Color.black);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(pnsanpham.getWidth(), 100));
        pnsanpham.setSize(chartPanel.getSize());

        pnsanpham.removeAll();
        pnsanpham.setLayout(new CardLayout());
        pnsanpham.add(chartPanel);
        pnsanpham.validate();
        pnsanpham.repaint();
    }

    public void bieudonhanvien() {
        DefaultCategoryDataset dateset = new DefaultCategoryDataset();
        for (int i = 0; i < tblNhanVien.getRowCount(); i++) {
            dateset.setValue(Double.parseDouble(tblNhanVien.getValueAt(i, 3).toString()), "Doanh thu", tblNhanVien.getValueAt(i, 1).toString());

        }
        JFreeChart chart = ChartFactory.createBarChart("Thống kê nhân viên", "tên nhân viên", "Doanh thu", dateset, PlotOrientation.HORIZONTAL, true, true, true);

        CategoryPlot p = chart.getCategoryPlot();
        chart.getTitle().setPaint(Color.pink);
        chart.setBackgroundPaint(Color.white);
        p.setRangeGridlinePaint(Color.black);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(pnnhanvien.getWidth(), 300));
        pnnhanvien.setSize(chartPanel.getSize());

        pnnhanvien.removeAll();
        pnnhanvien.setLayout(new CardLayout());
        pnnhanvien.add(chartPanel);
        pnnhanvien.validate();
        pnnhanvien.repaint();
    }

    public void bieuDoThoiGian() {
//        DefaultCategoryDataset dateset = new DefaultCategoryDataset();
//        for (int i = 0; i < tblThoiGian.getRowCount(); i++) {
//            dateset.setValue(Double.parseDouble(tblThoiGian.getValueAt(i, 2).toString()), "Doanh thu", tblThoiGian.getValueAt(i, 5).toString());
//
//        }
//        JFreeChart barChart = ChartFactory.createBarChart(
//                "Thống kê Doanh thu".toUpperCase(),
//                "Thời gian", "Doanh thu",
//                dateset, PlotOrientation.VERTICAL, false, true, false);
//        ChartPanel chartPanel = new ChartPanel(barChart);
//
//        barChart.getTitle().setPaint(Color.pink);
//        barChart.setBackgroundPaint(Color.white);
//        chartPanel.setPreferredSize(new Dimension(pnthoigian.getWidth(), 300));
//
//        pnthoigian.removeAll();
//        pnthoigian.setLayout(new CardLayout());
//        pnthoigian.add(chartPanel);
//        pnthoigian.validate();
//        pnthoigian.repaint();
    }

    public void xuatExcel(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void xuat() {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();
            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Doanh thu");
                Row rowcol = sheet.createRow(0);

                for (int i = 0; i < tblThoiGian.getColumnCount(); i++) {
                    Cell cell = rowcol.createCell(i);
                    cell.setCellValue(tblThoiGian.getColumnName(i));
                }

                for (int j = 0; j < tblThoiGian.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tblThoiGian.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tblThoiGian.getValueAt(j, k) != null) {
                            cell.setCellValue(tblThoiGian.getValueAt(j, k).toString());
                        }
                    }
                }
                Sheet sheet2 = wb.createSheet("Nhân viên");
                Row rowcol2 = sheet2.createRow(0);

                for (int i = 0; i < tblNhanVien.getColumnCount(); i++) {
                    Cell cell = rowcol2.createCell(i);
                    cell.setCellValue(tblNhanVien.getColumnName(i));
                }

                for (int j = 0; j < tblNhanVien.getRowCount(); j++) {
                    Row row = sheet2.createRow(j + 1);
                    for (int k = 0; k < tblNhanVien.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tblNhanVien.getValueAt(j, k) != null) {
                            cell.setCellValue(tblNhanVien.getValueAt(j, k).toString());
                        }
                    }
                }
                Sheet sheet1 = wb.createSheet("Sản phẩm");
                Row rowcol1 = sheet1.createRow(0);
                for (int i = 0; i < tblSanPham.getColumnCount(); i++) {
                    Cell cell = rowcol1.createCell(i);
                    cell.setCellValue(tblSanPham.getColumnName(i));
                }

                for (int j = 0; j < tblSanPham.getRowCount(); j++) {
                    Row row = sheet1.createRow(j + 1);
                    for (int k = 0; k < tblSanPham.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tblSanPham.getValueAt(j, k) != null) {
                            cell.setCellValue(tblSanPham.getValueAt(j, k).toString());
                        }
                    }
                }

                JOptionPane.showMessageDialog(this, "Xuất file thành công");
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                xuatExcel(saveFile.toString());

            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException ex) {
            Logger.getLogger(ThongKe_BaoCao.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        lblSLBan = new javax.swing.JLabel();
        lblDoanhThu = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lbldoanhThu = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblGiamGIa = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lblLoiNhuan = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        pnthoigian = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThoiGian = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        pnnhanvien = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        tp = new javax.swing.JTabbedPane();
        jPanel16 = new javax.swing.JPanel();
        pnsanpham = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        cboLoaiThoiGian4 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jButton11 = new javax.swing.JButton();
        pntheongay = new javax.swing.JPanel();
        lblBatDau = new javax.swing.JLabel();
        dKetThuc = new com.toedter.calendar.JDateChooser();
        lblKetThuc = new javax.swing.JLabel();
        dBatDau = new com.toedter.calendar.JDateChooser();
        pntheoquy = new javax.swing.JPanel();
        lblQuy = new javax.swing.JLabel();
        cboQuy = new javax.swing.JComboBox<>();
        pnThang = new javax.swing.JPanel();
        lblBatDau1 = new javax.swing.JLabel();
        lblKetThuc1 = new javax.swing.JLabel();
        cboBatDau = new javax.swing.JComboBox<>();
        cboKetThuc = new javax.swing.JComboBox<>();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setLayout(new java.awt.GridLayout(1, 0, 40, 10));

        jPanel18.setBackground(new java.awt.Color(76, 179, 195));
        jPanel18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel23.setBackground(new java.awt.Color(0, 0, 0));
        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel23.setText("SL\\Doanh thu hôm nay");

        lblSLBan.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lblSLBan.setText("0");

        lblDoanhThu.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        lblDoanhThu.setText("0");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 36)); // NOI18N
        jLabel2.setText("\\");

            javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
            jPanel18.setLayout(jPanel18Layout);
            jPanel18Layout.setHorizontalGroup(
                jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel18Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel18Layout.createSequentialGroup()
                            .addComponent(jLabel23)
                            .addContainerGap(114, Short.MAX_VALUE))
                        .addGroup(jPanel18Layout.createSequentialGroup()
                            .addComponent(lblSLBan, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lblDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(50, 50, 50))))
            );
            jPanel18Layout.setVerticalGroup(
                jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                    .addContainerGap(25, Short.MAX_VALUE)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblSLBan, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            jPanel19.add(jPanel18);

            jPanel3.setBackground(new java.awt.Color(4, 220, 220));
            jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jLabel6.setBackground(new java.awt.Color(0, 0, 0));
            jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
            jLabel6.setText("Tổng hóa đơn hôm nay");

            lbldoanhThu.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
            lbldoanhThu.setText("0");

            javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lbldoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(28, Short.MAX_VALUE)
                    .addComponent(lbldoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            jPanel19.add(jPanel3);

            jPanel2.setBackground(new java.awt.Color(255, 102, 102));

            jLabel7.setBackground(new java.awt.Color(0, 0, 0));
            jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
            jLabel7.setText("Đã giảm hôm nay");

            lblGiamGIa.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
            lblGiamGIa.setText("0");

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(0, 152, Short.MAX_VALUE))
                        .addComponent(lblGiamGIa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap(30, Short.MAX_VALUE)
                    .addComponent(lblGiamGIa, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            jPanel19.add(jPanel2);

            jPanel5.setBackground(new java.awt.Color(252, 152, 53));
            jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

            jLabel5.setBackground(new java.awt.Color(0, 0, 0));
            jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
            jLabel5.setText("Lợi nhuận");

            lblLoiNhuan.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
            lblLoiNhuan.setText("0");

            jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N

            javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
            jPanel5.setLayout(jPanel5Layout);
            jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblLoiNhuan, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addContainerGap(16, Short.MAX_VALUE)
                    .addComponent(lblLoiNhuan, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );

            jPanel19.add(jPanel5);

            jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

            javax.swing.GroupLayout pnthoigianLayout = new javax.swing.GroupLayout(pnthoigian);
            pnthoigian.setLayout(pnthoigianLayout);
            pnthoigianLayout.setHorizontalGroup(
                pnthoigianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 1338, Short.MAX_VALUE)
            );
            pnthoigianLayout.setVerticalGroup(
                pnthoigianLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 350, Short.MAX_VALUE)
            );

            javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
            jPanel10.setLayout(jPanel10Layout);
            jPanel10Layout.setHorizontalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnthoigian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanel10Layout.setVerticalGroup(
                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnthoigian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            jTabbedPane2.addTab("Tổng quan", jPanel10);

            tblThoiGian.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null},
                    {null, null, null, null, null, null}
                },
                new String [] {
                    "Nhân viên", "Khách hàng", "Số lượng bán", "Giảm giá", "Lợi nhuận", "Ngày mua"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            jScrollPane1.setViewportView(tblThoiGian);

            javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
            jPanel11.setLayout(jPanel11Layout);
            jPanel11Layout.setHorizontalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1318, Short.MAX_VALUE)
                    .addContainerGap())
            );
            jPanel11Layout.setVerticalGroup(
                jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel11Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                    .addContainerGap())
            );

            jTabbedPane2.addTab("Chi tiết", jPanel11);

            javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
            jPanel6.setLayout(jPanel6Layout);
            jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addContainerGap())
            );
            jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane2)
                    .addContainerGap())
            );

            jTabbedPane1.addTab("Thời gian", jPanel6);

            javax.swing.GroupLayout pnnhanvienLayout = new javax.swing.GroupLayout(pnnhanvien);
            pnnhanvien.setLayout(pnnhanvienLayout);
            pnnhanvienLayout.setHorizontalGroup(
                pnnhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 1338, Short.MAX_VALUE)
            );
            pnnhanvienLayout.setVerticalGroup(
                pnnhanvienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 350, Short.MAX_VALUE)
            );

            javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
            jPanel14.setLayout(jPanel14Layout);
            jPanel14Layout.setHorizontalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnnhanvien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanel14Layout.setVerticalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnnhanvien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            jTabbedPane4.addTab("Tổng quan", jPanel14);

            tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String [] {
                    "Mã nhân viên", "Tên nhân viên", "Tổng sản phẩm đã bán", "Lợi nhuận"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            jScrollPane3.setViewportView(tblNhanVien);

            javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
            jPanel15.setLayout(jPanel15Layout);
            jPanel15Layout.setHorizontalGroup(
                jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel15Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1318, Short.MAX_VALUE)
                    .addContainerGap())
            );
            jPanel15Layout.setVerticalGroup(
                jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel15Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                    .addContainerGap())
            );

            jTabbedPane4.addTab("Chi tiết", jPanel15);

            javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
            jPanel8.setLayout(jPanel8Layout);
            jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addContainerGap())
            );
            jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jTabbedPane4)
                    .addContainerGap())
            );

            jTabbedPane1.addTab("Nhân viên", jPanel8);

            javax.swing.GroupLayout pnsanphamLayout = new javax.swing.GroupLayout(pnsanpham);
            pnsanpham.setLayout(pnsanphamLayout);
            pnsanphamLayout.setHorizontalGroup(
                pnsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 1338, Short.MAX_VALUE)
            );
            pnsanphamLayout.setVerticalGroup(
                pnsanphamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 350, Short.MAX_VALUE)
            );

            javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
            jPanel16.setLayout(jPanel16Layout);
            jPanel16Layout.setHorizontalGroup(
                jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnsanpham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            jPanel16Layout.setVerticalGroup(
                jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnsanpham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );

            tp.addTab("Tổng quan", jPanel16);

            tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {null, null, null},
                    {null, null, null},
                    {null, null, null},
                    {null, null, null}
                },
                new String [] {
                    "Tên sản phẩm", "Số lượng bán", "Doanh thu"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            jScrollPane5.setViewportView(tblSanPham);

            javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
            jPanel17.setLayout(jPanel17Layout);
            jPanel17Layout.setHorizontalGroup(
                jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 1318, Short.MAX_VALUE)
                    .addContainerGap())
            );
            jPanel17Layout.setVerticalGroup(
                jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel17Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                    .addContainerGap())
            );

            tp.addTab("Chi tiết", jPanel17);

            javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
            jPanel9.setLayout(jPanel9Layout);
            jPanel9Layout.setHorizontalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel9Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addContainerGap())
            );
            jPanel9Layout.setVerticalGroup(
                jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(tp)
                    .addContainerGap())
            );

            jTabbedPane1.addTab("Sản phẩm", jPanel9);

            jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
            jLabel1.setText("Doanh thu - Báo cáo");

            jButton9.setBackground(new java.awt.Color(15, 121, 42));
            jButton9.setText("Xuất excel");
            jButton9.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton9ActionPerformed(evt);
                }
            });

            jButton10.setBackground(new java.awt.Color(21, 192, 192));
            jButton10.setText("Tìm kiếm");
            jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    jButton10MouseClicked(evt);
                }
            });
            jButton10.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton10ActionPerformed(evt);
                }
            });

            cboLoaiThoiGian4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Theo ngày", "Theo tháng", "Theo quý" }));
            cboLoaiThoiGian4.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    cboLoaiThoiGian4ActionPerformed(evt);
                }
            });

            jLabel27.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
            jLabel27.setText("Loại thời gian");

            jButton11.setBackground(new java.awt.Color(51, 102, 255));
            jButton11.setText("Báo cáo");
            jButton11.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton11ActionPerformed(evt);
                }
            });

            pntheongay.setBackground(new java.awt.Color(255, 255, 255));
            pntheongay.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

            lblBatDau.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
            lblBatDau.setText("Ngày kết thúc");
            pntheongay.add(lblBatDau, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 10, -1, 20));

            dKetThuc.setDateFormatString("MM/dd/yyyy");
            pntheongay.add(dKetThuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 120, -1));

            lblKetThuc.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
            lblKetThuc.setText("Ngày bắt đầu");
            pntheongay.add(lblKetThuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

            dBatDau.setDateFormatString("MM/dd/yyyy");
            pntheongay.add(dBatDau, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 120, -1));

            pntheoquy.setBackground(new java.awt.Color(255, 255, 255));
            pntheoquy.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

            lblQuy.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
            lblQuy.setText("Quý");
            pntheoquy.add(lblQuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

            cboQuy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
            cboQuy.setToolTipText("");
            pntheoquy.add(cboQuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 70, -1));

            pnThang.setBackground(new java.awt.Color(255, 255, 255));
            pnThang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

            lblBatDau1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
            lblBatDau1.setText("Tháng kết thúc");
            pnThang.add(lblBatDau1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, -1, 20));

            lblKetThuc1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
            lblKetThuc1.setText("Tháng bắt đầu");
            pnThang.add(lblKetThuc1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, -1, -1));

            cboBatDau.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
            pnThang.add(cboBatDau, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 70, -1));

            cboKetThuc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
            pnThang.add(cboKetThuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 70, -1));

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSeparator1)
                .addComponent(jSeparator2)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
                        .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButton10)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton9)
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton11))
                                .addComponent(jLabel1)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel27)
                                    .addGap(18, 18, 18)
                                    .addComponent(cboLoaiThoiGian4, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(35, 35, 35)
                                    .addComponent(pntheongay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(pntheoquy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(pnThang, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addGap(18, 18, 18)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pntheoquy, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cboLoaiThoiGian4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(pntheongay, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(pnThang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton10)
                        .addComponent(jButton9)
                        .addComponent(jButton11))
                    .addGap(6, 6, 6)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                    .addContainerGap())
            );

            jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {pnThang, pntheongay, pntheoquy});

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
        }// </editor-fold>//GEN-END:initComponents

    private void jButton10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton10MouseClicked

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:

        if (cboLoaiThoiGian4.getSelectedItem().equals("Theo ngày")) {
            filltableSanPhamTheoNgay();
            filltableNhanVienTheoNgay();
            filltableThoiGianTheoNgay();
        }
        if (cboLoaiThoiGian4.getSelectedItem().equals("Theo tháng")) {
            int batdau = Integer.parseInt(cboBatDau.getSelectedItem().toString());
            int ketthuc = Integer.parseInt(cboKetThuc.getSelectedItem().toString());
            filltableThoiGianTheoThang(batdau, ketthuc);
            filltableSanPhamTheoThang(batdau, ketthuc);
            filltableNhanVienTheoThang(batdau, ketthuc);
        }
        if (cboLoaiThoiGian4.getSelectedItem().equals("Theo quý")) {
            if (cboQuy.getSelectedItem().equals("1")) {
                filltableThoiGianTheoThang(1, 3);
                filltableSanPhamTheoThang(1, 3);
                filltableNhanVienTheoThang(1, 3);
            }
            if (cboQuy.getSelectedItem().equals("2")) {
                filltableThoiGianTheoThang(4, 6);
                filltableSanPhamTheoThang(4, 6);
                filltableNhanVienTheoThang(4, 6);
            }
            if (cboQuy.getSelectedItem().equals("3")) {
                filltableThoiGianTheoThang(7, 9);
                filltableSanPhamTheoThang(7, 9);
                filltableNhanVienTheoThang(7, 9);
            }
            if (cboQuy.getSelectedItem().equals("4")) {
                filltableThoiGianTheoThang(10, 12);
                filltableSanPhamTheoThang(10, 12);
                filltableNhanVienTheoThang(10, 12);
            }
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        xuat();
//        xuatSanpham();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void cboLoaiThoiGian4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoaiThoiGian4ActionPerformed
        // TODO add your handling code here:
        combo();
    }//GEN-LAST:event_cboLoaiThoiGian4ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        new SendMail().setVisible(true);
    }//GEN-LAST:event_jButton11ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboBatDau;
    private javax.swing.JComboBox<String> cboKetThuc;
    private javax.swing.JComboBox<String> cboLoaiThoiGian4;
    private javax.swing.JComboBox<String> cboQuy;
    private com.toedter.calendar.JDateChooser dBatDau;
    private com.toedter.calendar.JDateChooser dKetThuc;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JLabel lblBatDau;
    private javax.swing.JLabel lblBatDau1;
    private javax.swing.JLabel lblDoanhThu;
    private javax.swing.JLabel lblGiamGIa;
    private javax.swing.JLabel lblKetThuc;
    private javax.swing.JLabel lblKetThuc1;
    private javax.swing.JLabel lblLoiNhuan;
    private javax.swing.JLabel lblQuy;
    private javax.swing.JLabel lblSLBan;
    private javax.swing.JLabel lbldoanhThu;
    private javax.swing.JPanel pnThang;
    private javax.swing.JPanel pnnhanvien;
    private javax.swing.JPanel pnsanpham;
    private javax.swing.JPanel pntheongay;
    private javax.swing.JPanel pntheoquy;
    private javax.swing.JPanel pnthoigian;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblThoiGian;
    private javax.swing.JTabbedPane tp;
    // End of variables declaration//GEN-END:variables
}
