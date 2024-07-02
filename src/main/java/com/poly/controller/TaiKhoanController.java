package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.entity.TaiKhoan;
import com.poly.service.TaiKhoanService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class TaiKhoanController {

	@Autowired
	TaiKhoanService taikhoanService;

	@Autowired
	HttpServletRequest request;

	@GetMapping("/login")
	public String Login(Model model) {
		model.addAttribute("checkpass", false);
		return "user/dangnhap";
	}

	@PostMapping("/login")
	public String login(Model model, @RequestParam("TenTaiKhoan") String TenTaiKhoan,
			@RequestParam("MatKhau") String MatKhau) {
		try {
			TaiKhoan taikhoan = (TaiKhoan) taikhoanService.findById(TenTaiKhoan);
			if (taikhoan != null) {
				if (taikhoan.isRole()) {
					if (taikhoan.getMatKhau().equals(MatKhau)) {
						// Tạo một đối tượng session
						HttpSession session = request.getSession();
						// Thêm dữ liệu tên người dùng vào session
						session.setAttribute("tentaikhoan", TenTaiKhoan);
					} else {
						model.addAttribute("checkpass", true);
						return "user/dangnhap";
					}
					return "redirect:/admin";
				}
				if (taikhoan.getMatKhau().equals(MatKhau)) {
					// Tạo một đối tượng session
					HttpSession session = request.getSession();
					// Thêm dữ liệu tên người dùng vào session
					session.setAttribute("tentaikhoan", TenTaiKhoan);
					return "redirect:http://localhost:8080";
				}
				model.addAttribute("checkpass", true);
				return "user/dangnhap";
			} else {
				model.addAttribute("checkpass", true);
				return "user/dangnhap";
			}
		} catch (Exception e) {
			model.addAttribute("checkpass", true);
			return "user/login";
		}
	}

	@GetMapping("/logout")
	public String logout() {
		HttpSession session = request.getSession();
		session.removeAttribute("tentaikhoan");
		return "redirect:/login";
	}
	
	@GetMapping("/register")
	public String Register() {
		return "user/dangki";
	}
	
}
