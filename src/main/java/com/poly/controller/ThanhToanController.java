package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThanhToanController {

	@GetMapping("/don-hang")
	public String donhang() {
		return "user/don-hang";
	}
	
	@GetMapping("/hoa-don")
	public String hoadon() {
		return "user/hoa-don";
	}
}
