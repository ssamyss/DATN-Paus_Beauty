package com.poly.dao;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.SanPham;
import com.poly.entity.ThuongHieu;

public interface SanPhamDao extends JpaRepository<SanPham, Integer> {

	@Query("SELECT count(o) FROM SanPham o")
	Integer getCountSP();
	
	@Query("SELECT count(o) FROM SanPham o WHERE o.tonKho < 5")
    Integer getCountTonKho();
	
	Page<SanPham> findAll(Pageable pageable);
}
