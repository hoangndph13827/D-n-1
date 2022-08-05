/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.CTSachModel;
import MODEL.NXBModel;
import MODEL.NXBModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author THE PHONG
 */
public class NXBDAO implements QLDAO<NXBModel, Integer> {

    @Override
    public int insert(NXBModel e) {
        String sql = "insert into NXB (TENNXB,DIACHI,SDT,EMAIl) values (?,?,?,?)";
        return JdbcHelper.executeUpdate(sql, e.getTenNXB(), e.getDiaChi(), e.getSDT(), e.getEmail());
    }

    @Override
    public int update(NXBModel e) {
        String sql = "update NXB SET [TENNXB] =?,[DIACHI] =?,[SDT] =?,[EMAIl] =? where ID =?" ;
        return JdbcHelper.executeUpdate(sql, e.getTenNXB(), e.getDiaChi(), e.getSDT(), e.getEmail(),e.getId());
    }

    @Override
    public void delete(Integer k) {
        String sql = "delete from NXB where ID LIKE ?";
        JdbcHelper.executeUpdate(sql, k);
    }

    @Override
    public List<NXBModel> select() {
        String sql = "select *from NXB ";
        return select(sql);
    }

    @Override
    public NXBModel findById(Integer k) {
        String sql = "select * from NXB where ID LIKE ?";
        List<NXBModel> list = this.select(sql, k);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }

    }

    @Override
    public List<NXBModel> select(String sql, Object... args) {
        List<NXBModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    NXBModel model = readFromResultSet(rs);
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

    private NXBModel readFromResultSet(ResultSet rs) throws SQLException {
        NXBModel nxb = new NXBModel();
        nxb.setId(rs.getInt("ID"));
        nxb.setTenNXB(rs.getString("TENNXB"));
        nxb.setDiaChi(rs.getString("DIACHI"));
        nxb.setSDT(rs.getString("SDT"));
        nxb.setEmail(rs.getString("Email"));
        nxb.setGhiChu(rs.getString("GhiChu"));
        return nxb;

    }

    public List<NXBModel> selectTimKiem(String key) {
        String sql = "select *from NXB where TENNXB Like ?";

        return select(sql, "%" + key + "%");
    }

    public NXBModel findById(String tbl) {
         String sql = "select * from NXB where ID LIKE ?";
        List<NXBModel> list = this.select(sql, tbl);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public void delete(String manxb) {
        String sql = "delete from NXB where ID LIKE ?";
        JdbcHelper.executeUpdate(sql, manxb);
    }

   
}
