/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.CTSachModel;
import MODEL.SachModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SachDAO implements QLDAO<SachModel, Integer>{

    @Override
    public int insert(SachModel e) {
        String sql ="insert SACH (TENSACH)values (?)";
        return JdbcHelper.executeUpdate(sql, e.getTenSach());
    }

    @Override
    public int update(SachModel e) {
        String sql = " update SACH set TENSACH = ? where ID = ?";
        return JdbcHelper.executeUpdate(sql, e.getTenSach(), e.getId());
    }

    @Override
    public void delete(Integer k) {
        String sql ="delete from Sach where ID like ?";
       JdbcHelper.executeUpdate(sql,k);
    }

    @Override
    public List<SachModel> select() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SachModel findById(Integer k) {
        String sql= "select * from sach where id=?";
        List<SachModel> list = this.select(sql, k);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }
    
     public int selectMaxId() {
        String sql= "select max(id) as id from sach";
        ResultSet rs = JdbcHelper.executeQuery(sql);
         try {
             while (rs.next()) {             
             return rs.getInt(1);
         }
         } catch (Exception e) {
         }
         return 0;
    }

    @Override
    public List<SachModel> select(String sql, Object... args) {
        List<SachModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    SachModel model=readFromResultSet(rs);
                    list.add(model);
                }
            }
            finally{
                rs.getStatement().getConnection().close();
            }
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
    private SachModel readFromResultSet(ResultSet rs) throws SQLException{
        SachModel model=new SachModel();
        model.setId(rs.getInt("id"));
        model.setTenSach(rs.getString("TenSach")); 
        return model;
    }
    public SachModel findByKeyWord(String k) {
        String sql= "select * from sach where tensach =?";
        SachModel s;
        try {
              s= select(sql, k).get(0);
        } catch (Exception e) {
            return null;
        }
        return s ;
    }


}
