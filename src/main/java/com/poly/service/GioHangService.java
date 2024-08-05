package com.poly.service;

import java.util.List;

import com.poly.entity.GioHang;

public interface GioHangService {

	List<GioHang> findAll();

	GioHang findById(Integer maGH);

	List<GioHang> getGioHangByTenTaiKhoan(String ctenTaiKhoan);

	GioHang create(GioHang giohang);

	GioHang update(GioHang giohang);

	void delete(Integer maGH);

	List<GioHang> selectGioHang(GioHang giohang);
	
	public Long tongTien(String tenTaiKhoan);
	
}
