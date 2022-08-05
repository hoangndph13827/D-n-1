/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import java.util.Date;

/**
 *
 * @author Mr.Right
 */
public class BieuDoModel {
    
    private Date batdau ;
    private String tenSanpham;
    private double doanhthuSanpham;
    private String tenNhanVien ;
    private double doanhthuNhanvien;
    private double doanhThu ;

    public BieuDoModel() {
    }

    public BieuDoModel(Date batdau, String tenSanpham, double doanhthuSanpham, String tenNhanVien, double doanhthuNhanvien, double doanhThu) {
        this.batdau = batdau;
        this.tenSanpham = tenSanpham;
        this.doanhthuSanpham = doanhthuSanpham;
        this.tenNhanVien = tenNhanVien;
        this.doanhthuNhanvien = doanhthuNhanvien;
        this.doanhThu = doanhThu;
    }

    public Date getBatdau() {
        return batdau;
    }

    public void setBatdau(Date batdau) {
        this.batdau = batdau;
    }

    public String getTenSanpham() {
        return tenSanpham;
    }

    public void setTenSanpham(String tenSanpham) {
        this.tenSanpham = tenSanpham;
    }

    public double getDoanhthuSanpham() {
        return doanhthuSanpham;
    }

    public void setDoanhthuSanpham(double doanhthuSanpham) {
        this.doanhthuSanpham = doanhthuSanpham;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public double getDoanhthuNhanvien() {
        return doanhthuNhanvien;
    }

    public void setDoanhthuNhanvien(double doanhthuNhanvien) {
        this.doanhthuNhanvien = doanhthuNhanvien;
    }

    public double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(double doanhThu) {
        this.doanhThu = doanhThu;
    }

    
    
    
}
