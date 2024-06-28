package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.poly.dao.SanPhamDao;
import com.poly.entity.SanPham;

@Controller
public class HomeController {
	@Autowired
	SanPhamDao spDao;
	
	@GetMapping("/product")
	public String index(Model model) {
		List<SanPham> sp = spDao.findAll();
		model.addAttribute("sanpham", sp);
		return "user/sanpham";
	}
}
