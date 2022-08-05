/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author THE PHONG
 */
public class NXBModel {

    private int id;
    private String tenNXB, DiaChi;
    private String Email;
    private String SDT;
    private String GhiChu;

    public NXBModel() {
    }

    public NXBModel(int id, String tenNXB, String DiaChi, String Email, String SDT, String GhiChu) {
        this.id = id;
        this.tenNXB = tenNXB;
        this.DiaChi = DiaChi;
        this.Email = Email;
        this.SDT = SDT;
        this.GhiChu = GhiChu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNXB() {
        return tenNXB;
    }

    public void setTenNXB(String tenNXB) {
        this.tenNXB = tenNXB;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    @Override
    public String toString() {
        return tenNXB ;
    }

        

}
