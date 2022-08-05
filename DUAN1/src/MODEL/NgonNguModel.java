/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author Mr.Right
 */
public class NgonNguModel {
    private int ID;
    private String ngonNgu ;

    public NgonNguModel() {
    }

    public NgonNguModel(int ID, String ngonNgu) {
        this.ID = ID;
        this.ngonNgu = ngonNgu;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNgonNgu() {
        return ngonNgu;
    }

    public void setNgonNgu(String ngonNgu) {
        this.ngonNgu = ngonNgu;
    }

    @Override
    public String toString() {
        return  ngonNgu ;
    }
    
    
}
