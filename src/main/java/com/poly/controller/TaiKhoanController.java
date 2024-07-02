package com.poly.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	//Đăng ký
	@GetMapping("/register")
	public String Register(Model model) {
		model.addAttribute("taikhoanRequest", new TaiKhoan());
		return "user/dangky";
	}
	
	@PostMapping("/register")
    public String doPostRegister(@ModelAttribute("taikhoanRequest") TaiKhoan taikhoanRequest, HttpSession session) {
        try {
            TaiKhoan taikhoan = taikhoanService.save(taikhoanRequest);
            if (taikhoan != null) {
            	session.setAttribute("tentaikhoan", taikhoan);             
                return "redirect:/"; 
            } else {
                return "redirect:/register";
            }
        } catch (SQLException e) {
            return "redirect:/register";
        }
    }
	
	
	//	Đăng nhập
	@GetMapping("/login")
	public String Login(Model model) {
		model.addAttribute("checkpass", false);
		return "user/dangnhap";
	}

	@PostMapping("/login")
	public String login(Model model, @RequestParam("TenTaiKhoan") String TenTaiKhoan,
	                    @RequestParam("MatKhau") String MatKhau) {
	    try {
	        TaiKhoan taikhoan = taikhoanService.findById(TenTaiKhoan);
	        if (taikhoan != null) {
	            // Kiểm tra mật khẩu đã mã hóa
	            if (BCrypt.checkpw(MatKhau, taikhoan.getMatKhau())) {
	                // Tạo session và lưu tên tài khoản
	                HttpSession session = request.getSession();
	                session.setAttribute("tentaikhoan", TenTaiKhoan);
	                
	                // Redirect đến trang tương ứng dựa vào vai trò của người dùng
	                if (taikhoan.isRole()) {
	                    return "redirect:/admin";
	                } else {
	                    return "redirect:/";
	                }
	            } else {
	                model.addAttribute("checkpass", true);
	                return "user/dangnhap";
	            }
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
	
}
