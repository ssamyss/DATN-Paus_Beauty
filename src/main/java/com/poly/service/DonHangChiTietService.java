package com.poly.service;

import java.util.List;

import com.poly.entity.DonHang;
import com.poly.entity.DonHangChiTiet;

public interface DonHangChiTietService {

	List<DonHangChiTiet> findAll();

	DonHangChiTiet findById(int maDHCT);

	List<DonHangChiTiet> getDonHangChiTietByMaDH(String maDH);

	DonHangChiTiet create(DonHangChiTiet donhangchitiet);

	DonHangChiTiet update(DonHangChiTiet donhangchitiet);

	void delete(int maDHCT);
}
