/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

import java.util.Date;

/**
 *
 * @author admin
 */
public class TacGiaModel {

   private int ID_TacGia;
    private String TenTG;
    private String DiaChi;
    private Date ngaysinh;
    private Date ngaymat;

    public TacGiaModel() {
    }

    public int getID_TacGia() {
        return ID_TacGia;
    }

    public void setID_TacGia(int ID_TacGia) {
        this.ID_TacGia = ID_TacGia;
    }

    public String getTenTG() {
        return TenTG;
    }

    public void setTenTG(String TenTG) {
        this.TenTG = TenTG;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public Date getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(Date ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public Date getNgaymat() {
        return ngaymat;
    }

    public void setNgaymat(Date ngaymat) {
        this.ngaymat = ngaymat;
    }

    @Override
    public String toString() {
        return TenTG ;
    }

    

}
