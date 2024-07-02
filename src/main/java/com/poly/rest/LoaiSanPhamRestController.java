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


import com.poly.entity.LoaiSanPham;
import com.poly.service.LoaiSanPhamService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/loaisanpham")
public class LoaiSanPhamRestController {

	@Autowired
	LoaiSanPhamService loaiSanPhamService;
	
	@GetMapping()
	public List<LoaiSanPham> getAll() {
		return loaiSanPhamService.findAll();
	}
	
	@GetMapping("{maPL}")
	public LoaiSanPham getOne(@PathVariable("maPL") Integer maPL) {
		return loaiSanPhamService.findById(maPL);
	}
	
	@PostMapping()
	public LoaiSanPham create(@RequestBody LoaiSanPham loaisanpham) {
		return loaiSanPhamService.create(loaisanpham);
	}
	
	@PutMapping("{maPL}")
	public LoaiSanPham update(@PathVariable("maPL") Integer maPL, @RequestBody LoaiSanPham loaisanpham) {
		return loaiSanPhamService.update(loaisanpham);
	}
	
	@DeleteMapping("{maPL}")
	public void delete(@PathVariable("maPL") Integer maPL) {
		loaiSanPhamService.delete(maPL);
	}
	
	@GetMapping("/by-category/{maLSP}")
    public List<LoaiSanPham> getByCategory(@PathVariable("maLSP") Integer maLSP) {
        return loaiSanPhamService.findByDanhMucLoaiSanPham(maLSP);
    }
	
}
