package com.poly.service;

import java.util.Collection;
import com.poly.entity.VPGioHang;

public interface GioHangService {
	void remove(int id);
	void add(VPGioHang item);
	double getAmount();
	Collection<VPGioHang> getAllItems();
	void clear();
	VPGioHang update(int proId, int qty);
	VPGioHang plus(int id);
	VPGioHang minus(int id);
	int gettotalCount();
}
