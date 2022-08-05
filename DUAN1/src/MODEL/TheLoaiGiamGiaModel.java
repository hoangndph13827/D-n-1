/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author Admin
 */
public class TheLoaiGiamGiaModel {
    private int id_gg,id_TL;

    public TheLoaiGiamGiaModel(int id_gg, int id_TL) {
        this.id_gg = id_gg;
        this.id_TL = id_TL;
    }

    public TheLoaiGiamGiaModel() {
    }

    public int getId_gg() {
        return id_gg;
    }

    public void setId_gg(int id_gg) {
        this.id_gg = id_gg;
    }

    public int getId_TL() {
        return id_TL;
    }

    public void setId_TL(int id_TL) {
        this.id_TL = id_TL;
    }
    
}
