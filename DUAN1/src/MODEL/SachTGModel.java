/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author admin
 */
public class SachTGModel {
    private int ID_Sach ;
    private int ID_TG ;

    public SachTGModel() {
    }

    public SachTGModel(int ID_Sach, int ID_TG) {
        this.ID_Sach = ID_Sach;
        this.ID_TG = ID_TG;
    }
    

    public int getID_Sach() {
        return ID_Sach;
    }

    public void setID_Sach(int ID_Sach) {
        this.ID_Sach = ID_Sach;
    }

    public int getID_TG() {
        return ID_TG;
    }

    public void setID_TG(int ID_TG) {
        this.ID_TG = ID_TG;
    }
    
    
}
