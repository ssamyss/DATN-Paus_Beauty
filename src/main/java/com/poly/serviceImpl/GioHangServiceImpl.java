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
		return ghdao.findAll();
	}

	@Override
	public GioHang findById(Integer maGH) {
		return ghdao.findById(maGH).get();
	}

	@Override
	public List<GioHang> getGioHangByTenTaiKhoan(String tenTaiKhoan) {
		TaiKhoan taikhoan = new TaiKhoan();
		taikhoan.setTenTaiKhoan(tenTaiKhoan);
		return ghdao.findByTaiKhoan(taikhoan);
	}

	@Override
	public GioHang create(GioHang giohang) {
		return ghdao.save(giohang);
	}

	@Override
	public GioHang update(GioHang giohang) {
		return ghdao.save(giohang);
	}

	@Override
	public void delete(Integer maGH) {
		ghdao.deleteById(maGH);
	}

	@Override
	public List<GioHang> selectGioHang(GioHang giohang) {
		return ghdao.selectGioHang(giohang.getTaiKhoan().getTenTaiKhoan(), giohang.getSanPham().getMaSP());
	}
	
	@Override
	public Long tongTien(String tenTaiKhoan) {
		return ghdao.tongTien(tenTaiKhoan);
	}
}
