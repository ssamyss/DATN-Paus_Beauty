package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ThanhToanController {

	@GetMapping("/don-hang")
	public String donhang() {
		return "user/don-hang";
	}
	
	@GetMapping("/hoa-don/{maDH}")
	public String hoadon(@PathVariable("maDH") String maDH, Model model) {
		model.addAttribute("maDH", maDH);
		return "user/hoa-don";
	}
}
