/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.KhachHangModel;
import MODEL.UserModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class KhachHangDAO implements QLDAO<KhachHangModel, Integer>{

    @Override
    public int insert(KhachHangModel e) {
        String sql = "insert into KHACHHANG(ID,TENKH,SDT,Ngaysinh,Gioitinh,Email,DiaChi,IDTD)"
                + " values(?,?,?,?,?,?,?,?)";
        return JdbcHelper.executeUpdate(sql, e.getId(),
                e.getTenKH(),
                e.getSdt(),
                e.getNgaySinh(),
                e.isGioiTinh(),
                e.getEmail(),
                e.getDiaChi(),
                e.getId_td());
    }

    @Override
    public int update(KhachHangModel e) {
        String sql = "update dbo.KHACHHANG set TENKH =? ,SDT =? ,Ngaysinh =?,Gioitinh =? ,Email =? ,DiaChi =? \n"
                + "                where ID =?";
        return JdbcHelper.executeUpdate(sql, e.getTenKH(),
                e.getSdt(),
                e.getNgaySinh(),
                e.isGioiTinh(),
                e.getEmail(),
                e.getDiaChi(),
                e.getId());
    }
    
    public int updateDiem(KhachHangModel e){
        String sql = "update dbo.KHACHHANG set diemHT=?  where ID =?";
        return JdbcHelper.executeUpdate(sql, 
                e.getDiemHT(),
                e.getId());
    }
    

    @Override
    public void delete(Integer k) {
        String sql ="delete from dbo.[KHACHHANG] where ID = ?";
        JdbcHelper.executeUpdate(sql, k);
    }

    @Override
    public List<KhachHangModel> select() {
        String sql = "SELECT KHACHHANG.ID,TENKH,SDT,Ngaysinh,Gioitinh,Email,DiaChi,DiemHT,DiemHT * Tiendoi as GiamGia\n" +
"                from KHACHHANG JOIN TUIDIEM ON TUIDIEM.IDTD = KHACHHANG.IDTD";
       return select(sql);
    }

    @Override
    public KhachHangModel findById(Integer k) {
        String sql= "select * from khachHang where id=?";
        List<KhachHangModel> list = select(sql, k);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    public KhachHangModel findBySDT(String k) {
        String sql= "select * from khachHang where sdt=?";
        List<KhachHangModel> list = select(sql, k);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<KhachHangModel> select(String sql, Object... args) {
        List<KhachHangModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    KhachHangModel model=readFromResultSet(rs);
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
    
    
    private KhachHangModel readFromResultSet(ResultSet rs) throws SQLException{
        KhachHangModel model=new KhachHangModel();
        model.setId(rs.getInt("id"));
        model.setDiaChi(rs.getString("diachi"));
        model.setSdt(rs.getString("sdt"));
        model.setTenKH(rs.getString("tenKH"));
        model.setDiemHT(rs.getInt("DiemHT")); 
        model.setEmail(rs.getString("email"));
        model.setGioiTinh(rs.getBoolean("gioiTinh")); 
        model.setTenKH(rs.getString("tenKH"));
        model.setSdt(rs.getString("sdt"));
        model.setNgaySinh(rs.getDate("ngaySinh"));
        model.setGiamGia(rs.getInt("giamGia"));
        return model;
    }
    
}
