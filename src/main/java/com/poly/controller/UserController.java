package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String productList(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
		int pageSize = 8;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<SanPham> sanphamPage = spDao.findAll(pageable);
		model.addAttribute("sanphamPage", sanphamPage);
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

	@GetMapping("/quydinhchung")
	public String quyDinhChung() {
		return "user/quydinhchung";
	}

	@GetMapping("/doitra")
	public String doiTra() {
		return "user/doitra";
	}
	
	@GetMapping("/thong-tin-ca-nhan")
	public String thongTin() {
		return "user/thong-tin-ca-nhan";
	}
}
