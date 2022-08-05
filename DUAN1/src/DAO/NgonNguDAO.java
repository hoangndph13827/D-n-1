/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.NgonNguModel;
import MODEL.NgonNguModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class NgonNguDAO implements QLDAO<NgonNguModel, Integer> {

    @Override
    public int insert(NgonNguModel e) {
        String sql ="insert into NGONNGU (TENNGONNGU) values (?)";
        return  JdbcHelper.executeUpdate(sql,e.getNgonNgu());
    }

    @Override
    public int update(NgonNguModel e) {
        String sql = "update NGONNGU SET [TENNGONNGU] =? where ID =?" ;
        return JdbcHelper.executeUpdate(sql, e.getNgonNgu(),e.getID());
    }

    @Override
    public void delete(Integer k) {
          String sql = "delete from NGONNGU where ID LIKE ?";
        JdbcHelper.executeUpdate(sql, k);
    }

    @Override
    public List<NgonNguModel> select() {
        String sql ="SELECT * FROM dbo.NGONNGU ";
        return select(sql);
    }

    @Override
    public NgonNguModel findById(Integer k) {
        String sql= "select * from ngonNgu where id like ?";
        return select(sql, k).get(0);
    }

    @Override
    public List<NgonNguModel> select(String sql, Object... args) {
        List<NgonNguModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    NgonNguModel model = readFromResultSet(rs);
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

      private NgonNguModel readFromResultSet(ResultSet rs) throws SQLException{
        NgonNguModel model = new NgonNguModel();
        model.setID(rs.getInt("ID"));
        model.setNgonNgu(rs.getString("tenngonngu"));
        return model;
      }
 public List<NgonNguModel> selectTimKiem(String key) {
        String sql = "select *from NGONNGU where TENNGONNGU Like ?";

        return select(sql, "%" + key + "%");
    }

    public NgonNguModel findById(String tbl) {
          String sql= "select * from ngonNgu where id like ?";
        return select(sql, tbl).get(0);
    }

    public void delete(String manxb) {
        String sql = "delete from NGONNGU where ID LIKE ?";
        JdbcHelper.executeUpdate(sql, manxb);
    }
}
