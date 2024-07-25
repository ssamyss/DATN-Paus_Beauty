package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.GioHang;
import com.poly.entity.SanPham;
import com.poly.service.GioHangService;
import com.poly.service.TaiKhoanService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/rest/giohang")
public class GioHangRestController {
	
	@Autowired
	GioHangService giohangService;
	
	@Autowired
	TaiKhoanService taikhoanService;

	@GetMapping()
	public List<GioHang> getAll() {
		return giohangService.findAll();
	}

	@GetMapping("{maGH}")
	public GioHang getOne(@PathVariable("maGH") Integer maGH) {
		return giohangService.findById(maGH);
	}
	
	@PostMapping("/ghtontai")
	public List<GioHang> add(@RequestBody SanPham sanpham, HttpSession session) {
		GioHang giohang = new GioHang();
		String tentaikhoan = (String) session.getAttribute("tentaikhoan");
		giohang.setTaiKhoan(taikhoanService.findById(tentaikhoan));
		giohang.setSanPham(sanpham);
		return giohangService.selectGioHang(giohang);
	}
	
	@GetMapping("/byttk/{tentaikhoan}")
	public List<GioHang> getGioHangByTenTaiKhoan(@PathVariable String tentaikhoan){
		return giohangService.getGioHangByTenTaiKhoan(tentaikhoan);
	}

	@PostMapping()
	public GioHang create(@RequestBody SanPham sanpham, HttpSession session) {
		GioHang giohang = new GioHang();
		String tentaikhoan = (String) session.getAttribute("tentaikhoan");
		giohang.setSoLuong(1);
		giohang.setTaiKhoan(taikhoanService.findById(tentaikhoan));
		giohang.setSanPham(sanpham);
		return giohangService.create(giohang);
	}
	
	@PutMapping("/create2/{maGH}")
	public GioHang create2(@PathVariable("maGH") Integer maGH, @RequestBody GioHang giohang) {
		return giohangService.update(giohang);
	}

	@PutMapping("{maGH}")
	public GioHang update(@PathVariable("maGH") Integer maGH, @RequestBody GioHang giohang) {
		return giohangService.update(giohang);
	}

	@DeleteMapping("{giohangid}")
	public void delete(@PathVariable("taiKhoan") Integer maGH) {
		giohangService.delete(maGH);
	}

}
