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
public class GiamGiaModel {
    private float giamGia=0;
    private int id;
    private Date ngayApDung,ngayKetThuc;
    private String tenChuongTrnh;
    private boolean trangThai;

    public GiamGiaModel() {
    }

    public GiamGiaModel(int id,int giamGia, Date ngayApDung, Date ngayKetThuc, String tenChuongTrnh, boolean trangThai) {
        this.id = id;
        this.ngayApDung = ngayApDung;
        this.ngayKetThuc = ngayKetThuc;
        this.tenChuongTrnh = tenChuongTrnh;
        this.trangThai = trangThai;
        this.giamGia=giamGia;
    }

    @Override
    public String toString() {
        return "GiamGiaModel{" + "giamGia=" + giamGia + ", id=" + id + ", ngayApDung=" + ngayApDung + ", ngayKetThuc=" + ngayKetThuc + ", tenChuongTrnh=" + tenChuongTrnh + ", trangThai=" + trangThai + '}';
    }

    
 
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getNgayApDung() {
        return ngayApDung;
    }

    public void setNgayApDung(Date ngayApDung) {
        this.ngayApDung = ngayApDung;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getTenChuongTrnh() {
        return tenChuongTrnh;
    }

    public void setTenChuongTrnh(String tenChuongTrnh) {
        this.tenChuongTrnh = tenChuongTrnh;
    } 
    public float getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(float giamGia) {
        this.giamGia = giamGia;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
    
}
