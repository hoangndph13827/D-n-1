/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.HDKhachHangModel;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HDKhachHangDAO implements QLDAO<HDKhachHangModel, Integer>{

    @Override
    public int insert(HDKhachHangModel e) {
        String sql="insert into hdkhachHang (id_hd,id_kh) values(?,?)";
        return JdbcHelper.executeUpdate(sql, e.getId_HD(),e.getId_kh());
    }

    @Override
    public int update(HDKhachHangModel e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HDKhachHangModel> select() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HDKhachHangModel findById(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HDKhachHangModel> select(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
