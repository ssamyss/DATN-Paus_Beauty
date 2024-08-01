package com.poly.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Table(name = "san_pham")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPham implements Serializable {

	@Id
	@Column(name = "ma_sp")
	private Integer maSP;

	@Column(name = "ten_sp", columnDefinition = "nvarchar(max)")
	private String tenSP;

	@Column(name = "gia")
	private Long gia;

	@Column(name = "ton_kho")
	private Integer tonKho;

	@Column(name = "mo_ta", columnDefinition = "nvarchar(max)")
	private String mota;

	@Column(name = "anh")
	private String anh;

	@Column(name = "trang_thai")
	private boolean trangThai;

	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_cap_nhat")
	private Date createDate = new Date();

	@JsonIgnore
	@OneToMany(mappedBy = "sanPham")
	private List<DonHangChiTiet> donHangChiTiet;

	@JsonIgnore
	@OneToMany(mappedBy = "sanPham")
	private List<GioHang> gioHang;

	@ManyToOne
	@JoinColumn(name = "ma_th")
	private ThuongHieu thuongHieu;

	@ManyToOne
	@JoinColumn(name = "ma_lsp")
	private LoaiSanPham loaiSanPham;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "ma_loai_sp")
	private DanhMucLoaiSanPham danhMucLoaiSanPham;
}
