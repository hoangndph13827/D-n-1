/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.HoaDonModel;
import MODEL.UserModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Admin
 */
public class UserDAO implements QLDAO<UserModel, String> {

    @Override
    public int insert(UserModel e) {

        String sql = "insert into USERS (MAUSER,HOVATEN,SDT,EMAIL,CMND,NgaySinh,GioiTinh,ID_VT,DIACHI,MATKHAU,HINH,trangthai) \n"
                + "values(?,?,?,?,?,?,?,?,?,?,?)";

        return JdbcHelper.executeUpdate(sql, e.getMa(), e.getHoten(), e.getSdt(), e.getEmail(), e.getCmnd(), e.getNgaySinh(), e.isGioitinh(), e.getId_vt(), e.getDiachi(), e.getMatKhau(), e.getHinh(),e.isChon());
    }

    @Override
    public int update(UserModel e) {
        
          String sql = "update dbo.[USERS]\n"
                + "set [HOVATEN] =? ,[SDT] =? ,[EMAIL] =?,[CMND] =? ,[NgaySinh] =? ,[GioiTinh] =? ,[ID_VT] =? ,[DIACHI] =? ,[MATKHAU] =? ,[HINH] =?,[trangthai] = ?\n"
                + "where [MAUSER] =?";
        int so = JdbcHelper.executeUpdate(sql, e.getHoten(), e.getSdt(), e.getEmail(), e.getCmnd(),
                e.getNgaySinh(), e.isGioitinh(), e.getId_vt(), e.getDiachi(), 
                e.getMatKhau(), e.getHinh(),e.isChon(),e.getMa());  
         return so; 
    }

    @Override
    public void delete(String k) {
        String sql = "update dbo.[USERS]\n"
                + "set [trangthai] = 0\n"
                + "where [MAUSER] =?";
        JdbcHelper.executeUpdate(sql, k);
    }

    @Override
    public List<UserModel> select() {
        String sql = "Select * from users";
        return select(sql);
    }

    @Override
    public UserModel findById(String k) {
        String sql = "Select * from users where mauser=?";
        List<UserModel> list = select(sql, k);
        return list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public List<UserModel> select(String sql, Object... args) {
        List<UserModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    UserModel model = readFromResultSet(rs);
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

    private UserModel readFromResultSet(ResultSet rs) throws SQLException {
        UserModel model = new UserModel();
        model.setCmnd(rs.getString("cmnd"));
        model.setEmail(rs.getString("email"));
        model.setGioitinh(rs.getBoolean("Gioitinh"));
        model.setHinh(rs.getString("Hinh"));
        model.setHoten(rs.getString("hoVaTen"));
        model.setId_vt(rs.getInt("id_vt"));
        model.setDiachi(rs.getString("DiaChi"));
        model.setMatKhau(rs.getString("matKhau"));
        model.setNgaySinh(rs.getDate("ngaySinh"));
        model.setSdt(rs.getString("sdt"));
        model.setMa(rs.getString("mauser"));
        model.setChon(rs.getBoolean("trangthai"));
        return model;
    }

    public List<UserModel> timkiem(String timkiem) {
        String sql = "Select * from users where hovaten like ?";
        return select(sql,"%" + timkiem +"%");
    }
    public void maUser(JLabel lbl){
        try {
            String sql ="select MAX(MAUSER) from USERS";
            ResultSet rs = JdbcHelper.executeQuery(sql);
            while (rs.next()) {                
//                if(rs.getString(1) == null){
//                    lbl.setText("PH000001");
//                }else{
//                    long id = Long.parseLong(rs.getString(1).substring(2, rs.getString(1).length()));
//                    id++;
//                    lbl.setText("PH"+String.format("%06d", id));
//                }
            }
            rs.getStatement().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
