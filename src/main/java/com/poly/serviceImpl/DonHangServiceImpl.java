package com.poly.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.dao.DonHangDao;
import com.poly.entity.DonHang;
import com.poly.service.DonHangService;

@Service
public class DonHangServiceImpl implements DonHangService {
	
	@Autowired
	DonHangDao dhdao;

	@Override
	public List<DonHang> findAll() {
		// TODO Auto-generated method stub
		return dhdao.findAll();
	}

	@Override
	public DonHang findById(String maDH) {
		// TODO Auto-generated method stub
		return dhdao.findById(maDH).get();
	}

	@Override
	public List<DonHang> findByDonHangId(String cmaDH) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DonHang create(DonHang donHang) {
		// TODO Auto-generated method stub
		return dhdao.save(donHang);
	}

	@Override
	public DonHang update(DonHang donHang) {
		// TODO Auto-generated method stub
		return dhdao.save(donHang);
	}

	@Override
	public void delete(String maDH) {
		// TODO Auto-generated method stub
		dhdao.deleteById(maDH);
	}

	@Override
    public List<Object[]> getMonthlyRevenue() {
        return dhdao.getMonthlyRevenue();
    }
    
    @Override
    public List<Object[]> getQuarterlyRevenue() {
        return dhdao.getQuarterlyRevenue();
    }

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		dhdao.deleteAll();
	}
	
	@Override
	public List<DonHang> getOrders(String tentaikhoan) {
		// TODO Auto-generated method stub
		return dhdao.getOrders(tentaikhoan);
	}
	
	@Override
	public List<DonHang> findAllByTrangThaiDangXuLy() {
	    return dhdao.findAllByTrangThaiDangXuLy();
	}

	@Override
	public List<DonHang> findAllByTrangThaiHoanThanh() {
		return dhdao.findAllByTrangThaiHoanThanh();
	}

	@Override
	public List<DonHang> findAllByTrangThaiHuyDon() {
		return dhdao.findAllByTrangThaiHuyDon();
	}

	@Override
	public List<DonHang> findAllByTrangThaiChoLayHang() {
		// TODO Auto-generated method stub
		return dhdao.findAllByTrangThaiChoLayHang();
	}

	@Override
	public List<DonHang> findAllByTrangThaiDangGiaoHang() {
		// TODO Auto-generated method stub
		return dhdao.findAllByTrangThaiDangGiaoHang();
	}


}
