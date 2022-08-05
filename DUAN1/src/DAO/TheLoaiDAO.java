/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.HoaDonModel;
import MODEL.TheLoaiModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TheLoaiDAO  implements QLDAO<TheLoaiModel, Integer>{

    @Override
    public int insert(TheLoaiModel e) {
        String  sql ="insert into THELOAI (TENTL ) values (?)";
        return  JdbcHelper.executeUpdate(sql,e.getTenTL());
    }

    @Override
    public int update(TheLoaiModel e) {
        String sql = "update THELOAI set TENTL = ? where ID = ?";
        return  JdbcHelper.executeUpdate(sql, e.getTenTL(), e.getId());
    }

    @Override
    public void delete(Integer k) {
        String sql = "delete from THELOAI where ID like ?";
        JdbcHelper.executeUpdate(sql, k);
    }

    @Override
    public List<TheLoaiModel> select() {
        String sql ="SELECT * FROM dbo.THELOAI ";
        return select(sql);
    }
    public List<TheLoaiModel> selectByKeyWord(String tenTL) {
        String sql ="SELECT * FROM dbo.THELOAI where tenTL like ?";
        return select(sql,tenTL);
    }
    public List<TheLoaiModel> findByIdSach(Integer k) {
        String sql= "select THELOAI.id,THELOAI.TENTL from theloai join sachtl on SACHTL.ID_TL = THELOAI.ID\n" +
        "join SACH on SACH.ID= SACHTL.ID_SACH where sach.ID = ?";
        List<TheLoaiModel> lst= select(sql,k);
        return lst.size()<1?null:lst;
    }
    
    @Override
    public TheLoaiModel findById(Integer k) {
        String sql= "select * from theloai where ID like ?";
        return select(sql,k).get(0);
    }
    
    public TheLoaiModel findByIdTL(Integer k) {
        String sql= "select * from theloai where id =? ";
        return select(sql,k).get(0);
    }
    
    public List<TheLoaiModel> findByKyeWord(String k) {
        String sql= "select * from THELOAI where TENTL like ?";
        return select(sql,k);
    }
    
    public List<TheLoaiModel>  findByIdCts(String id_cts){
        String sql="select theloai.id,theloai.tentl from theloai join sachtl on sachtl.ID_TL= THELOAI.ID\n" +
"	join SACH on sach.ID= SACHTL.ID_SACH\n" +
"	join CTSACH on SACH.id= CTSACH.ID_SACH\n" +
"	where CTSACH.id= ? ";
        return select(sql, id_cts);
    }
    @Override
    public List<TheLoaiModel> select(String sql, Object... args) {
       List<TheLoaiModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    TheLoaiModel model=readFromResultSet(rs);
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
    private TheLoaiModel readFromResultSet(ResultSet rs) throws SQLException{
        TheLoaiModel model=new TheLoaiModel();
        model.setTenTL(rs.getString("tenTL"));
        model.setId(rs.getInt("id"));
        return model;
    }
    
}
