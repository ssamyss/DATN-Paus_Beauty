package com.poly.service;

import java.util.List;

import com.poly.entity.DanhMucLoaiSanPham;

public interface DanhMucSanPhamService {

	List<DanhMucLoaiSanPham> findAll();

	DanhMucLoaiSanPham findById(int maLSP);

	List<DanhMucLoaiSanPham> findByDanhMucId(int cmaLSP);

	DanhMucLoaiSanPham create(DanhMucLoaiSanPham danhmucsanpham);

	DanhMucLoaiSanPham update(DanhMucLoaiSanPham danhmucsanpham);

	void delete(int maLSP);
	
	
}
