package com.poly.serviceImpl;

import java.util.Comparator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.dao.SanPhamDao;
import com.poly.entity.SanPham;
import com.poly.service.SanPhamService;

@Service
public class SanPhamServiceImpl implements SanPhamService {
	
	@Autowired
	SanPhamDao spdao;

	@Override
	public List<SanPham> findAll() {
		// TODO Auto-generated method stub
		return spdao.findAll();
	}

	@Override
	public SanPham findById(int maSP) {
		// TODO Auto-generated method stub
		return spdao.findById(maSP).get();
	}

	@Override
	public List<SanPham> findByCategoryId(int cmaSP) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SanPham create(SanPham sanpham) {
		// TODO Auto-generated method stub
		return spdao.save(sanpham);
	}

	@Override
	public SanPham update(SanPham sanpham) {
		// TODO Auto-generated method stub
		return spdao.save(sanpham);
	}

	@Override
	public void delete(int maSP) {
		// TODO Auto-generated method stub
		spdao.deleteById(maSP);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		spdao.deleteAll();
	}

	@Override
    public List<SanPham> getTop5SanPhamBanChay() {
        List<Object[]> results = spdao.findTop5SanPhamBanChay(PageRequest.of(0, 5));
        return results.stream()
                      .map(result -> (SanPham) result[0])
                      .collect(Collectors.toList());
    }

	@Override
	public List<SanPham> findHetHang() {
		// TODO Auto-generated method stub
		return spdao.findByTonKho(0);
	}
	
	@Override
	public List<Object[]> getTotalProductsByDanhMuc() {
	    return spdao.getTotalProductsByDanhMuc();
	}
	
	@Override
    public List<SanPham> findRandomByCategory(String categoryName, Pageable pageable) {
        return spdao.findRandomByCategory(categoryName, pageable);
    }
	
	@Override
	public Page<SanPham> findByPriceLessThan(Long price, Pageable pageable){
		return spdao.findByPriceLessThan(price, pageable);
	}
	@Override
	public Page<SanPham> findByPriceLargeThan(Long price, Pageable pageable){
		return spdao.findByPriceLargeThan(price, pageable);
	}
	@Override
	public Page<SanPham> findByPriceBetween(String price, Pageable pageable){
		return spdao.findByPriceBetween(price, pageable);
	}

	@Override
    public Page<SanPham> getProductsSortedByPrice(String sortOrder, Pageable pageable) {
        Page<SanPham> sanphamPage;

        if ("desc".equals(sortOrder)) {
            sanphamPage = spdao.getProductsSortedByPrice(sortOrder, pageable);
        } else {
            // If sortOrder is not "desc" or empty, return all products (can be modified to handle "asc" sorting)
            sanphamPage = spdao.findAll(pageable);
        }

        return sanphamPage;
    }

	@Override
	public List<SanPham> findSapHetHang() {
		// TODO Auto-generated method stub
		return spdao.findBySapHetHang(10);
	}

	
	
}
