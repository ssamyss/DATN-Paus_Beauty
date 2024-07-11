package com.poly.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Table(name = "gio_hang")
public class GioHang implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer maGH;
	
	@ManyToOne
	@JoinColumn(name = "ten_tai_khoan")
	private TaiKhoan taiKhoan;

	@ManyToOne
	@JoinColumn(name = "ma_sp")
	private SanPham sanPham;
	
	@Column(name = "so_luong")
	private Integer soLuong;
}
