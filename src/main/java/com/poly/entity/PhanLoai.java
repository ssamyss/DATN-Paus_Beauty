package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity 
@Table(name = "phan_loai")
public class PhanLoai implements Serializable{

		@Id
		@Column(name = "ma_pl")
		private Integer maPL;
		
		@Column(name = "ten_pl")
		private String tenPL;
		
		@Column(name = "anh")
		private String anh;
		
		@JsonIgnore
		@OneToMany(mappedBy = "phanLoai")
		private List<SanPham> sanPham;
		
		@ManyToOne
		@JoinColumn(name = "ma_lsp")
		private LoaiSanPham loaiSanPham;
}
