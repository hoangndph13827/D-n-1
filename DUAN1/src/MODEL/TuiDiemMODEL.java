/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODEL;

/**
 *
 * @author THE PHONG
 */
public class TuiDiemMODEL {
    private int IDTD;
    private int DiemQD;
    private double Tiendoi;

    public TuiDiemMODEL() {
    }

    public TuiDiemMODEL(int IDTD, int DiemQD, double Tiendoi) {
        this.IDTD = IDTD;
        this.DiemQD = DiemQD;
        this.Tiendoi = Tiendoi;
    }

    public int getIDTD() {
        return IDTD;
    }

    public void setIDTD(int IDTD) {
        this.IDTD = IDTD;
    }

    public int getDiemQD() {
        return DiemQD;
    }

    public void setDiemQD(int DiemQD) {
        this.DiemQD = DiemQD;
    }

    public double getTiendoi() {
        return Tiendoi;
    }

    public void setTiendoi(double Tiendoi) {
        this.Tiendoi = Tiendoi;
    }
    
    
}
