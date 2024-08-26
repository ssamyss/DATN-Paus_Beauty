package com.poly.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import com.poly.entity.SanPham;

public interface SanPhamService {
	List<SanPham> findAll();

	SanPham findById(int maSP);

	List<SanPham> findByCategoryId(int cmaSP);

	SanPham create(SanPham sanpham);

	SanPham update(SanPham sanpham);

	void delete(int maSP);
	void deleteAll();
	
	List<SanPham> getTop5SanPhamBanChay();
	
	List<SanPham> findHetHang();
	
	List<SanPham> findSapHetHang();

	public List<Object[]> getTotalProductsByDanhMuc();

	List<SanPham> findRandomByCategory(String categoryName, Pageable pageable);
	
	Page<SanPham> findByPriceLessThan(Long price, Pageable pageable);
	Page<SanPham> findByPriceLargeThan(Long price, Pageable pageable);
	Page<SanPham> findByPriceBetween(String price, Pageable pageable);
	
	Page<SanPham> getProductsSortedByPrice(String sortOrder, Pageable pageable);
}
