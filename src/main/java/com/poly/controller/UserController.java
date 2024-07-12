package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.poly.dao.SanPhamDao;
import com.poly.entity.SanPham;

@Controller
public class UserController {

	@Autowired
	SanPhamDao spDao;

	@GetMapping("")
	public String index() {
		return "user/index";
	}

	@GetMapping("/product")
	public String index(Model model) {
		List<SanPham> sp = spDao.findAll();
		model.addAttribute("sanpham", sp);
		return "user/sanpham";
	}

	@GetMapping("/detail/{id}")
	public String productDetail(Model model, @PathVariable("id") Integer maSP) {
		SanPham sp = spDao.findById(maSP).orElse(null);
		if (sp == null) {
			// Xử lý khi không tìm thấy sản phẩm
			return "redirect:/"; // Chuyển hướng về trang chủ hoặc trang thông báo lỗi
		}
		model.addAttribute("sanpham", sp);
		return "user/product-detail";
	}

	@GetMapping("/about")
	public String about() {
		return "user/about";
	}

	@GetMapping("/contact")
	public String contact() {
		return "user/contact";
	}
}
