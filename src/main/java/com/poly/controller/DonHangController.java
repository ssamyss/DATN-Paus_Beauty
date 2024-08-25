package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DonHangController {

	@GetMapping("/don-hang")
	public String donhang() {
		return "user/don-hang";
	}
	
	
	@GetMapping("/hoa-don/{maDH}")
	public String hoadon(@PathVariable("maDH") String maDH, Model model) {
		model.addAttribute("maDH", maDH);
		return "user/hoa-don";
	}
	
	@GetMapping("/quan-ly-don-hang")
	public String quanlydonhang() {
		return "user/quan-ly-don-hang";
	}
	
	@GetMapping("/don-hang-chi-tiet/{maDH}")
	public String quanlydonhangchitiet() {
		return "user/quan-ly-don-hang-chi-tiet";
	}
	
	@GetMapping("/tra-cuu-don-hang")
	public String tracuudonhang() {
		return "user/tra-cuu-don-hang";
	}
}
