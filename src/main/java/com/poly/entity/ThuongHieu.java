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
@Table(name = "thuong_hieu")
public class ThuongHieu implements Serializable{

	@Id
	@Column(name = "ma_th")
	private Integer maTH;
	
	@Column(name = "ten_th")
	private String tenTH;
	
	@Column(name = "anh")
	private String anh;
	
	@JsonIgnore
	@OneToMany(mappedBy = "thuongHieu")
	private List<SanPham> sanPham;
}
