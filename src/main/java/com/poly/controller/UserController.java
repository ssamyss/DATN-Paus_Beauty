package com.poly.controller;

import java.util.List;
import java.util.Locale;
import java.text.NumberFormat;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poly.dao.DanhMucLoaiSanPhamDao;
import com.poly.dao.SanPhamDao;
import com.poly.dao.TaiKhoanDao;
import com.poly.entity.DanhMucLoaiSanPham;
import com.poly.entity.SanPham;
import com.poly.entity.TaiKhoan;
import com.poly.service.SanPhamService;

@Controller
public class UserController {

	@Autowired
	SanPhamDao spDao;

	@Autowired
	TaiKhoanDao tkDao;

	@Autowired
	DanhMucLoaiSanPhamDao dmlspDao;

	@Autowired
	SanPhamService spService;

	@Autowired
	private JavaMailSender mailSender;

	@GetMapping("")
	public String index(Model model) {
		model.addAttribute("categories", dmlspDao.findAll());
		return "user/index";
	}

//	@GetMapping("/product")
//	public String productList(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
//		int pageSize = 50;
//		Pageable pageable = PageRequest.of(page - 1, pageSize);
//		Page<SanPham> sanphamPage = spDao.findAll(pageable);
//		model.addAttribute("sanphamPage", sanphamPage);
//		model.addAttribute("categories", dmlspDao.findAll());
//		return "user/sanpham";
//	}
	@GetMapping("/product")
	public String productList(Model model,
	                          @RequestParam(value = "page", defaultValue = "1") int page,
	                          @RequestParam(value = "price", required = false) String price,
	                          @RequestParam(value = "sort", required = false) String sortOrder) {

	    int pageSize = 200;
	    Pageable pageable = PageRequest.of(page - 1, pageSize);
	    Page<SanPham> sanphamPage;
	   
	    
	    if ("under500".equals(price)) {
	    	
	        sanphamPage = spDao.findByPriceLessThan((long) 500000, pageable);
	    } else if ("over1000".equals(price)) {
	        sanphamPage = spDao.findByPriceLargeThan((long) 1000000, pageable);
	        
	    } else if ("between500-1000".equals(price)) {
	        sanphamPage = spDao.findByPriceBetween("between500-1000", pageable);
	    } else if ("desc".equals(sortOrder)) {
	    	
	        sanphamPage = spDao.getProductsSortedByPrice(sortOrder, pageable); // Ensure method name matches
	    } else {
	    	
	        sanphamPage = spDao.findAll(pageable);
	    }
	    
	    model.addAttribute("sanphamPage", sanphamPage);
	    model.addAttribute("categories", dmlspDao.findAll());
	    return "user/sanpham";
	}

	
	
	
	


	@GetMapping("/search")
	public String search(Model model, @RequestParam("keyword") String keyword) {
		List<SanPham> searchResults = spDao.searchByTenSP(keyword);
		model.addAttribute("searchResults", searchResults);
		model.addAttribute("keyword", keyword);
		return "user/search-results";
	}

	@GetMapping("/category/{maLSP}")
	public String getProductByCategory(@PathVariable("maLSP") Integer maLSP, Model model,
			@RequestParam(value = "page", defaultValue = "1") int page) {
		int pageSize = 200;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<SanPham> sanphamPage = spDao.findByDanhMucLoaiSanPhamMaLSP(maLSP, pageable);
		model.addAttribute("sanphamPage", sanphamPage);
		model.addAttribute("categories", dmlspDao.findAll());
		return "user/sanpham";
	}

	@GetMapping("/detail/{id}")
	public String productDetail(Model model, @PathVariable("id") Integer maSP) {
		SanPham sp = spDao.findById(maSP).orElse(null);
		if (sp == null) {
			return "redirect:/";
		}
		model.addAttribute("sanpham", sp);
		return "user/product-detail";
	}

	@GetMapping("/about")
	public String about() {
		return "user/about";
	}

	@GetMapping("/contact")
	public String contact() {
		return "user/contact";
	}

	@GetMapping("/quydinhchung")
	public String quyDinhChung() {
		return "user/quydinhchung";
	}

	@GetMapping("/doitra")
	public String doiTra() {
		return "user/doitra";
	}

	@GetMapping("/thong-tin-ca-nhan")
	public String thongTinCaNhan(Model model) {
		// Assuming that you have a method to get the currently logged-in user
		TaiKhoan taiKhoan = tkDao.findByTenTaiKhoan("currentUsername"); // Replace with actual method to get the
																		// logged-in user
		model.addAttribute("taikhoan", taiKhoan);
		return "user/thong-tin-ca-nhan";
	}

	@PutMapping("/rest/taikhoan")
	@ResponseBody
	public ResponseEntity<TaiKhoan> updateTaiKhoan(@RequestBody TaiKhoan taiKhoan) {
		// Find the existing account by username or ID
		TaiKhoan existingTaiKhoan = tkDao.findById(taiKhoan.getTenTaiKhoan()).orElse(null);
		if (existingTaiKhoan != null) {
			// Update the existing account details
			existingTaiKhoan.setHoVaTen(taiKhoan.getHoVaTen());
			existingTaiKhoan.setDiaChi(taiKhoan.getDiaChi());
			existingTaiKhoan.setSDT(taiKhoan.getSDT());
			existingTaiKhoan.setEmail(taiKhoan.getEmail());
			tkDao.save(existingTaiKhoan);
			return ResponseEntity.ok(existingTaiKhoan);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/chitietsanpham")
	public String chitietsanpham() {
		return "user/chitietsanpham";
	}

	@PostMapping("/sendEmail")
	public String sendEmail(@RequestParam("email") String email, @RequestParam("message") String message) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo("phambin984@gmail.com");
			mailMessage.setSubject("Thông tin liên hệ từ: " + email);
			mailMessage.setText(message);
			mailMessage.setFrom(email);

			mailSender.send(mailMessage);
			return "redirect:/contact?success";
		} catch (Exception e) {
			return "redirect:/contact?error";
		}
	}
}
