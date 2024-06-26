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

import com.poly.entity.DonHang;
import com.poly.service.DonHangService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/donhang")
public class DonHangRestController {

	@Autowired
	DonHangService donHangService;
	
	@GetMapping()
	public List<DonHang> getAll() {
		return donHangService.findAll();
	}
	
	@GetMapping("{maDH}")
	public DonHang getOne(@PathVariable("maDH") String maDH) {
		return donHangService.findById(maDH);
	}
	
	@PostMapping()
	public DonHang create(@RequestBody DonHang donHang) {
		return donHangService.create(donHang);
	}
	
	@PutMapping("{maDH}")
	public DonHang update(@PathVariable("maDHCT") Integer maDHCT, @RequestBody DonHang donHang) {
		return donHangService.update(donHang);
	}
	
	@DeleteMapping("{maDH}")
	public void delete(@PathVariable("maDH") String maDH) {
		donHangService.delete(maDH);
	}
}
