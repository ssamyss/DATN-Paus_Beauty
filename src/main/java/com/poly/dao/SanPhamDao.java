package com.poly.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.SanPham;
import com.poly.entity.ThuongHieu;

public interface SanPhamDao extends JpaRepository<SanPham, Integer> {

	@Query("SELECT count(o) FROM SanPham o")
	Integer getCountSP();

	@Query("SELECT count(o) FROM SanPham o WHERE o.tonKho < 5")
	Integer getCountTonKho();

	@Query("SELECT count(o) FROM SanPham o WHERE o.tonKho =0")
	Integer getCounthHetSP();

	@Query("SELECT sp FROM SanPham sp WHERE sp.tenSP LIKE %:keyword%")
	List<SanPham> searchByTenSP(@Param("keyword") String keyword);
	
	@Query("SELECT sp FROM SanPham sp WHERE sp.danhMucLoaiSanPham.tenLSP = :category")
	List<SanPham> findByCategory(@Param("category") String category);

	List<SanPham> findByTenSPContainingIgnoreCase(String tenSP);

	Page<SanPham> findAll(Pageable pageable);
}
