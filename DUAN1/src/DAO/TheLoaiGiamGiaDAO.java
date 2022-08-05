/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.TheLoaiGiamGiaModel;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class TheLoaiGiamGiaDAO implements QLDAO<TheLoaiGiamGiaModel, Integer>{

   

    @Override
    public List<TheLoaiGiamGiaModel> select(String sql, Object... args) {
        List<TheLoaiGiamGiaModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    TheLoaiGiamGiaModel model = readFromResultSet(rs);
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
    
    private TheLoaiGiamGiaModel readFromResultSet(ResultSet rs) throws SQLException {
        TheLoaiGiamGiaModel model = new TheLoaiGiamGiaModel();
        model.setId_TL(rs.getInt("id_tl"));
        model.setId_gg(rs.getInt("id_gg"));
        
        return model;
    }

    @Override
    public int insert(TheLoaiGiamGiaModel e) {
        String sql= "insert into TLgiamGia(id_tl,id_gg) values(?,?) ";
        return JdbcHelper.executeUpdate(sql, 
                e.getId_TL(),
                e.getId_gg()
                );
    }

    @Override
    public int update(TheLoaiGiamGiaModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        String sql= "delete TLgiamGia where id_gg=?";
        JdbcHelper.executeUpdate(sql, k);
    }

    @Override
    public List<TheLoaiGiamGiaModel> select() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TheLoaiGiamGiaModel findById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<TheLoaiGiamGiaModel> findByIdGG(Integer k) {
        String sql= "Select * from tlGiamGia where id_gg=?";
        return select(sql, k);
    }
}
