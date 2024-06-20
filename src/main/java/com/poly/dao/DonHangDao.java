package com.poly.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.DonHang;

public interface DonHangDao extends JpaRepository<DonHang, String> {

}
