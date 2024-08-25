package com.poly.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.entity.ThuongHieu;
import com.poly.service.ThuongHieuService;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/thuonghieu")
public class ThuongHieuRestController {

	@Autowired
	ThuongHieuService thuongHieuService;
	
	@GetMapping()
	public List<ThuongHieu> getAll() {
		return thuongHieuService.findAll();
	}
	
	@GetMapping("{maTH}")
	public ThuongHieu getOne(@PathVariable("maTH") Integer maTH) {
		return thuongHieuService.findById(maTH);
	}
	
	@PostMapping()
	public ThuongHieu create(@RequestBody ThuongHieu thuonghieu) {
		return thuongHieuService.create(thuonghieu);
	}
	
	@PutMapping("{maTH}")
	public ThuongHieu update(@PathVariable("maTH") Integer maTH, @RequestBody ThuongHieu brand) {
		return thuongHieuService.update(brand);
	}
	
	@DeleteMapping("{maTH}")
	public void delete(@PathVariable("maTH") Integer maTH) {
		thuongHieuService.delete(maTH);
	}
	
	 @GetMapping("/random")
	    public List<ThuongHieu> getRandomBrands() {
	        return thuongHieuService.findRandomThuongHieu(PageRequest.of(0, 5));
	    }

}
