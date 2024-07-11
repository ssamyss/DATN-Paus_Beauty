package com.poly.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.GioHang;
import com.poly.entity.TaiKhoan;

public interface GioHangDao extends JpaRepository<GioHang, Integer> {

	List<GioHang> findByTaiKhoan(TaiKhoan taiKhoan);
}
