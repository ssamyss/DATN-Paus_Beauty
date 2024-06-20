package com.poly.service;

import java.util.List;

import com.poly.entity.SanPham;

public interface SanPhamService {
	List<SanPham> findAll();

	SanPham findById(int maSP);

	List<SanPham> findByCategoryId(int cmaSP);

	SanPham create(SanPham sanpham);

	SanPham update(SanPham sanpham);

	void delete(int maSP);
}
