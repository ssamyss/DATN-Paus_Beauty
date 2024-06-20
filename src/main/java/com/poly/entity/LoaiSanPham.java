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
@Table(name = "loai_sp")
public class LoaiSanPham implements Serializable{

		@Id
		@Column(name = "ma_lsp")
		private Integer maPL;
		
		@Column(name = "ten", columnDefinition = "nvarchar(max)")
		private String tenPL;	
		
		@JsonIgnore
		@OneToMany(mappedBy = "loaiSanPham")
		private List<SanPham> sanPham;
		
		@ManyToOne
		@JoinColumn(name = "ma_dmlsp")
		private DanhMucLoaiSanPham danhMucLoaiSanPham;
}
