package com.poly.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.poly.dao.TaiKhoanDao;
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

	@Autowired
	TaiKhoanDao tkdao;

	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	// Đăng ký admin
	@GetMapping("/register/admin")
	public String register_admin(Model model) {
		model.addAttribute("kttaikhoan", false);
		model.addAttribute("taikhoanRequest", new TaiKhoan());
		return "user/dangkyadmin";
	}

	@PostMapping("/register/admin")
	public String processSignUp_admin(@ModelAttribute TaiKhoan taikhoan, HttpSession session, Model model) {
		session.setAttribute("taikhoan", taikhoan);
		try {
			taikhoanService.checkTenTaiKhoan((TaiKhoan) session.getAttribute("taikhoan"));
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
	public String processVerifyRegisterPin_admin(@RequestParam(name = "pin") String pin, HttpSession session,
			Model model) {
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

	// Đăng ký
	@GetMapping("/register")
	public String Register(Model model) {
		model.addAttribute("kttaikhoan", false);
		model.addAttribute("taikhoanRequest", new TaiKhoan());
		return "user/dangky";
	}

	@PostMapping("/register")
	@ResponseBody
	public Map<String, Object> processSignUp(@ModelAttribute TaiKhoan taikhoan, @RequestParam String xnmatKhau,
			HttpSession session) {
		Map<String, Object> response = new HashMap<>();

		// Kiểm tra mật khẩu và xác nhận mật khẩu
		if (!taikhoan.getMatKhau().equals(xnmatKhau)) {
			response.put("success", false);
			response.put("errors", List.of("Mật khẩu và xác nhận mật khẩu không khớp."));
			return response;
		}

		// Kiểm tra chiều dài mật khẩu
		if (taikhoan.getMatKhau().length() < 6) {
			response.put("success", false);
			response.put("errors", List.of("Mật khẩu phải dài hơn 6 ký tự."));
			return response;
		}

		// Kiểm tra định dạng số điện thoại
		if (!taikhoan.getSDT().matches("\\d{10}")) {
			response.put("success", false);
			response.put("errors", List.of("Số điện thoại phải là 10 số và không chứa chữ cái."));
			return response;
		}

		// Kiểm tra tính duy nhất của email
		if (taikhoanService.isEmailExists(taikhoan.getEmail())) {
			response.put("success", false);
			response.put("errors", List.of("Email đã tồn tại trong hệ thống."));
			return response;
		}

		try {
			taikhoanService.checkTenTaiKhoan(taikhoan);
			String pin = taikhoanService.generateAndSendPIN(taikhoan.getEmail());
			session.setAttribute("registerPIN", pin);
			response.put("success", true);
			session.setAttribute("taikhoan", taikhoan);
			return response;
		} catch (Exception e) {
			response.put("success", false);
			response.put("errors", List.of("Tên tài khoản đã tồn tại."));
			return response;
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

	// Quên mật khẩu
	@GetMapping("/forgotpass")
	public String forgotPass(Model model) {
		return "user/forgotpass";
	}

	@PostMapping("/forgotpass")
	public String forgotPass(TaiKhoan taikhoan, RedirectAttributes redirectAttributes) {
		if (taikhoan.getEmail() != null && !taikhoan.getEmail().isEmpty()) {
			String link = taikhoanService.generateAndSendLinkResetPass(taikhoan.getEmail());
			redirectAttributes.addFlashAttribute("message", "Email đã được gửi thành công!");
			return "redirect:/forgotpass";
		} else {
			// Xử lý trường hợp email null hoặc rỗng
			redirectAttributes.addFlashAttribute("error", "Email không hợp lệ!");
			return "redirect:/forgotpass";
		}
	}

	@GetMapping("/resetpassword/{email}")
	public String resetPasswordPage(@PathVariable("email") String email, Model model) {
		model.addAttribute("userEmail", email);
		return "user/reset-pass";
	}

	@PostMapping("/resetpassword/{email}")
	public String resetPassword(@PathVariable("email") String email, @RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword, RedirectAttributes redirectAttributes) {
		if (newPassword.length() < 6 || confirmPassword.length() < 6) {
			redirectAttributes.addFlashAttribute("error", "Mật khẩu phải có ít nhất 6 ký tự.");
			return "redirect:/resetpassword/" + email;
		}

		if (!newPassword.equals(confirmPassword)) {
			redirectAttributes.addFlashAttribute("error", "Mật khẩu xác nhận không khớp.");
			return "redirect:/resetpassword/" + email;
		}

		TaiKhoan taikhoan = tkdao.findByEmail(email);
		if (taikhoan != null) {
			taikhoan.setMatKhau(bcrypt.encode(newPassword));
			tkdao.save(taikhoan);
			return "redirect:/login";
		}

		redirectAttributes.addFlashAttribute("error", "Email không hợp lệ.");
		return "redirect:/resetpassword/" + email;
	}

	// Đăng nhập
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
					if(taikhoan.isRole())
					{
						return "redirect:/";
					}
					HttpSession session = request.getSession();
					session.setAttribute("tentaikhoan", TenTaiKhoan);
					System.out.println("TaiKhoanController4: "+TenTaiKhoan);
					System.out.println("TaiKhoanController5: "+taikhoan);
					System.out.println("TaiKhoanController6: "+session.getAttribute("tentaikhoan"));	
					
					// Redirect đến trang tương ứng dựa vào vai trò của người dùng
					
						return "redirect:/";
					
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

	@RequestMapping("/loginAdmin")
	public String loginAdmin(Model model,@RequestParam(value = "username",required = false)String username,
			@RequestParam(value="password",required = false)  String password) {
		try {
			TaiKhoan taikhoan = taikhoanService.findById(username);
			if(taikhoan != null) {
				if (BCrypt.checkpw(password, taikhoan.getMatKhau())) {
					HttpSession session = request.getSession();
					session.setAttribute("user", taikhoan);
					System.out.println("TaiKhoanController1: "+username);
					System.out.println("TaiKhoanController2: "+taikhoan);
					System.out.println("TaiKhoanController3: "+session.getAttribute("user"));	
					if (taikhoan.isRole() ) {
						return "redirect:/admin";
					} else {
						return "redirect:/";
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("message", "Tên người dùng sai");
		}		
		
		return "/user/dangnhap_admin";
		
	}

	@GetMapping("/logout2")
	public String logout() {
		HttpSession session = request.getSession();
		session.removeAttribute("tentaikhoan");
		return "redirect:/";
	}
	@GetMapping("/logout3")
	public String logoutAdmin() {
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		return "redirect:/admin";
	}

}
