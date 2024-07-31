package com.poly.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.poly.entity.ThuongHieu;

public interface ThuongHieuDao extends JpaRepository<ThuongHieu, Integer> {

	Page<ThuongHieu> findAll(Pageable pageable);
	
}
