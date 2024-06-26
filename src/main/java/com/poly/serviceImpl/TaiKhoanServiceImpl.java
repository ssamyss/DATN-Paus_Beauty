package com.poly.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.TaiKhoanDao;
import com.poly.entity.TaiKhoan;
import com.poly.service.TaiKhoanService;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService{

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
}
