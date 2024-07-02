package com.poly.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.LoaiSanPhamDao;
import com.poly.entity.LoaiSanPham;
import com.poly.service.LoaiSanPhamService;

@Service
public class LoaiSanPhamServiceImpl implements LoaiSanPhamService {
	
	@Autowired
	LoaiSanPhamDao lspdao;

	@Override
	public List<LoaiSanPham> findAll() {
		// TODO Auto-generated method stub
		return lspdao.findAll();
	}

	@Override
	public LoaiSanPham findById(int maPL) {
		// TODO Auto-generated method stub
		return lspdao.findById(maPL).get();
	}

	@Override
	public List<LoaiSanPham> findByLoaiSanPhamId(int cmaPL) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoaiSanPham create(LoaiSanPham loaisanpham) {
		// TODO Auto-generated method stub
		return lspdao.save(loaisanpham);
	}

	@Override
	public LoaiSanPham update(LoaiSanPham loaisanpham) {
		// TODO Auto-generated method stub
		return lspdao.save(loaisanpham);
	}

	@Override
	public void delete(int maPL) {
		// TODO Auto-generated method stub
		lspdao.deleteById(maPL);
	}

	@Override
	public List<LoaiSanPham> findByDanhMucLoaiSanPham(Integer maLSP) {
		// TODO Auto-generated method stub
		return lspdao.findByDanhMucLoaiSanPham(maLSP);
	}

}
