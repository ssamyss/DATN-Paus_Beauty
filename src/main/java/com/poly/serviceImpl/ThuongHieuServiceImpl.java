package com.poly.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.poly.dao.ThuongHieuDao;
import com.poly.entity.ThuongHieu;
import com.poly.service.ThuongHieuService;

@Service
public class ThuongHieuServiceImpl implements ThuongHieuService {
	
	@Autowired
	ThuongHieuDao thdao;

	@Override
	public List<ThuongHieu> findAll() {
		// TODO Auto-generated method stub
		return thdao.findAll();
	}

	@Override
	public ThuongHieu findById(Integer maTH) {
		// TODO Auto-generated method stub
		return thdao.findById(maTH).get();
	}

	@Override
	public ThuongHieu create(ThuongHieu thuonghieu) {
		// TODO Auto-generated method stub
		return thdao.save(thuonghieu);
	}

	@Override
	public ThuongHieu update(ThuongHieu thuonghieu) {
		// TODO Auto-generated method stub
		return thdao.save(thuonghieu);
	}

	@Override
	public void delete(Integer maTH) {
		// TODO Auto-generated method stub
		thdao.deleteById(maTH);
	}
	
	@Override
    public List<ThuongHieu> findRandomThuongHieu(Pageable pageable) {
        return thdao.findRandomThuongHieu(pageable);
    }

}
