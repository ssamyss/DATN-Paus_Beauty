package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GioHangController {
	// giỏ hàng
	@GetMapping("/cart")
	public String home() {
		return "user/cart";
	}

}
