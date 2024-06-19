package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity 
@Table(name = "loai_san_pham")
public class LoaiSanPham implements Serializable{

	@Id
	@Column(name = "ma_lsp")
	private Integer maLSP;
	
	@Column(name = "ten_lsp")
	private String tenLSP;
	
	@JsonIgnore
	@OneToMany(mappedBy = "loaiSanPham")
	private List<PhanLoai> phanLoai;
}
