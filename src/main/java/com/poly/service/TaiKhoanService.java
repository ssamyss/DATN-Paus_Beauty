package com.poly.service;

import java.util.List;

import com.poly.entity.TaiKhoan;

public interface TaiKhoanService {

	List<TaiKhoan> findAll();

	TaiKhoan findByUserName(String TenTaiKhoan);

//	List<TaiKhoan> findByAccountUserName(String cusername);

	TaiKhoan create(TaiKhoan TaiKhoan);

	TaiKhoan update(TaiKhoan TaiKhoan);

	void delete(String TenTaiKhoan);

}
