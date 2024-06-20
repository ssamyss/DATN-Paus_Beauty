package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.DanhMucLoaiSanPham;
import com.poly.service.DanhMucSanPhamService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/danhmucsanpham")
public class DanhMucSanPhamRestController {

	@Autowired
	DanhMucSanPhamService danhMucSanPhamService;
	
	@GetMapping()
	public List<DanhMucLoaiSanPham> getAll() {
		return danhMucSanPhamService.findAll();
	}
	
	@GetMapping("{maLSP}")
	public DanhMucLoaiSanPham getOne(@PathVariable("maLSP") Integer maLSP) {
		return danhMucSanPhamService.findById(maLSP);
	}
	
	@PostMapping()
	public DanhMucLoaiSanPham create(@RequestBody DanhMucLoaiSanPham danhmucsanpham) {
		return danhMucSanPhamService.create(danhmucsanpham);
	}
	
	@PutMapping("{maLSP}")
	public DanhMucLoaiSanPham update(@PathVariable("maLSP") Integer maTH, @RequestBody DanhMucLoaiSanPham danhmucsanpham) {
		return danhMucSanPhamService.update(danhmucsanpham);
	}
	
	@DeleteMapping("{maLSP}")
	public void delete(@PathVariable("maLSP") Integer maLSP) {
		danhMucSanPhamService.delete(maLSP);
	}
	
}
