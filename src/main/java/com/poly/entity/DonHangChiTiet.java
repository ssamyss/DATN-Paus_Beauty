package com.poly.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity 
@Table(name = "don_hang_chi_tiet")
public class DonHangChiTiet implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ma_dhct")
	private Integer maDHCT;
	
	@Column(name = "so_luong")
	private Integer soLuong;
	
	@Column(name = "gia")
	private Double gia;
	
	@ManyToOne
	@JoinColumn(name = "ma_sp")
	private SanPham sanPham;
	
	@ManyToOne
	@JoinColumn(name = "ma_dh")
	private DonHang donHang;
	
}
