package com.poly.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.entity.LoaiSanPham;
import com.poly.entity.SanPham;
import com.poly.entity.ThuongHieu;

public interface SanPhamDao extends JpaRepository<SanPham, Integer> {

	Page<SanPham> findByDanhMucLoaiSanPham_MaLSP(Integer maLSP, Pageable pageable);

	Page<SanPham> findByLoaiSanPhamIn(List<LoaiSanPham> loaiSanPham, Pageable pageable);

	@Query("SELECT sp FROM SanPham sp WHERE sp.loaiSanPham.danhMucLoaiSanPham.maLSP = :maLSP")
	Page<SanPham> findByDanhMucLoaiSanPhamMaLSP(@Param("maLSP") Integer maLSP, Pageable pageable);

	@Query("SELECT count(o) FROM SanPham o")
	Integer getCountSP();

	@Query("SELECT count(o) FROM SanPham o WHERE o.tonKho < 5")
	Integer getCountTonKho();

	@Query("SELECT count(o) FROM SanPham o WHERE o.tonKho =0")
	Integer getCounthHetSP();

	@Query("SELECT sp FROM SanPham sp WHERE sp.tenSP LIKE %:keyword%")
	List<SanPham> searchByTenSP(@Param("keyword") String keyword);

	@Query("SELECT sp FROM SanPham sp WHERE sp.danhMucLoaiSanPham.tenLSP = :category")
	List<SanPham> findByCategory(@Param("category") String category);

	List<SanPham> findByTenSPContainingIgnoreCase(String tenSP);

	Page<SanPham> findAll(Pageable pageable);

	@Query("SELECT sp, SUM(ct.soLuong) AS totalQuantity " + "FROM DonHangChiTiet ct " + "JOIN ct.sanPham sp "
			+ "GROUP BY sp " + "ORDER BY totalQuantity DESC")
	List<Object[]> findTop5SanPhamBanChay(Pageable pageable);

	@Query("SELECT sp FROM SanPham sp WHERE sp.tonKho = 0")
	List<SanPham> findByTonKho(@Param("tonKho") Integer tonKho);
	
	@Query("SELECT sp FROM SanPham sp WHERE sp.tonKho <= 10")
	List<SanPham> findBySapHetHang(@Param("tonKho") Integer tonKho);

	@Query("SELECT dm.tenLSP, COUNT(sp) FROM SanPham sp JOIN sp.loaiSanPham lsp JOIN lsp.danhMucLoaiSanPham dm GROUP BY dm.tenLSP")
	List<Object[]> getTotalProductsByDanhMuc();

	@Query("SELECT sp FROM SanPham sp " + "JOIN sp.loaiSanPham lsp " + "JOIN lsp.danhMucLoaiSanPham dm "
			+ "WHERE dm.tenLSP = :categoryName " + "ORDER BY NEWID()")
	List<SanPham> findRandomByCategory(@Param("categoryName") String categoryName, Pageable pageable);
	
	@Query("SELECT sp FROM SanPham sp WHERE sp.gia <500000")
	Page<SanPham> findByPriceLessThan(@Param("gia") Long gia, Pageable pageable);
	@Query("SELECT sp FROM SanPham sp WHERE sp.gia >1000000")
	Page<SanPham> findByPriceLargeThan(@Param("gia") Long gia, Pageable pageable);
	@Query("SELECT sp FROM SanPham sp WHERE sp.gia between 500000 And 1000000")
	Page<SanPham> findByPriceBetween(@Param("gia") String string, Pageable pageable);
	
	@Query("SELECT sp FROM SanPham sp ORDER BY sp.gia DESC")
	Page<SanPham> getProductsSortedByPrice(String sortOrder, Pageable pageable);
}
