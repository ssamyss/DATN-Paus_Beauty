package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GioHangController {
	// giỏ hàng
	@GetMapping("/cart")
	public String home() {
		return "user/cart";
	}
	
	@PostMapping("/cart")
	public String checkout() {
		return "redirect:/don-hang";
	}

}
