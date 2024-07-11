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
import com.poly.service.GioHangService;

@RestController
@RequestMapping("/rest/giohang")
public class GioHangRestController {
	
	@Autowired
	GioHangService giohangService;

	@GetMapping()
	public List<GioHang> getAll() {
		return giohangService.findAll();
	}

	@GetMapping("{taiKhoan}")
	public GioHang getOne(@PathVariable("taiKhoan") String taiKhoan) {
		return giohangService.findById(taiKhoan);
	}

	@PostMapping()
	public GioHang create(@RequestBody GioHang giohang) {
		return giohangService.create(giohang);
	}

	@PutMapping("{taiKhoan}")
	public GioHang update(@PathVariable("taiKhoan") String taiKhoan, @RequestBody GioHang giohang) {
		return giohangService.update(giohang);
	}

	@DeleteMapping("{taiKhoan}")
	public void delete(@PathVariable("taiKhoan") String taiKhoan) {
		giohangService.delete(taiKhoan);
	}

}
