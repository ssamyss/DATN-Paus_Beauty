package com.poly.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.poly.entity.ThuongHieu;

public interface ThuongHieuService {

	List<ThuongHieu> findAll();

	ThuongHieu findById(Integer maTH);

	ThuongHieu create(ThuongHieu thuonghieu);

	ThuongHieu update(ThuongHieu thuonghieu);

	void delete(Integer maTH);
	
	List<ThuongHieu> findRandomThuongHieu(Pageable pageable);


}
