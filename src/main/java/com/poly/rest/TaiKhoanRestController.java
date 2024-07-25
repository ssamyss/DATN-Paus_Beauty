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

import com.poly.entity.TaiKhoan;
import com.poly.service.TaiKhoanService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/taikhoan")
public class TaiKhoanRestController {

	@Autowired
	TaiKhoanService taiKhoanService;

	// Lấy tài khoản đang dùng
	@GetMapping("/tentaikhoan")
	ResponseEntity<TaiKhoan> gettaikhoan(HttpSession session) {
		try {
			ResponseEntity<TaiKhoan> responseEntity;
			String tenTaiKhoan = (String) session.getAttribute("tentaikhoan");
			TaiKhoan taiKhoan = taiKhoanService.findById(tenTaiKhoan);
			if (tenTaiKhoan != null) {
				responseEntity = ResponseEntity.ok(taiKhoan);
			} else {
				responseEntity = ResponseEntity.notFound().build();
			}
			return responseEntity;
		} catch (Exception e) {
			return null;
		}
	}

	@GetMapping()
	public List<TaiKhoan> getAll() {
		return taiKhoanService.findAll();
	}

	@GetMapping("{tenTaiKhoan}")
	public TaiKhoan getOne(@PathVariable("tenTaiKhoan") String tenTaiKhoan) {
		return taiKhoanService.findById(tenTaiKhoan);
	}

	@PostMapping()
	public TaiKhoan create(@RequestBody TaiKhoan taikhoan) {
		return taiKhoanService.create(taikhoan);
	}

	@PutMapping("{tenTaiKhoan}")
	public TaiKhoan update(@PathVariable("tenTaiKhoan") String tenTaiKhoan, @RequestBody TaiKhoan taikhoan) {
		return taiKhoanService.update(taikhoan);
	}

	@DeleteMapping("{tenTaiKhoan}")
	public void delete(@PathVariable("tenTaiKhoan") String tenTaiKhoan) {
		taiKhoanService.delete(tenTaiKhoan);
	}
}
