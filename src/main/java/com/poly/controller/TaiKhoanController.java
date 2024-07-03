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
	
	//Đăng ký admin
	@GetMapping("/register/admin")
	public String register_admin(Model model) {
		model.addAttribute("kttaikhoan", false);
		model.addAttribute("taikhoanRequest", new TaiKhoan());
		return "user/dangkyadmin";
	}
	
	@PostMapping("/register/admin")
    public String processSignUp_admin(@ModelAttribute  TaiKhoan taikhoan, HttpSession session, Model model) {
        session.setAttribute("taikhoan", taikhoan);
        try {
        	taikhoanService.checkTenTaiKhoan( (TaiKhoan) session.getAttribute("taikhoan"));
            String pin = taikhoanService.generateAndSendPIN(taikhoan.getEmail());
            session.setAttribute("registerPIN", pin);
            
            return "redirect:/verify-register-pin/admin";
			
		} catch (Exception e) {
			model.addAttribute("kttaikhoan", true);
			return "user/dangkyadmin";
		}
    }
	
	@GetMapping("/verify-register-pin/admin")
    public String showVerifyRegisterPinForm_admin() {
        return "user/verify-register-pin-admin";
    }
	
	@PostMapping("/verify-register-pin/admin")
	public String processVerifyRegisterPin_admin(@RequestParam(name = "pin") String pin, HttpSession session, Model model) {
	    // Kiểm tra xem pin có tồn tại trong request không
	    if (pin == null || pin.isEmpty()) {
	        model.addAttribute("error", "Invalid PIN. Please try again.");
	        return "user/verify-register-pin-admin";
	    }

	    String sessionPIN = (String) session.getAttribute("registerPIN");
	    if (sessionPIN != null && sessionPIN.equals(pin)) {
	        TaiKhoan taikhoan = (TaiKhoan) session.getAttribute("taikhoan");
	        try {
	            taikhoanService.save(taikhoan, true);
	            session.removeAttribute("registerPIN");
	            session.removeAttribute("taikhoan");
	            return "redirect:/register-success";
	        } catch (SQLException e) {
	            // Xử lý ngoại lệ SQL nếu cần thiết
	            model.addAttribute("error", "Error saving user. Please try again later.");
	            return "user/register";
	        }
	    } else {
	        model.addAttribute("error", "Invalid PIN. Please try again.");
	        return "user/verify-register-pin-admin";
	    }
	}
	
	//Đăng ký
	@GetMapping("/register")
	public String Register(Model model) {
		model.addAttribute("kttaikhoan", false);
		model.addAttribute("taikhoanRequest", new TaiKhoan());
		return "user/dangky";
	}
	
//	@PostMapping("/register")
//    public String doPostRegister(@ModelAttribute("taikhoanRequest") TaiKhoan taikhoanRequest, HttpSession session) {
//        try {
//            TaiKhoan taikhoan = taikhoanService.save(taikhoanRequest);
//            if (taikhoan != null) {
//            	session.setAttribute("tentaikhoan", taikhoan);             
//                return "redirect:/"; 
//            } else {
//                return "redirect:/register";
//            }
//        } catch (SQLException e) {
//            return "redirect:/register";
//        }
//    }
	

	@PostMapping("/register")
    public String processSignUp(@ModelAttribute  TaiKhoan taikhoan, HttpSession session, Model model) {
        session.setAttribute("taikhoan", taikhoan);
        try {
        	taikhoanService.checkTenTaiKhoan( (TaiKhoan) session.getAttribute("taikhoan"));
            String pin = taikhoanService.generateAndSendPIN(taikhoan.getEmail());
            session.setAttribute("registerPIN", pin);
            return "redirect:/verify-register-pin";
			
		} catch (Exception e) {
			model.addAttribute("kttaikhoan", true);
			return "user/dangky";
		}
        
    }
	
	@GetMapping("/verify-register-pin")
    public String showVerifyRegisterPinForm() {
        return "user/verify-register-pin";
    }
	
	@PostMapping("/verify-register-pin")
	public String processVerifyRegisterPin(@RequestParam(name = "pin") String pin, HttpSession session, Model model) {
	    // Kiểm tra xem pin có tồn tại trong request không
	    if (pin == null || pin.isEmpty()) {
	        model.addAttribute("error", "Invalid PIN. Please try again.");
	        return "user/verify-register-pin";
	    }

	    String sessionPIN = (String) session.getAttribute("registerPIN");
	    if (sessionPIN != null && sessionPIN.equals(pin)) {
	        TaiKhoan taikhoan = (TaiKhoan) session.getAttribute("taikhoan");
	        try {
	            taikhoanService.save(taikhoan, false);
	            session.removeAttribute("registerPIN");
	            session.removeAttribute("taikhoan");
	            return "redirect:/register-success";
	        } catch (SQLException e) {
	            // Xử lý ngoại lệ SQL nếu cần thiết
	            model.addAttribute("error", "Error saving user. Please try again later.");
	            return "user/register";
	        }
	    } else {
	        model.addAttribute("error", "Invalid PIN. Please try again.");
	        return "user/verify-register-pin";
	    }
	}
	
	@GetMapping("/register-success")
    public String showRegisterSuccess() {
        return "user/register-success";
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
