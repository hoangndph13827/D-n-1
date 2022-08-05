/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.GiamGiaModel;
import MODEL.SachTheLoaiModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class GiamGiaDAO implements QLDAO<GiamGiaModel, Float> {

    @Override
    public int insert(GiamGiaModel e) {
        String sql = "insert into GIAMGIA (NGAYAPDUNG,NGAYKETTHUC,tenChuongTrinh,TRIETKHAU)"
                + " values(?,?,?,?)";
        return JdbcHelper.executeUpdate(sql,
                e.getNgayApDung(),
                e.getNgayKetThuc(),
                e.getTenChuongTrnh(),
                e.getGiamGia()
        );
    }

    @Override
    public int update(GiamGiaModel e) {
        String sql= "update giamGia set NGAYAPDUNG =?,NGAYKETTHUC =?,tenChuongTrinh=? ,TRIETKHAU=? where id=?";
        return JdbcHelper.executeUpdate(sql, 
                e.getNgayApDung(),
                e.getNgayKetThuc(),
                e.getTenChuongTrnh(),
                e.getGiamGia(),
                e.getId());
    }

    public void updateTrangThai(GiamGiaModel e){
        String sql = "update giamGia set trangThai =? where id=? ";
        JdbcHelper.executeUpdate(sql, e.isTrangThai(),
                e.getId());
    }
    @Override
    public void delete(Float k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<GiamGiaModel> select() {
        String sql = "select * from giamGia";
        return select(sql);
    }

    public List<GiamGiaModel> selectDangApDung() {
        String sql = "select * from giamGia where getDate() between ngayApdung and NgayKetThuc";
        return select(sql);
    }

    public List<GiamGiaModel> selectSapDienRa() {
        String sql = "select * from giamGia where getDate() < ngayApDung ";
        return select(sql);
    }

    @Override
    public GiamGiaModel findById(Float k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public GiamGiaModel findByIdSach(Float k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int findByIdCTSSach(String id_ctSach) {
        String sql = "select max(trietKhau) as trietKhau from TLGIAMGIA join  THELOAI on THELOAI.Id= TLGIAMGIA.ID_TL\n"
                + "                	join SACHtl on sachtl.ID_TL = THELOAI.ID\n"
                + "                	join SACH on SACH.ID= SACHTL.ID_SACH\n"
                + "                	join CTSACH on SACH.ID= CTSACH.ID_SACH\n"
                + "			join GIAMGIA on GIAMGIA.id= TLGIAMGIA.ID_GG\n"
                + "			group by trietKhau,NGAYAPDUNG,NGAYKETTHUC,GIAMGIA.trangThai,CTSACH.id\n"
                + "			having CTSACH.id= ? and (getDate()  between NGAYAPDUNG and NGAYKETTHUC) and  GIAMGIA.trangThai=1";
        int gg= 0;
        ResultSet rs= JdbcHelper.executeQuery(sql,id_ctSach);
        try {
            while (rs.next()) gg=rs.getInt(1);
        } catch (Exception e) {
        }
        return gg;
    }

    @Override
    public List<GiamGiaModel> select(String sql, Object... args) {
        List<GiamGiaModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    GiamGiaModel model = readFromResultSet(rs);
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

    public int selectIdMax() throws SQLException {
        String sql = "select max(id) from GiamGia";
        ResultSet rs = JdbcHelper.executeQuery(sql);
        while (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    private GiamGiaModel readFromResultSet(ResultSet rs) throws SQLException {
        GiamGiaModel model = new GiamGiaModel();
        model.setGiamGia(rs.getFloat("trietKhau"));
        model.setId(rs.getInt("id"));
        model.setNgayApDung(rs.getDate("ngayapdung"));
        model.setNgayKetThuc(rs.getDate("NgayKetThuc"));
        model.setTenChuongTrnh(rs.getString("tenChuongTrinh"));
        model.setTrangThai(rs.getBoolean("TrangThai"));
        return model;
    }

}
