package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.DonHang;

public interface DonHangDao extends JpaRepository<DonHang, String> {

	@Query("SELECT count(o) FROM DonHang o")
	Integer getCountDH();
}
