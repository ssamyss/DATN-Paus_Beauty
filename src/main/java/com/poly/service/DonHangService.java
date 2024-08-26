package com.poly.service;

import java.util.List;

import com.poly.entity.DonHang;

public interface DonHangService {

	List<DonHang> findAll();

	DonHang findById(String maDH);

	List<DonHang> findByDonHangId(String cmaDH);

	DonHang create(DonHang donHang);

	DonHang update(DonHang donHang);

	void delete(String maDH);

	void deleteAll();

	List<Object[]> getMonthlyRevenue();

	List<Object[]> getQuarterlyRevenue();

	List<DonHang> getOrders(String tentaikhoan);
	
	List<DonHang> findAllByTrangThaiDangXuLy();
	
	List<DonHang> findAllByTrangThaiChoLayHang();

	List<DonHang> findAllByTrangThaiDangGiaoHang();
	
	List<DonHang> findAllByTrangThaiHoanThanh();

	List<DonHang> findAllByTrangThaiHuyDon();

}
