package com.poly.serviceImpl;

import java.util.Collection;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.poly.entity.VPGioHang;
import com.poly.service.GioHangService;

@Service
public class GioHangServiceImpl implements GioHangService {
Map<Integer, VPGioHang> maps = new HashMap<>(); //gio hang
	
	@Override
	public void remove(int id) {
		maps.remove(id);
		
	}

	@Override
	public void add(VPGioHang item) {
		VPGioHang cartItem = maps.get(item.getMaSP());
		if (cartItem == null) {
			maps.put(item.getMaSP(), item);
		}
		else
		{
			cartItem.setQty(cartItem.getQty() +1);
		}
		
	}

	@Override
	public double getAmount() {
		return maps.values().stream()
				.mapToDouble(item -> item.getQty() * item.getGia())
				.sum();		
	}

	@Override
	public int gettotalCount() {
	    int totalCount = 0;
	    for (VPGioHang cartItem : maps.values()) 
	    {
	        totalCount += cartItem.getQty();
	    }
	    return totalCount;
	}

	@Override
	public Collection<VPGioHang> getAllItems() {
		return maps.values();
	}

	@Override
	public void clear() {
		maps.clear();	
	}

	@Override
	public VPGioHang update(int id, int qty) {
		VPGioHang cartItem = maps.get(id);
		cartItem.setQty(qty);
		return cartItem;
	}
	
	@Override
	public VPGioHang plus(int id) {
		VPGioHang cartItem = maps.get(id);
	    int qty = cartItem.getQty();
	    int tonKho = cartItem.getTonKho();

	    // Kiểm tra nếu số lượng đã đạt tối đa
	    if (qty < tonKho) {
	        cartItem.setQty(qty + 1);
	    } else {
	        // Nếu vượt quá số lượng tonkho, bạn có thể thực hiện xử lý hoặc thông báo tùy thuộc vào yêu cầu của bạn.
	        // Ở đây, tôi đơn giản là không thay đổi giá trị qty nếu đã vượt quá giới hạn.
	        // Bạn có thể thay đổi phần này tùy thuộc vào yêu cầu cụ thể của bạn..
	        System.out.println("Số lượng đã vượt quá giới hạn.");
	    }

	    return cartItem;
	}

	@Override
	public VPGioHang minus(int id) {
		VPGioHang cartItem = maps.get(id);
	    int qty = cartItem.getQty();
	    if (qty < 1) {
	    	maps.remove(id);
		}
	    else
	    {
	    cartItem.setQty(qty - 1);
	    }
	    return cartItem;
	}
}
