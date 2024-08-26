package com.poly.controller;

import java.net.http.HttpRequest;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.poly.dao.DonHangDao;
import com.poly.dao.SanPhamDao;
import com.poly.dao.TaiKhoanDao;
import com.poly.entity.SanPham;
import com.poly.service.DonHangService;
import com.poly.service.SanPhamService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

	@Autowired
	SanPhamDao spdao;

	@Autowired
	DonHangDao dhdao;

	@Autowired
	TaiKhoanDao tkdao;

	@Autowired
	SanPhamService sanPhamService;

	@Autowired
	DonHangService donHangService;
		
	@GetMapping("/admin")
	public String index(Model model) {
		model.addAttribute("product_count", spdao.getCountSP());
		model.addAttribute("order_count", dhdao.getCountDH());
		model.addAttribute("account_count", tkdao.getCountTK());
		model.addAttribute("tonkho_count", spdao.getCountTonKho());
		model.addAttribute("spHetHang_count", spdao.getCounthHetSP());
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

	@GetMapping("/dataOrder/xacnhan")
	public String dataOrderDangxuly() {
		return "admin/table-data-order-DXL";
	}
	
	@GetMapping("/dataOrder/cholayhang")
	public String dataOrderChoLayHang() {
		return "admin/table-data-order-CLH";
	}
	
	@GetMapping("/dataOrder/danggiaohang")
	public String dataOrderDangGiaoHang() {
		return "admin/table-data-order-DGH";
	}
	
	@GetMapping("/dataOrder/hoanthanh")
	public String dataOrderHoanthanh() {
		return "admin/table-data-order-HT";
	}

	@GetMapping("/dataOrder/huydon")
	public String dataOrderHuydon() {
		return "admin/table-data-order-HD";
	}
	
	@GetMapping("/baocao")
	public String baoCao(Model model) {
		model.addAttribute("product_count", spdao.getCountSP());
		model.addAttribute("order_count", dhdao.getCountDH());
		model.addAttribute("account_count", tkdao.getCountTK());
		model.addAttribute("tonkho_count", spdao.getCountTonKho());
		double orderCountTongDT = dhdao.getCountTongDT();
		NumberFormat vietnameseFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
		String formattedOrderCount = vietnameseFormat.format(orderCountTongDT);
		model.addAttribute("order_countTongDT", formattedOrderCount);
		List<SanPham> top5SanPham = sanPhamService.getTop5SanPhamBanChay();
		model.addAttribute("top5SanPham", top5SanPham);
		model.addAttribute("order_countHuyDon", dhdao.getCountHuyDon());
		return "admin/quan-ly-bao-cao";
	}

	@GetMapping("/donhang")
	public String donHang() {
		return "admin/form-add-don-hang";
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
