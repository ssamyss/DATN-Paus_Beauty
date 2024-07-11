package com.poly.serviceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poly.dao.GioHangDao;
import com.poly.entity.GioHang;
import com.poly.entity.TaiKhoan;
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
	public GioHang findById(Integer maGH) {
		// TODO Auto-generated method stub
		return ghdao.findById(maGH).get();
	}

	@Override
	public List<GioHang> getGioHangByTenTaiKhoan(String tenTaiKhoan) {
		// TODO Auto-generated method stub
		TaiKhoan taikhoan = new TaiKhoan();
		taikhoan.setTenTaiKhoan(tenTaiKhoan);
		return ghdao.findByTaiKhoan(taikhoan);
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
	public void delete(Integer maGH) {
		// TODO Auto-generated method stub
		ghdao.deleteById(maGH);
	}


}
