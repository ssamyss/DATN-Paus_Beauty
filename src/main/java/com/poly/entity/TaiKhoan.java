package com.poly.entity;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@Table(name = "tai_khoan")
public class TaiKhoan implements Serializable {

	/* @NotBlank(message = "Vui lòng nhập username") */
	@Id
	@Column(name = "ten_tai_khoan")
	private String TenTaiKhoan;

	/* @NotBlank(message = "Vui lòng nhập password") */
	@Column(name = "mat_khau")
	private String MatKhau;

	@Column(name = "ho_va_ten", columnDefinition = "nvarchar(max)")
	/* @NotEmpty(message = "Vui lòng nhập họ tên") */
	private String HoVaTen;

//	@NotBlank(message = "Vui lòng nhập số điện thoại")
//	@Size(min = 10, max = 10, message = "Số điện thoại phải có 10 chữ số")
//	@Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại không hợp lệ")
	@Column(name = "sdt")
	private String SDT;

//	@NotBlank(message = "Vui lòng nhập địa chỉ")
	@Column(name = "dia_chi", columnDefinition = "nvarchar(max)")
	private String DiaChi;

//	@NotBlank(message = "Vui lòng nhập email")
//	@Email(message = "Email sai định dạng")
	@Column(name = "email")
	private String Email;
	
	/* @NotNull(message = "Role không được bỏ trống") */
	@Column(name = "role")
	private boolean Role;
	
	@JsonIgnore
	@OneToMany(mappedBy = "taiKhoan")
	private List<DonHang> donHang;
	
	@JsonIgnore
	@OneToMany(mappedBy = "taiKhoan")
	private List<GioHang> gioHang;
}
