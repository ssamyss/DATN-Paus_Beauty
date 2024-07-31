package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThanhToanController {

	@GetMapping("/don-hang")
	public String form() {
		return "user/don-hang";
	}
	
}
