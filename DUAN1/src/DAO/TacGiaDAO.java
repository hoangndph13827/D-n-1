/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import HELP.JdbcHelper;
import java.sql.SQLException;
import MODEL.TacGiaModel;
import MODEL.TheLoaiModel;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TacGiaDAO implements QLDAO<TacGiaModel, Integer> {

    @Override
    public int insert(TacGiaModel e) {
        String sql = "insert into TACGIA values (?,?,?,?)";
        return JdbcHelper.executeUpdate(sql,  e.getTenTG(), e.getDiaChi(), e.getNgaysinh(), e.getNgaymat());
    }

    @Override
    public int update(TacGiaModel e) {
        String sql = "update TACGIA set TENTG = ?, DIACHI = ?, NGAYSINH = ?, NGAYMAT = ? where ID = ?";
        return JdbcHelper.executeUpdate(sql, e.getTenTG(), e.getDiaChi(), e.getNgaysinh(), e.getNgaymat(), e.getID_TacGia());
    }

    @Override
    public void delete(Integer k) {
        String sql = "delete from TACGIA where ID = ?";
        JdbcHelper.executeUpdate(sql, k);
   }

    @Override
    public List<TacGiaModel> select() {
        String sql = "select * from TACGIA";
        return select(sql);
    }
    public List<TacGiaModel> selectByKeyWord(String tenTG) {
        String sql = "select * from TACGIA where tenTg like ?";
        return select(sql,tenTG);
    }
    
    @Override
    public TacGiaModel findById(Integer k) {
        String sql = "select*from TACGIA where ID Like ?";
        List<TacGiaModel> list = this.select(sql,k);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
     }
    public List<TacGiaModel> findByIdCts(String id_cts) {
        String sql = "select TACGIA.TENTG,TACGIA.ID,TACGIA.DIACHI,TACGIA.NGAYSINH,TACGIA.NGAYMAT from CTSACH\n" +
"	join SACH on SACH.ID= CTSACH.ID_SACH \n" +
"	join SACHTG on SACHTG.ID_SACH = SACH.id\n" +
"	join TACGIA on TACGIa.ID= SACHTG.ID_TG where ctsach.id=?";
        List<TacGiaModel> list = this.select(sql,id_cts);
        return list;
    }

    public List<TacGiaModel> findByIdSach(Integer k) {
        String sql = "select TACGIA.id,TACGIA.TENTG,TACGIA.DIACHI,TACGIA.NGAYSINH,TACGIA.NGAYMAT from TACGIA join SACHTG on SACHTG.ID_TG = TACGIA.id\n"
                + "	join SACH on SACH.ID= SACHTG.ID_SACH where sach.ID = ?";
        return select(sql, k);
    }

    @Override
    public List<TacGiaModel> select(String sql, Object... args) {
        List<TacGiaModel> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    TacGiaModel model=readFromResultSet(rs);
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
    private TacGiaModel readFromResultSet(java.sql.ResultSet rs) throws SQLException{
        TacGiaModel model=new TacGiaModel();
        model.setID_TacGia(rs.getInt("ID"));
        model.setTenTG(rs.getString("TENTG"));
        model.setDiaChi(rs.getString("DIACHI"));
        model.setNgaysinh(rs.getDate("NGAYSINH"));
        model.setNgaymat(rs.getDate("NGAYMAT"));
        return model;
    }

}
