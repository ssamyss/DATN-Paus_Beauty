package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.LoaiSanPham;

public interface LoaiSanPhamDao extends JpaRepository<LoaiSanPham, Integer> {

	@Query("SELECT l FROM LoaiSanPham l WHERE l.danhMucLoaiSanPham.maLSP = :maLSP")
    List<LoaiSanPham> findByDanhMucLoaiSanPham(@Param("maLSP") Integer maLSP);
	
	Page<LoaiSanPham> findAll(Pageable pageable);
}
