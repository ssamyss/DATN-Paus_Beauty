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

import com.poly.entity.SanPham;
import com.poly.service.SanPhamService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/sanpham")
public class SanPhamRestController {

	@Autowired
	SanPhamService sanPhamService;
	
	@GetMapping()
	public List<SanPham> getAll() {
		return sanPhamService.findAll();
	}
	
	@GetMapping("{maSP}")
	public SanPham getOne(@PathVariable("maSP") Integer maSP) {
		return sanPhamService.findById(maSP);
	}
	

	@PostMapping()
	public SanPham create(@RequestBody SanPham sanpham) {
		return sanPhamService.create(sanpham);
	}
	
	@PutMapping("{maSP}")
	public SanPham update(@PathVariable("maSP") Integer maSP, @RequestBody SanPham sanpham) {
		return sanPhamService.update(sanpham);
	}
	
	@DeleteMapping("{maSP}")
	public void delete(@PathVariable("maSP") Integer maSP) {
		sanPhamService.delete(maSP);
	}
	
}
