package com.poly.service;

import java.util.List;

import com.poly.entity.GioHang;

public interface GioHangService {
	
	List<GioHang> findAll();

	GioHang findById(String taiKhoan);

	List<GioHang> findByDonHangId(String cmaDH) ;

	GioHang create(GioHang giohang);

	GioHang update(GioHang giohang);

	void delete(String maDH);
}
