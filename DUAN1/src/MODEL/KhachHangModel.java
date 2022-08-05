/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class KhachHangModel {
    private String tenKH,sdt,DiaChi,email;
    private int id,id_td,diemHT,giamGia;
    private Date ngaySinh;
    private  boolean gioiTinh;
    

    public KhachHangModel() {
        
    }

    public KhachHangModel(String tenKH, String sdt, String DiaChi, String email, int id, int id_td, int diemHT, int giamGia, Date ngaySinh, boolean gioiTinh) {
        this.tenKH = tenKH;
        this.sdt = sdt;
        this.DiaChi = DiaChi;
        this.email = email;
        this.id = id;
        this.id_td = id_td;
        this.diemHT = diemHT;
        this.giamGia = giamGia;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


 
    public String getTenKH() {
        return tenKH;
    }

    public int getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(int giamGia) {
        this.giamGia = giamGia;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId_td() {
        return id_td;
    }

    public void setId_td(int id_td) {
        this.id_td = id_td;
    }

    public int getDiemHT() {
        return diemHT;
    }

    public void setDiemHT(int diemHT) {
        this.diemHT = diemHT;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

}
