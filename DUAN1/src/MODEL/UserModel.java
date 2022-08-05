/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import java.awt.Checkbox;
import java.util.Date;

/**
 *
 * @author Admin
 */
public class UserModel {
    private String ma,hoten,sdt,email,cmnd,hinh,matKhau, diachi;
    private boolean  chon,gioitinh;
    private Date ngaySinh;
    private int id_vt;
    private Checkbox catnhap, xoa;

    public UserModel() {
    }

    public UserModel(String ma, String hoten, String sdt, String email, String cmnd, String hinh, String matKhau, String diachi, boolean chon, boolean gioitinh, Date ngaySinh, int id_vt, Checkbox catnhap, Checkbox xoa) {
        this.ma = ma;
        this.hoten = hoten;
        this.sdt = sdt;
        this.email = email;
        this.cmnd = cmnd;
        this.hinh = hinh;
        this.matKhau = matKhau;
        this.diachi = diachi;
        this.chon = chon;
        this.gioitinh = gioitinh;
        this.ngaySinh = ngaySinh;
        this.id_vt = id_vt;
        this.catnhap = catnhap;
        this.xoa = xoa;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public boolean isChon() {
        return chon;
    }

    public void setChon(boolean chon) {
        this.chon = chon;
    }

    public boolean isGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public int getId_vt() {
        return id_vt;
    }

    public void setId_vt(int id_vt) {
        this.id_vt = id_vt;
    }

    public Checkbox getCatnhap() {
        return catnhap;
    }

    public void setCatnhap(Checkbox catnhap) {
        this.catnhap = catnhap;
    }

    public Checkbox getXoa() {
        return xoa;
    }

    public void setXoa(Checkbox xoa) {
        this.xoa = xoa;
    }

    
   public String gioitinh(boolean gioiTinh){
       boolean gioitinh = true;
       if(gioiTinh){
           return "Nam";
       }else{
           return "Nữ";
       }
   }
    public String vaitro(int id_vt){
       if(id_vt ==1){
           return "Nhân viên";
       }else{
           return "Trưởng phòng";
       }
   }
    public boolean trangthai(boolean Chon){
       boolean chon = true;
       if(Chon){
           return true;
       }else{
           return false;
       }
   }
   
    

    
    
}
