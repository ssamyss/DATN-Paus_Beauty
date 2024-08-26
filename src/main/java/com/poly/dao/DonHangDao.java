package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.DonHang;

public interface DonHangDao extends JpaRepository<DonHang, String> {

	@Query("SELECT count(o) FROM DonHang o")
	Integer getCountDH();
	
	@Query("SELECT SUM(dh.tongGia) FROM DonHang dh WHERE dh.trangThai = 'HOAN_TAT'")
	Double getCountTongDT();
	
	@Query("SELECT count(dh) FROM DonHang dh WHERE dh.trangThai = 'HUY_DON'")
	Integer getCountHuyDon();

//	@Query("SELECT dh.maDH, tk.HoVaTen, " +
//	           "STRING_AGG(sp.tenSP, ', '), " +
//	           "SUM(dht.soLuong), " +
//	           "SUM(dht.gia * dht.soLuong) " +
//	           "FROM DonHang dh " +
//	           "JOIN dh.donHangChiTiet dht " +
//	           "JOIN dht.sanPham sp " +
//	           "JOIN dh.taiKhoan tk " +
//	           "GROUP BY dh.maDH, tk.HoVaTen")
//	List<Object[]> getOrderSummary();
	
	@Query("SELECT dh from DonHang dh where dh.taiKhoan.TenTaiKhoan = :tentaikhoan")
	List<DonHang> getOrders(@Param("tentaikhoan") String tentaikhoan);
	
	@Query("SELECT MONTH(dh.createDate), SUM(dh.tongGia) FROM DonHang dh WHERE dh.trangThai = 'HOAN_TAT' GROUP BY MONTH(dh.createDate)")
	List<Object[]> getMonthlyRevenue();

	@Query("SELECT QUARTER(dh.createDate), SUM(dh.tongGia) FROM DonHang dh WHERE dh.trangThai = 'HOAN_TAT' GROUP BY QUARTER(dh.createDate)")
    List<Object[]> getQuarterlyRevenue();
    
    @Query("SELECT dh FROM DonHang dh WHERE dh.trangThai = 'DANG_XU_LY'")
    List<DonHang> findAllByTrangThaiDangXuLy();
    
    @Query("SELECT dh FROM DonHang dh WHERE dh.trangThai = 'CHO_LAY_HANG'")
    List<DonHang> findAllByTrangThaiChoLayHang();
    
    @Query("SELECT dh FROM DonHang dh WHERE dh.trangThai = 'DANG_GIAO_HANG'")
    List<DonHang> findAllByTrangThaiDangGiaoHang();

    @Query("SELECT dh FROM DonHang dh WHERE dh.trangThai = 'HOAN_TAT'")
    List<DonHang> findAllByTrangThaiHoanThanh();
    
    @Query("SELECT dh FROM DonHang dh WHERE dh.trangThai = 'HUY_DON'")
    List<DonHang> findAllByTrangThaiHuyDon();
}
