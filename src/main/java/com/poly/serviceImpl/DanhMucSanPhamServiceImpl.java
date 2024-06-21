package com.poly.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.DanhMucLoaiSanPhamDao;
import com.poly.entity.DanhMucLoaiSanPham;
import com.poly.service.DanhMucSanPhamService;

@Service
public class DanhMucSanPhamServiceImpl implements DanhMucSanPhamService {
	
	@Autowired
	DanhMucLoaiSanPhamDao dmdao;

	@Override
	public List<DanhMucLoaiSanPham> findAll() {
		// TODO Auto-generated method stub
		return dmdao.findAll();
	}

	@Override
	public DanhMucLoaiSanPham findById(int maLSP) {
		// TODO Auto-generated method stub
		return dmdao.findById(maLSP).get();
	}

	@Override
	public List<DanhMucLoaiSanPham> findByDanhMucId(int cmaLSP) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DanhMucLoaiSanPham create(DanhMucLoaiSanPham danhmucsanpham) {
		// TODO Auto-generated method stub
		return dmdao.save(danhmucsanpham);
	}

	@Override
	public DanhMucLoaiSanPham update(DanhMucLoaiSanPham danhmucsanpham) {
		// TODO Auto-generated method stub
		return dmdao.save(danhmucsanpham);
	}

	@Override
	public void delete(int maLSP) {
		// TODO Auto-generated method stub
		dmdao.deleteById(maLSP);
	}

}
