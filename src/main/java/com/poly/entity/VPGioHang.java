package com.poly.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VPGioHang {
	private Integer maSP;
	private String tenSP;
	private Long gia;
	private Integer tonKho;
	private String mota;
	private String anh;
	private int qty = 1;  
	
	SanPham sanpham = new SanPham();
	public VPGioHang(SanPham sanpham) {
        this.maSP = sanpham.getMaSP();
        this.tenSP = sanpham.getTenSP();
        this.gia = sanpham.getGia();
        this.anh = sanpham.getAnh();
        this.tonKho = sanpham.getTonKho();
        this.sanpham = sanpham;
    }
}
