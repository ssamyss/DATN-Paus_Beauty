package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.poly.dao.DonHangDao;
import com.poly.dao.SanPhamDao;
import com.poly.dao.TaiKhoanDao;

@Controller
public class AdminController {
	
	@Autowired
	SanPhamDao spdao;
	
	@Autowired
	DonHangDao dhdao;
	
	@Autowired
	TaiKhoanDao tkdao;
	
	
	@GetMapping("/admin")
	public String index(Model model) {
		model.addAttribute("product_count", spdao.getCountSP());
		model.addAttribute("order_count", dhdao.getCountDH());
		model.addAttribute("account_count", tkdao.getCountTK());
		model.addAttribute("tonkho_count", spdao.getCountTonKho());
		return "admin/index";
	}

	@GetMapping("/dataDanhmuc")
	public String dataTable() {
		return "admin/table-data-danhmuc";
	}

	@GetMapping("/dataProduct")
	public String dataProduct() {
		return "admin/table-data-product";
	}

	@GetMapping("/dataOrder")
	public String dataOrder() {
		return "admin/table-data-order";
	}

	@GetMapping("/baocao")
	public String baoCao(Model model ) {
		model.addAttribute("product_count", spdao.getCountSP());
		model.addAttribute("order_count", dhdao.getCountDH());
		model.addAttribute("account_count", tkdao.getCountTK());
		model.addAttribute("tonkho_count", spdao.getCountTonKho());
		return "admin/quan-ly-bao-cao";
	}

	@GetMapping("/donhang")
	public String donHang() {
		return "admin/form-add-don-hang";
	}

	@GetMapping("/danhmuc")
	public String nhanVien() {
		return "admin/form-add-catelogy";
	}

	@GetMapping("/sanpham")
	public String sanPham() {
		return "admin/form-add-san-pham";
	}

	@GetMapping("/chitietdonhang/{maDH}")
	public String chiTietDonHang(Model model, @PathVariable("maDH") String maDH) {
		
		return "admin/form-chi-tiet-don-hang";
	}
}
