package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.DonHang;
import com.poly.entity.DonHangChiTiet;
import java.util.List;


public interface DonHangChiTietDao extends JpaRepository<DonHangChiTiet, Integer> {

	List<DonHangChiTiet> findByDonHang(DonHang donHang);
}
