package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.DonHang;

public interface DonHangDao extends JpaRepository<DonHang, String> {

	@Query("SELECT count(o) FROM DonHang o")
	Integer getCountDH();
	
	@Query("SELECT SUM(dh.tongGia) FROM DonHang dh WHERE dh.trangThai = 'HOAN_TAT'")
	Double getCountTongDT();
	
	@Query("SELECT count(dh) FROM DonHang dh WHERE dh.trangThai = 'HUY_DON'")
	Integer getCountHuyDon();

	@Query("SELECT dh.maDH, tk.HoVaTen, " +
	           "STRING_AGG(sp.tenSP, ', '), " +
	           "SUM(dht.soLuong), " +
	           "SUM(dht.gia * dht.soLuong) " +
	           "FROM DonHang dh " +
	           "JOIN dh.donHangChiTiet dht " +
	           "JOIN dht.sanPham sp " +
	           "JOIN dh.taiKhoan tk " +
	           "GROUP BY dh.maDH, tk.HoVaTen")
	List<Object[]> getOrderSummary();
}
