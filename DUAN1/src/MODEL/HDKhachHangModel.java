/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author Admin
 */
public class HDKhachHangModel {
    private int id_HD,id_kh;

    public HDKhachHangModel() {
    }

    public HDKhachHangModel(int id_HD, int id_kh) {
        this.id_HD = id_HD;
        this.id_kh = id_kh;
    }
    
    
    public int getId_HD() {
        return id_HD;
    }

    public void setId_HD(int id_HD) {
        this.id_HD = id_HD;
    }

    public int getId_kh() {
        return id_kh;
    }

    public void setId_kh(int id_kh) {
        this.id_kh = id_kh;
    }
    
}
