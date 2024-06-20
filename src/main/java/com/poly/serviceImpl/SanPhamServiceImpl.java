package com.poly.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.SanPhamDao;
import com.poly.entity.SanPham;
import com.poly.service.SanPhamService;

@Service
public class SanPhamServiceImpl implements SanPhamService {
	
	@Autowired
	SanPhamDao spdao;

	@Override
	public List<SanPham> findAll() {
		// TODO Auto-generated method stub
		return spdao.findAll();
	}

	@Override
	public SanPham findById(int maSP) {
		// TODO Auto-generated method stub
		return spdao.findById(maSP).get();
	}

	@Override
	public List<SanPham> findByCategoryId(int cmaSP) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SanPham create(SanPham sanpham) {
		// TODO Auto-generated method stub
		return spdao.save(sanpham);
	}

	@Override
	public SanPham update(SanPham sanpham) {
		// TODO Auto-generated method stub
		return spdao.save(sanpham);
	}

	@Override
	public void delete(int maSP) {
		// TODO Auto-generated method stub
		spdao.deleteById(maSP);
	}

}
