package com.poly.service;

import java.sql.SQLException;
import java.util.List;

import com.poly.entity.TaiKhoan;

public interface TaiKhoanService {

	List<TaiKhoan> findAll();

	TaiKhoan findById(String tenTaiKhoan);

	List<TaiKhoan> findByTaiKhoanId(String ctenTaiKhoan);

	TaiKhoan create(TaiKhoan taiKhoan);

	TaiKhoan update(TaiKhoan taiKhoan);

	void delete(String tenTaiKhoan);
	
	TaiKhoan save(TaiKhoan taikhoanRequest, boolean role) throws SQLException;
	
	String generateAndSendPIN(String email);
	
	String generateAndSendLinkResetPass(String email);
	
	void checkTenTaiKhoan(TaiKhoan taikhoanRequest) throws SQLException;
	
	boolean isEmailExists(String email);
	
}
