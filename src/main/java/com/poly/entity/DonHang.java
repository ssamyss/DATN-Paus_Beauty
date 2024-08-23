package com.poly.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Table(name = "don_hang")
public class DonHang implements Serializable{
	
	@Id
	@Column(name = "ma_dh")
	private String maDH;
	
	@Column(name = "tong_gia")
	private Long tongGia;
	
//	@NotBlank(message = "Vui lòng nhập số điện thoại")
//	@Size(min = 10, max = 10, message = "Số điện thoại phải có 10 chữ số")
//	@Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại không hợp lệ")
	@Column(name = "sdt")
	private String sDT;

//	@NotBlank(message = "Vui lòng nhập địa chỉ")
	@Column(name = "dia_chi", columnDefinition = "nvarchar(max)")
	private String diaChi;
	
	@Column(name = "note", columnDefinition = "nvarchar(max)")
	private String note;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "ngay_tao")
	private Date createDate = new Date();
	
	@Column(name = "trang_thai")
    @Enumerated(EnumType.STRING)
    private TrangThaiDonHang trangThai;
	
	@ManyToOne
	@JoinColumn(name = "ten_tai_khoan")
	private TaiKhoan taiKhoan;

	@JsonIgnore
	@OneToMany(mappedBy = "donHang")
	private List<DonHangChiTiet> donHangChiTiet;
}
