package com.poly.service;

import java.util.List;

import com.poly.entity.DonHang;

public interface DonHangService {
	
	List<DonHang> findAll();

	DonHang findById(String maDH);

	List<DonHang> findByDonHangId(String cmaDH);

	DonHang create(DonHang donHang);

	DonHang update(DonHang donHang);

	void delete(String maDH);
}
