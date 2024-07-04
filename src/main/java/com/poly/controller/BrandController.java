package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.poly.entity.ThuongHieu;
import com.poly.entity.SanPham;
import com.poly.service.GioHangService;
import com.poly.dao.ThuongHieuDao;
import com.poly.dao.SanPhamDao;

@Controller
public class BrandController {
	@Autowired
	ThuongHieuDao tdao;

	@Autowired
	SanPhamDao sdao;
	
	@Autowired
	GioHangService gService;
	
	@GetMapping("/brand")
	public String brand(Model model) {
		List<ThuongHieu> brands = tdao.findAll();
		model.addAttribute("brands", brands);
		model.addAttribute("count", gService.gettotalCount());
		return "user/brand";
	}
	
	@GetMapping("/productBrand")
	public String pBrand() {
		return "user/brand2";
	}
}
