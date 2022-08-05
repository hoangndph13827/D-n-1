/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.KhachHangModel;
import java.util.List;
import MODEL.TuiDiemMODEL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author THE PHONG
 */
public class TuiDiemDAO implements QLDAO<TuiDiemMODEL, String >{

    @Override
    public int insert(TuiDiemMODEL e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int update(TuiDiemMODEL e) {
        String sql = "update TUIDIEM\n"
                + "set Tiendoi = ?\n"
                + "where IDTD = '120'";
        return JdbcHelper.executeUpdate(sql, e.getTiendoi());
    }
 
    @Override
    public void delete(String k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TuiDiemMODEL> select() {
        String sql = "SELECT * FROM TUIDIEM";
        return select(sql);
    }

    @Override
    public TuiDiemMODEL findById(String k) {
        String sql = "SELECT IDTD FROM TUIDIEM";
        return select(sql).get(0); 
    }
    
    

    @Override
    public List<TuiDiemMODEL> select(String sql, Object... args) {
          List<TuiDiemMODEL> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    TuiDiemMODEL model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

      private TuiDiemMODEL readFromResultSet(ResultSet rs) throws SQLException {
        TuiDiemMODEL model = new TuiDiemMODEL();
        model.setIDTD(rs.getInt("IDTD"));
        model.setDiemQD(rs.getInt("DiemQD"));
        model.setTiendoi(rs.getDouble("Tiendoi"));
       
        return model;
    }
    public int hienThi(){
         int sl = 0;
        try {
            String sql = "SELECT IDTD FROM TUIDIEM";
            ResultSet rs = JdbcHelper.executeQuery(sql);
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
        }

        return sl;
    }

       public int hienThi1(){
         int sl = 0;
        try {
            String sql = "SELECT DiemQD FROM TUIDIEM";
            ResultSet rs = JdbcHelper.executeQuery(sql);
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
        }

        return sl;
    }
   
}
