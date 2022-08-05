/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.CTHoaDonModel;
import MODEL.SachTheLoaiModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SachTheLoaiDAO implements QLDAO<SachTheLoaiModel, Integer>{

    @Override
    public int insert(SachTheLoaiModel e) {
         String sql =" insert into SACHTL (ID_SACH, ID_TL) values (?,?)";
        return  JdbcHelper.executeUpdate(sql, e.getId_sach(),e.getId_TL());
    }

    @Override
    public int update(SachTheLoaiModel e) {
         String sql ="update SACHTL set [ID_Sach]=?, [ID_TL]=?";
        return  JdbcHelper.executeUpdate(sql, e.getId_sach(),e.getId_TL());
    }

    @Override
    public void delete(Integer k) {
          String sql ="delete * from SACHTL where ID_Sach =?";
         JdbcHelper.executeUpdate(sql,k);
    }

    @Override
    public List<SachTheLoaiModel> select() {
           String sql ="select * from SACHTL ";
        return select(sql);
    }

    @Override
    public SachTheLoaiModel findById(Integer k) {
       String sql ="select * from SACHTL where ID_SACH =?";
        return select(sql,k).get(0);
    }

    @Override
    public List<SachTheLoaiModel> select(String sql, Object... args) {
        List<SachTheLoaiModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    SachTheLoaiModel model=readFromResultSet(rs);
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
    
    private SachTheLoaiModel readFromResultSet(ResultSet rs) throws SQLException{
        SachTheLoaiModel model=new SachTheLoaiModel();
        model.setId_TL(rs.getInt("id_tl"));
        model.setId_sach(rs.getInt("id_sach"));       
        return model;
    }
    
}
