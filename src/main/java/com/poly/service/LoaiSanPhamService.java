package com.poly.service;

import java.util.List;

import com.poly.entity.LoaiSanPham;

public interface LoaiSanPhamService {

	List<LoaiSanPham> findAll();

	LoaiSanPham findById(int maPL);

	List<LoaiSanPham> findByLoaiSanPhamId(int cmaPL);

	LoaiSanPham create(LoaiSanPham loaisanpham);

	LoaiSanPham update(LoaiSanPham loaisanpham);

	void delete(int maPL);
	List<LoaiSanPham> findByDanhMucLoaiSanPham(Integer maLSP);
}
