package com.poly.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.DonHangDao;
import com.poly.entity.DonHang;
import com.poly.service.DonHangService;

@Service
public class DonHangServiceImpl implements DonHangService {
	
	@Autowired
	DonHangDao dhdao;

	@Override
	public List<DonHang> findAll() {
		// TODO Auto-generated method stub
		return dhdao.findAll();
	}

	@Override
	public DonHang findById(String maDH) {
		// TODO Auto-generated method stub
		return dhdao.findById(maDH).get();
	}

	@Override
	public List<DonHang> findByDonHangId(String cmaDH) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DonHang create(DonHang donHang) {
		// TODO Auto-generated method stub
		return dhdao.save(donHang);
	}

	@Override
	public DonHang update(DonHang donHang) {
		// TODO Auto-generated method stub
		return dhdao.save(donHang);
	}

	@Override
	public void delete(String maDH) {
		// TODO Auto-generated method stub
		dhdao.deleteById(maDH);
	}

}
