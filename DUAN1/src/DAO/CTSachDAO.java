/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.CTSachModel;
import MODEL.HoaDonModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CTSachDAO implements QLDAO<CTSachModel, String>{

    @Override
    public int insert(CTSachModel e) {
        String sql = "insert INTO CTSACH (ID,ID_SACH,ID_NN,ID_NXB,VITRI,TRANGTHAI,GIA,TAP,hinh,soLuong) values (?,?,?,?,?,?,?,?,?,?)";
        return JdbcHelper.executeUpdate(sql, e.getId(), e.getId_sach(), e.getId_NN(), e.getId_NXB(), e.getVitri(), e.getTrangThai(), e.getGia(), e.getTap(), e.getHinh(), e.getSoLuong());
    }

    @Override
    public int update(CTSachModel e) {
        String sql = "update CTSACH\n"
                + "set VITRI=?,GIA =?,TAP=?,hinh=?,soLuong=?\n"
                + "where ID = ?";
        return JdbcHelper.executeUpdate(sql, e.getVitri(), e.getGia(), e.getTap(), e.getHinh(), e.getSoLuong(), e.getId());
    }
    
    public int updateSL(CTSachModel cts){
        String sql="update ctsach set soLuong =? where id=?";
        System.out.println(cts.toString());
        return JdbcHelper.executeUpdate(sql, cts.getSoLuong(),cts.getId());
    }

    @Override
    public void delete(String k) {
        String sql ="delete from CTSACH where ID Like ?";
        JdbcHelper.executeUpdate(sql,k);
    }

    @Override
    public List<CTSachModel> select() {
        String sql= "select * from ctsach ";
        return select(sql);
    }
    

    @Override
    public CTSachModel findById(String k) {
        String sql= "select * from CTsach where id=?";
        List<CTSachModel> lst=select(sql, k);
        return lst.size()<1?null:lst.get(0);
    }
    
    public List<CTSachModel> findByKeyWord(String k) {
        String sql= "select DISTINCT CTSACH.id,CTSACH.ID_SACH,ID_NXB,VITRI,GIA,TRANGTHAI,soLuong,SOTRANG,TAP,hinh,ID_NN from CTSACH \n" +
"	join SACH on SACH.ID= CTSACH.ID_SACH \n" +
"       join SACHTG on SACH.ID= SACHTG.ID_SACH\n" +
"	join TACGIA on TACGIA.ID= SACHTG.ID_TG\n" +
"	join SACHTL on SACH.ID= SACHTL.ID_SACH \n" +
"	join THELOAI on SACHTL.ID_TL= THELOAI.ID\n" +
"	where sach.TENSACH like ? or tacgia.tentg like ?  or theloai.tentl like ?";
        List<CTSachModel> lst=select(sql, k,k,k) ;
        return lst.size()<1?null:lst;
    }
    
    @Override
    public List<CTSachModel> select(String sql, Object... args) {
        List<CTSachModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    CTSachModel model=readFromResultSet(rs);
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
    
    private CTSachModel readFromResultSet(ResultSet rs) throws SQLException{
        CTSachModel model=new CTSachModel();
        model.setGia(rs.getDouble("gia"));
        model.setId(rs.getString("id"));
        model.setId_NN(rs.getInt("id_nn"));
        model.setId_NXB(rs.getInt("id_nxb"));
        model.setId_sach(rs.getInt("id_sach"));
        model.setSoLuong(rs.getInt("soLuong"));
        model.setSoTrang(rs.getInt("soTrang"));
        model.setTap(rs.getString("tap"));
        model.setHinh(rs.getString("hinh"));
        model.setVitri(rs.getString("vitri"));
        model.setTrangThai(rs.getString("TrangThai"));
        return model;
    }
    
}
