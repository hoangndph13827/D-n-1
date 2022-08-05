/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author Admin
 */
public class CTSachModel {
    private String id,vitri,trangThai,hinh;
    private double gia;
    private String tap;
    private int id_sach,id_NXB,id_NN,soLuong,soTrang;
    
    public CTSachModel(String id, int sl) {
        this.id = id;
        this.soLuong = sl;
    }

    public CTSachModel() {
    }

    @Override
    public String toString() {
        return "CTSachModel{" + "id=" + id + ", vitri=" + vitri + ", trangThai=" + trangThai + ", gia=" + gia + ", id_sach=" + id_sach + ", id_NXB=" + id_NXB + ", id_NN=" + id_NN + ", soLuong=" + soLuong + ", tap=" + tap + ", soTrang=" + soTrang + '}';
    }

    public CTSachModel(String id, String vitri, String trangThai, String hinh, double gia, String tap, int id_sach, int id_NXB, int id_NN, int soLuong, int soTrang) {
        this.id = id;
        this.vitri = vitri;
        this.trangThai = trangThai;
        this.hinh = hinh;
        this.gia = gia;
        this.tap = tap;
        this.id_sach = id_sach;
        this.id_NXB = id_NXB;
        this.id_NN = id_NN;
        this.soLuong = soLuong;
        this.soTrang = soTrang;
    }

    
    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVitri() {
        return vitri;
    }

    public void setVitri(String vitri) {
        this.vitri = vitri;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public int getId_sach() {
        return id_sach;
    }

    public void setId_sach(int id_sach) {
        this.id_sach = id_sach;
    }

    public int getId_NXB() {
        return id_NXB;
    }

    public void setId_NXB(int id_NXB) {
        this.id_NXB = id_NXB;
    }


    public int getId_NN() {
        return id_NN;
    }

    public void setId_NN(int id_NN) {
        this.id_NN = id_NN;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTap() {
        return tap;
    }

    public void setTap(String tap) {
        this.tap = tap;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }
    
}
