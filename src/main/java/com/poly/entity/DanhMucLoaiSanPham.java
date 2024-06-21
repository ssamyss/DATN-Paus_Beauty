package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity 
@Table(name = "danh_muc_Loai_sp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DanhMucLoaiSanPham implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ma_dmlsp")
	private Integer maLSP;
	
	@Column(name = "ten", columnDefinition = "nvarchar(max)")
	private String tenLSP;
	
	@JsonIgnore
	@OneToMany(mappedBy = "danhMucLoaiSanPham")
	private List<LoaiSanPham> loaiSanPham;
}
