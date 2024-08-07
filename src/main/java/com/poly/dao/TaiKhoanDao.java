package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.entity.TaiKhoan;

public interface TaiKhoanDao extends JpaRepository<TaiKhoan, String> {

	@Query("SELECT count(o) FROM TaiKhoan o")
	Integer getCountTK();
}
