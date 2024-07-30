package com.poly.dao;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.SanPham;
import com.poly.entity.ThuongHieu;

public interface SanPhamDao extends JpaRepository<SanPham, Integer> {
	Page<SanPham> findAll(Pageable pageable);
}
