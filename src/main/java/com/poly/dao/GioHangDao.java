package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.GioHang;
import com.poly.entity.TaiKhoan;

public interface GioHangDao extends JpaRepository<GioHang, Integer> {

	List<GioHang> findByTaiKhoan(TaiKhoan taiKhoan);

	@Query("SELECT gh FROM GioHang gh WHERE gh.taiKhoan.TenTaiKhoan = :tentaikhoan AND gh.sanPham.maSP = :masp")
	List<GioHang> selectGioHang(@Param("tentaikhoan") String tentaikhoan, @Param("masp") Integer masp);

	//@Query("select SUM(sp.gia * gh.so_luong) from GioHang gh join tai_khoan tk on gh.ten_tai_khoan = tk.ten_tai_khoan join san_pham sp on gh.ma_sp = sp.ma_sp where tk.ten_tai_khoan = 'nnahnn'")
	
	@Query("select SUM(gh.sanPham.gia * gh.soLuong) from GioHang gh where gh.taiKhoan.TenTaiKhoan = :tentaikhoan")
	Long tongTien(@Param("tentaikhoan") String tentaikhoan);
	
}
