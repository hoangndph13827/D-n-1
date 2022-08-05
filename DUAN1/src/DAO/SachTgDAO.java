/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.SachTGModel;
import MODEL.SachTheLoaiModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class SachTgDAO implements QLDAO<SachTGModel, Integer>{

    @Override
    public int insert(SachTGModel e) {
        String sql =" insert into SACHTG (ID_Sach, ID_TG) values (?,?)";
        return  JdbcHelper.executeUpdate(sql, e.getID_Sach(),e.getID_TG());
    }

    @Override
    public int update(SachTGModel e) {
         String sql ="update SACHTG set [ID_Sach]=?, [ID_TG]=?";
        return  JdbcHelper.executeUpdate(sql, e.getID_Sach(),e.getID_TG());
    }

    @Override
    public void delete(Integer k) {
        String sql ="delete * from SACHTG where ID_Sach =?";
         JdbcHelper.executeUpdate(sql,k);
    }

    @Override
    public List<SachTGModel> select() {
        String sql ="select * from SACHTG ";
        return select(sql);
    }

    @Override
    public SachTGModel findById(Integer k) {
        String sql ="select * from SACHTG where ID_SACH =?";
        return select(sql,k).get(0);
    }

    @Override
    public List<SachTGModel> select(String sql, Object... args) {
       List<SachTGModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    SachTGModel model=readFromResultSet(rs);
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
    
     
    private SachTGModel readFromResultSet(ResultSet rs) throws SQLException{
        SachTGModel model=new SachTGModel();
        model.setID_Sach(rs.getInt("ID_Sach"));
        model.setID_TG(rs.getInt("ID_TG"));
        return model;
    }
    
}
