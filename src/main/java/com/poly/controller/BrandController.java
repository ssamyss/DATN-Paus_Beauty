package com.poly.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poly.entity.ThuongHieu;
import com.poly.entity.SanPham;
import com.poly.service.GioHangService;
import com.poly.dao.ThuongHieuDao;
import com.poly.dao.SanPhamDao;

@Controller
public class BrandController {
    @Autowired
    ThuongHieuDao tdao;

    @Autowired
    SanPhamDao sdao;

    @Autowired
    GioHangService gService;

    @GetMapping("/brand")
    public String brand(Model model, @RequestParam(name = "letter", required = false) String letter) {
        List<ThuongHieu> brands;
        if (letter != null && !letter.isEmpty()) {
            brands = tdao.findByTenTHStartingWith(letter);
        } else {
            brands = tdao.findAllOrderByTenTHAsc();
        }
        model.addAttribute("brands", brands);
        return "user/brand";
    }

    @GetMapping("/brand/data")
    @ResponseBody
    public ResponseEntity<List<ThuongHieu>> brandData(@RequestParam(name = "letter", required = false) String letter) {
        List<ThuongHieu> brands;
        if (letter != null && !letter.isEmpty()) {
            brands = tdao.findByTenTHStartingWith(letter);
        } else {
            brands = tdao.findAllOrderByTenTHAsc();
        }
        return ResponseEntity.ok(brands);
    }

    @GetMapping("/brand2/{maTH}")
    public String chiTietThuongHieu(@PathVariable("maTH") String maTH, Model model) {
        try {
            Integer maTHInteger = Integer.parseInt(maTH);
            Optional<ThuongHieu> optThuongHieu = tdao.findById(maTHInteger);
            if (optThuongHieu.isPresent()) {
                ThuongHieu thuongHieu = optThuongHieu.get();
                List<SanPham> sanPhams = thuongHieu.getSanPham();
                int count = sanPhams.size();

                model.addAttribute("thuongHieu", thuongHieu);
                model.addAttribute("sanPhams", sanPhams);

                return "user/brand2";
            } else {
                return "redirect:/brand"; 
            }
        } catch (NumberFormatException e) {
            return "redirect:/brand";
        }
    }
}
