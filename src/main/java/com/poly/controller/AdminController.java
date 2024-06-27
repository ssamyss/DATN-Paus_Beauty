package com.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	@GetMapping("")
	public String index() {
		return "admin/index";
	}

	@GetMapping("/dataTable")
	public String dataTable() {
		return "admin/table-data-table";
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
	public String baoCao() {
		return "admin/quan-ly-bao-cao";
	}

	@GetMapping("/donhang")
	public String donHang() {
		return "admin/form-add-don-hang";
	}

	@GetMapping("/nhanvien")
	public String nhanVien() {
		return "admin/form-add-nhan-vien";
	}

	@GetMapping("/sanpham")
	public String sanPham() {
		return "admin/form-add-san-pham";
	}

	@GetMapping("/chitietdonhang/{maDH}")
	public String chiTietDonHang() {
		return "admin/form-chi-tiet-don-hang";
	}
}
