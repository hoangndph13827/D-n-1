/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import HELP.JdbcHelper;
import MODEL.HoaDonModel;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Mr.Right
 */
public class ThongKeDAO {

    private List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>(); //danh sách chứa các mảng

            ResultSet rs = JdbcHelper.executeQuery(sql, args);
            while (rs.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);

                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object[]> getSanPhamtheongay(String batDau , String ketThuc ) {
        String sql = "{CALL sp_sanphamtheongay(?,?)}";
            String[] cols = {"TenSanPham", "soluongban", "doanhthu"};
        return this.getListOfArray(sql, cols,batDau,ketThuc);
    }
    public List<Object[]> getSanPhamtheothang(int batDau , int ketThuc ) {
        String sql = "{CALL sp_sanphamtheothang(?,?)}";
            String[] cols = {"TenSanPham", "soluongban", "doanhthu"};
        return this.getListOfArray(sql, cols,batDau,ketThuc);
    }
    public List<Object[]> getsanpham() {
        String sql = "{CALL sp_sanpham}";
        String[] cols = {"TenSanPham", "soluongban", "doanhthu",};
        return this.getListOfArray(sql, cols);
    }
    public List<Object[]> getNhanVientheongay(String batDau , String ketThuc ) {
        String sql = "{CALL sp_nhanvientheongay(?,?)}";
            String[] cols = {"MaNhanVien", "TenNhanVien", "soluongban", "DoanhThu"};
        return this.getListOfArray(sql, cols,batDau,ketThuc);
    }
    
    public List<Object[]> getNhanvientheothang(int batDau , int ketThuc ) {
        String sql = "{CALL sp_nhanvientheothang(?,?)}";
            String[] cols = {"MaNhanVien", "TenNhanVien", "soluongban", "DoanhThu"};
        return this.getListOfArray(sql, cols,batDau,ketThuc);
    }
    
    public List<Object[]> getNhanVien() {
        String sql = "{CALL sp_nhanvien}";
        String[] cols = {"MaNhanVien", "TenNhanVien", "soluongban", "DoanhThu"};
        return this.getListOfArray(sql, cols);
    }
    public List<Object[]> getThoiGiantheongay(String batDau , String ketThuc ) {
        String sql = "{CALL sp_thoigiantheongay(?,?)}";
            String[] cols = {"tennhanvien", "tenkhach", "soluongban", "giamgia","doanhthu","ngaymua"};
        return this.getListOfArray(sql, cols,batDau,ketThuc);
    }
    
    public List<Object[]> getThoiGiantheothang(int batDau , int ketThuc ) {
        String sql = "{CALL sp_thoigiantheothang(?,?)}";
            String[] cols = {"tennhanvien", "tenkhach", "soluongban", "giamgia","doanhthu","ngaymua"};
        return this.getListOfArray(sql, cols,batDau,ketThuc);
    }
    public List<Object[]> getThoiGian() {
        String sql = "{CALL sp_thoigian}";
        String[] cols = {"tennhanvien", "tenkhach", "soluongban", "giamgia","doanhthu","ngaymua"};
        return this.getListOfArray(sql, cols);
    }

    public int SoLuongBan(String date) {
        int sl = 0;
        try {
            String sql = "SELECT SUM(SLban) SoLuongDaBan\n"
                    + "FROM HOADON join CTHOADON  on HOADON.ID = CTHOADON.ID_HD\n"
                    + "where CONVERT(date,[NGAYMUA] )= ?";
            ResultSet rs = JdbcHelper.executeQuery(sql, date);
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
        }

        return sl;
    }

    public int TongHoaDon(String date) {
        int sl = 0;
        try {
            String sql = "SELECT COUNT(ID) tonghoadon\n" +
"                    FROM HOADON \n" +
"                    where CONVERT(date,[NGAYMUA] )= ?";
            ResultSet rs = JdbcHelper.executeQuery(sql, date);
            while (rs.next()) {
                sl = rs.getInt(1);
            }
        } catch (Exception e) {
        }

        return sl;
    }

    public double loiNhuan(String date) {
        double sl = 0;
        try {
            String sql = "SELECT Sum(THANHTIEN)\n" +
"                    FROM HOADON \n" +
"                    where  YEAR(NGAYMUA) = ? ";
            ResultSet rs = JdbcHelper.executeQuery(sql, date);
            while (rs.next()) {
                sl = rs.getDouble(1);
            }
        } catch (Exception e) {
        }

        return sl;
    }

    public double giamGia(String date) {
        double sl = 0;
        try {
            String sql = "SELECT SUM(giamGia) giamgia\n"
                    + "                    FROM HOADON join CTHOADON  on HOADON.ID = CTHOADON.ID_HD\n"
                    + "                    where CONVERT(date,[NGAYMUA] )= ? ";
            ResultSet rs = JdbcHelper.executeQuery(sql, date);
            while (rs.next()) {
                sl = rs.getDouble(1);
            }
        } catch (Exception e) {
        }

        return sl;
    }

    public double doanhThu(String date) {
        double sl = 0;
        try {
            String sql = "SELECT Sum(THANHTIEN) thanhtien\n"
                    + "FROM HOADON join CTHOADON  on HOADON.ID = CTHOADON.ID_HD\n"
                    + "where [NGAYMUA] = ?";
            ResultSet rs = JdbcHelper.executeQuery(sql, date);
            while (rs.next()) {
                sl = rs.getDouble(1);
            }
        } catch (Exception e) {
        }

        return sl;
    }

}
