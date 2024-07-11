package com.poly.serviceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poly.dao.GioHangDao;
import com.poly.entity.GioHang;
import com.poly.service.GioHangService;

@Service
public class GioHangServiceImpl implements GioHangService {
	
	@Autowired
	GioHangDao ghdao;

    @Override
	public List<GioHang> findAll() {
		// TODO Auto-generated method stub
		return ghdao.findAll();
	}

	@Override
	public GioHang findById(String maDH) {
		// TODO Auto-generated method stub
		return ghdao.findById(maDH).get();
	}

	@Override
	public List<GioHang> findByDonHangId(String cmaDH) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GioHang create(GioHang giohang) {
		// TODO Auto-generated method stub
		return ghdao.save(giohang);
	}

	@Override
	public GioHang update(GioHang giohang) {
		// TODO Auto-generated method stub
		return ghdao.save(giohang);
	}

	@Override
	public void delete(String maDH) {
		// TODO Auto-generated method stub
		ghdao.deleteById(maDH);
	}


}
