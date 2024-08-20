package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poly.dao.DanhMucLoaiSanPhamDao;
import com.poly.dao.SanPhamDao;
import com.poly.entity.DanhMucLoaiSanPham;
import com.poly.entity.SanPham;
import com.poly.service.SanPhamService;

@Controller
public class UserController {

	@Autowired
	SanPhamDao spDao;

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

	@GetMapping("/product")
	public String productList(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
		int pageSize = 8;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<SanPham> sanphamPage = spDao.findAll(pageable);
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
		int pageSize = 8;
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<SanPham> sanphamPage = spDao.findByDanhMucLoaiSanPham_MaLSP(maLSP, pageable);
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
	public String thongTin() {
		return "user/thong-tin-ca-nhan";
	}

	@GetMapping("/chitietsanpham")
	public String chitietsanpham(@RequestParam(name = "maSP") Integer maSP, Model model) {
		SanPham product = spService.findById(maSP);
		if (product != null) {
			model.addAttribute("product", product);
			return "user/chitietsanpham";
		} else {
			return "redirect:/error";
		}
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
