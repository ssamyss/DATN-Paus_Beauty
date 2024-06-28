package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poly.dao.SanPhamDao;
import com.poly.entity.SanPham;
import com.poly.entity.VPGioHang;
import com.poly.service.GioHangService;

@Controller
public class GioHangController {
//	@Autowired
//	SanPhamDao spDao;
//	
//	@Autowired
//	GioHangService ghService;
//	
//	//giỏ hàng
//		@GetMapping("/cart")
//		public String home(Model model) {
//			model.addAttribute("sanpham", ghService.getAllItems());
//			model.addAttribute("count", ghService.gettotalCount());
//			model.addAttribute("roundedAmount", ghService.getAmount());
//			return "user/cart";
//		}
//
//		//Thêm vào giỏ hàng
//		@GetMapping("/cart/{id}")
//		public String detail(Model model, @PathVariable("id") Integer id) {
//			SanPham sanpham = spDao.findById(id).get();
//			model.addAttribute("sanpham", sanpham);
//			if (sanpham != null) {
//				VPGioHang item = new VPGioHang();
//				item.setMaSP(sanpham.getMaSP());
//				item.setTenSP(sanpham.getTenSP());
//				item.setAnh(sanpham.getAnh());
//				item.setGia(sanpham.getGia());
//				item.setTonKho(sanpham.getTonKho());
//				item.setQty(1);
//				ghService.add(item);
//			}
//			model.addAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng.");
//			return "redirect:/detail/{id}";
//		}
//		
//		//Mua ngay 
//		@GetMapping("/cart/add/{id}")
//		public String cartIted(Model model, @PathVariable("id") Integer id) {
//			SanPham sanpham = spDao.findById(id).get();
//			model.addAttribute("sanpham", sanpham);
//			if (sanpham != null) {
//				VPGioHang item = new VPGioHang();
//				item.setMaSP(sanpham.getMaSP());
//				item.setTenSP(sanpham.getTenSP());
//				item.setAnh(sanpham.getAnh());
//				item.setGia(sanpham.getGia());
//				item.setTonKho(sanpham.getTonKho());
//				item.setQty(1);
//				ghService.add(item);
//			}
//			model.addAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng.");
//			return "redirect:/cart";
//		}
//
//		//Tăng giá khi thêm số lượng sản phẩm
//		@GetMapping("/cart/update/{id}/plus") // annotation xử lí yêu cầu
//		public String plusQty(@PathVariable("id") int id, Model model) {
//			ghService.plus(id);
//			return "redirect:/cart";
//		}
//
//		//Ngược lại
//		@GetMapping("/cart/update/{id}/minus") // annotation xử lí yêu cầu
//		public String minusQty(@PathVariable("id") int id) {
//			ghService.minus(id);
//			return "redirect:/cart";
//		}
//
//		//Xóa sản phẩm
//		@GetMapping("/cart/remove/{id}")
//		public String RemoveCart(@PathVariable("id") int id) {
//			ghService.remove(id);
//			return "redirect:/cart";
//		}
//
//		//Xóa toàn bộ sản phẩm
//		@RequestMapping("/cart/clear")
//		public String ClearCart() {
//			ghService.clear();
//			return "redirect:/cart";
//		}
}
