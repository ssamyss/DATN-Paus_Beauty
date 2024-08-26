package com.poly.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.DonHangChiTietDao;
import com.poly.entity.DonHang;
import com.poly.entity.DonHangChiTiet;
import com.poly.service.DonHangChiTietService;

@Service
public class DonHangChiTietServiceImpl implements DonHangChiTietService{

	@Autowired
	DonHangChiTietDao dhctdao;

	@Override
	public List<DonHangChiTiet> findAll() {
		// TODO Auto-generated method stub
		return dhctdao.findAll();
	}

	@Override
	public DonHangChiTiet findById(int maDHCT) {
		// TODO Auto-generated method stub
		return dhctdao.findById(maDHCT).get();
	}

	@Override
	public List<DonHangChiTiet> getDonHangChiTietByMaDH(String maDH) {
        DonHang donHang = new DonHang();
        donHang.setMaDH(maDH);
        return dhctdao.findByDonHang(donHang);
    }
	
	@Override
	public List<DonHangChiTiet> findByDonHang(DonHang donHang) {
		return dhctdao.findByDonHang(donHang);
	}

	@Override
	public DonHangChiTiet create(DonHangChiTiet donHangChiTiet) {
		// TODO Auto-generated method stub
		return dhctdao.save(donHangChiTiet);
	}
	
	@Override
	public DonHangChiTiet update(DonHangChiTiet donHangChiTiet) {
		// TODO Auto-generated method stub
		return dhctdao.save(donHangChiTiet);
	}

	@Override
	public void delete(int maDHCT) {
		// TODO Auto-generated method stub
		dhctdao.deleteById(maDHCT);
	}
}
