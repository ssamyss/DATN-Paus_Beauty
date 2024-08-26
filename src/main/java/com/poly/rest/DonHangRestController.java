package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.DonHang;
import com.poly.service.DonHangService;

import jakarta.servlet.http.HttpSession;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/donhang")
public class DonHangRestController {

	@Autowired
	DonHangService donHangService;
	
	@GetMapping()
	public List<DonHang> getAll() {
		return donHangService.findAll();
	}
	
	@GetMapping("canhan")
	public List<DonHang> getDHs(HttpSession session) {
		String tentaikhoan = (String) session.getAttribute("tentaikhoan");
		return donHangService.getOrders(tentaikhoan);
	}
	
	@GetMapping("{maDH}")
	public DonHang getOne(@PathVariable("maDH") String maDH) {
		return donHangService.findById(maDH);
	}
	
	@PostMapping()
	public DonHang create(@RequestBody DonHang donHang) {
		return donHangService.create(donHang);
	}
	
	@PutMapping("{maDH}")
	public DonHang update(@PathVariable("maDH") String maDH, @RequestBody DonHang donHang) {
		return donHangService.update(donHang);
	}
	
	@DeleteMapping("{maDH}")
	public void delete(@PathVariable("maDH") String maDH) {
		donHangService.delete(maDH);
	}
	
	@DeleteMapping()
    public void deleteAll() {
		donHangService.deleteAll();
    }
	
	@GetMapping("/doanhthu")
    public List<Object[]> getMonthlyRevenue() {
        return donHangService.getMonthlyRevenue();
    }
    
    @GetMapping("/doanhthu/quy")
    public List<Object[]> getQuarterlyRevenue() {
        return donHangService.getQuarterlyRevenue();
    }
    
    @GetMapping("/dangxuly")
    public List<DonHang> getDangXuLyOrders() {
        return donHangService.findAllByTrangThaiDangXuLy();
    }
    
    @GetMapping("/cholayhang")
    public List<DonHang> getChoLayHangOrders() {
        return donHangService.findAllByTrangThaiChoLayHang();
    }
    
    @GetMapping("/danggiaohang")
    public List<DonHang> getDangGiaoHangOrders() {
        return donHangService.findAllByTrangThaiDangGiaoHang();
    }

    @GetMapping("/hoanthanh")
    public List<DonHang> getHoanThanhOrders() {
        return donHangService.findAllByTrangThaiHoanThanh();
    }
    
    @GetMapping("/huydon")
    public List<DonHang> getHuyDonrders() {
        return donHangService.findAllByTrangThaiHuyDon();
    }
}
