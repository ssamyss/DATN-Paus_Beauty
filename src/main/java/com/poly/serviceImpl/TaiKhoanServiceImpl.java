package com.poly.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.dao.TaiKhoanDao;
import com.poly.entity.TaiKhoan;
import com.poly.service.TaiKhoanService;

import jakarta.transaction.Transactional;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService{

	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	
	@Autowired
	TaiKhoanDao dhdao;
	

	@Override
	public List<TaiKhoan> findAll() {
		// TODO Auto-generated method stub
		return dhdao.findAll();
	}

	@Override
	public TaiKhoan findById(String tenTaiKhoan) {
		// TODO Auto-generated method stub
		return dhdao.findById(tenTaiKhoan).get();
	}

	@Override
	public List<TaiKhoan> findByTaiKhoanId(String ctenTaiKhoan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaiKhoan create(TaiKhoan taiKhoan) {
		// TODO Auto-generated method stub
		return dhdao.save(taiKhoan);
	}
	@Override
	public TaiKhoan update(TaiKhoan taiKhoan) {
		// TODO Auto-generated method stub
		return dhdao.save(taiKhoan);
	}

	@Override
	public void delete(String tenTaiKhoan) {
		// TODO Auto-generated method stub
		dhdao.deleteById(tenTaiKhoan);
	}

	@Override
	@Transactional(rollbackOn = { Throwable.class })
	public TaiKhoan save(TaiKhoan taikhoanRequest) throws SQLException {

		TaiKhoan TaiKhoanDao = taikhoanRequest;
		taikhoanRequest.setRole(false);
		taikhoanRequest.setMatKhau(bcrypt.encode(taikhoanRequest.getMatKhau()));

		return dhdao.saveAndFlush(TaiKhoanDao);
	}
}
