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
		String tentaikhoan = (String) session.getAttribute("tentaikhoan");
		GioHang giohang = new GioHang();
		giohang.setTaiKhoan(taikhoanService.findById(tentaikhoan));
		giohang.setSanPham(sanpham);
		
		// Kiểm tra sản phẩm đã tồn tại trong giỏ hàng chưa
		List<GioHang> gioHangTonTai = giohangService.selectGioHang(giohang);
		if (!gioHangTonTai.isEmpty()) {
			// Sản phẩm đã tồn tại, tăng số lượng sản phẩm
			GioHang existingItem = gioHangTonTai.get(0);
			existingItem.setSoLuong(existingItem.getSoLuong() + 1);
			giohangService.update(existingItem);
		} else {
			// Sản phẩm chưa tồn tại, thêm sản phẩm mới vào giỏ hàng
			giohang.setSoLuong(1);
			giohangService.create(giohang);
		}

		return giohangService.getGioHangByTenTaiKhoan(tentaikhoan);
	}
	
	@GetMapping("/byttk/{tentaikhoan}")
	public List<GioHang> getGioHangByTenTaiKhoan(@PathVariable String tentaikhoan){
		return giohangService.getGioHangByTenTaiKhoan(tentaikhoan);
	}
	
	@GetMapping("/tongtien")
	public Long tongTien(HttpSession session) {
		String tentaikhoan = (String) session.getAttribute("tentaikhoan");
		return giohangService.tongTien(tentaikhoan);
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

	@DeleteMapping("{maGH}")
	public void delete(@PathVariable("maGH") Integer maGH) {
		giohangService.delete(maGH);
	}
	
	@DeleteMapping("/deleteByTTK/{tentaikhoan}")
	public void deleteAll(@PathVariable("tentaikhoan") String tentaikhoan) {
		List<GioHang> gh = getGioHangByTenTaiKhoan(tentaikhoan);
		int count = gh.size();
		for (int i = 0; i < count; i++) {
			GioHang gioHang = gh.get(i);
			giohangService.delete(gioHang.getMaGH());
		}
	}
}
