package com.poly.service;

import java.util.List;

import com.poly.entity.TaiKhoan;

public interface TaiKhoanService {

	List<TaiKhoan> findAll();

	TaiKhoan findById(String tenTaiKhoan);

	List<TaiKhoan> findByTaiKhoanId(String ctenTaiKhoan);

	TaiKhoan create(TaiKhoan taiKhoan);

	TaiKhoan update(TaiKhoan taiKhoan);

	void delete(String tenTaiKhoan);

}
