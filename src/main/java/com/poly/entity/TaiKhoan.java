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

    @Id
    @Column(name = "ten_tai_khoan")
    private String TenTaiKhoan;

    @Column(name = "mat_khau")
    private String MatKhau;

    @Column(name = "ho_va_ten", columnDefinition = "nvarchar(max)")
    private String HoVaTen;

    @Column(name = "sdt")
    private String SDT;

    @Column(name = "dia_chi", columnDefinition = "nvarchar(max)")
    private String DiaChi;

    @Column(name = "email")
    private String email;
    
    @Column(name = "role")
    private boolean Role;

    @JsonIgnore
    @OneToMany(mappedBy = "taiKhoan")
    private List<DonHang> donHang;

    @JsonIgnore
    @OneToMany(mappedBy = "taiKhoan")
    private List<GioHang> gioHang;
}
