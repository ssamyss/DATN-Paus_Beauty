package com.poly.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.poly.entity.ThuongHieu;

public interface ThuongHieuDao extends JpaRepository<ThuongHieu, Integer> {

	Page<ThuongHieu> findAll(Pageable pageable);

	@Query("SELECT th FROM ThuongHieu th ORDER BY th.tenTH ASC")
	List<ThuongHieu> findAllOrderByTenTHAsc();

	@Query("SELECT th FROM ThuongHieu th WHERE th.tenTH LIKE :prefix% ORDER BY th.tenTH ASC")
	List<ThuongHieu> findByTenTHStartingWith(@Param("prefix") String prefix);
	
	@Query(value = "SELECT th FROM ThuongHieu th ORDER BY FUNCTION('NEWID') ASC")
    List<ThuongHieu> findRandomThuongHieu(Pageable pageable);
}
