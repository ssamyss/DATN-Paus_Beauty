package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.poly.entity.DonHangChiTiet;
import com.poly.service.DonHangChiTietService;
import com.poly.service.DonHangService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/donhangchitiet")
public class DonHangChiTietRestController {

	@Autowired
	DonHangChiTietService donHangChiTietService;
	
	@Autowired
	DonHangService donHangService;

	@GetMapping()
	public List<DonHangChiTiet> getAll() {
		return donHangChiTietService.findAll();
	}

	@GetMapping("{maDHCT}")
	public DonHangChiTiet getOne(@PathVariable("maDHCT") Integer maDHCT) {
		return donHangChiTietService.findById(maDHCT);
	}

	@GetMapping("/bymadh/{maDH}")
	public List<DonHangChiTiet> getDonHangChiTietByMaDH(@PathVariable String maDH) {
		return donHangChiTietService.getDonHangChiTietByMaDH(maDH);
	}
	
	@GetMapping("/bydh/{maDH}")
	public List<DonHangChiTiet> findByDonHang(@PathVariable String maDH) {
		DonHang donHang = new DonHang();
		donHang = donHangService.findById(maDH);
		return donHangChiTietService.findByDonHang(donHang);
	}

	@PostMapping()
	public DonHangChiTiet create(@RequestBody DonHangChiTiet donHangChiTiet) {
		return donHangChiTietService.create(donHangChiTiet);
	}

	@PutMapping("{maDHCT}")
	public DonHangChiTiet update(@PathVariable("maDHCT") Integer maDHCT, @RequestBody DonHangChiTiet donHangChiTiet) {
		return donHangChiTietService.update(donHangChiTiet);
	}

	@DeleteMapping("{maDHCT}")
	public void delete(@PathVariable("maDHCT") Integer maDHCT) {
		donHangChiTietService.delete(maDHCT);
	}
}
