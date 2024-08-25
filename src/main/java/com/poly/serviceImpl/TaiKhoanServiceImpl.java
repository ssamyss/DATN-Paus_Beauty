package com.poly.serviceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.dao.TaiKhoanDao;
import com.poly.entity.TaiKhoan;
import com.poly.service.TaiKhoanService;

import jakarta.transaction.Transactional;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {

	private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	private final JavaMailSender javaMailSender;

	@Autowired
	TaiKhoanDao dhdao;

	public TaiKhoanServiceImpl(JavaMailSender javaMailSender, TaiKhoanDao dhdao) {
		this.javaMailSender = javaMailSender;
		this.dhdao = dhdao;
	}

	@Override
	public List<TaiKhoan> findAll() {
		// TODO Auto-generated method stub
		return dhdao.findAll();
	}

	@Override
	public TaiKhoan findById(String tenTaiKhoan) {
		// TODO Auto-generated method stub
		return dhdao.findById(tenTaiKhoan).get();
	}

	@Override
	public List<TaiKhoan> findByTaiKhoanId(String ctenTaiKhoan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TaiKhoan create(TaiKhoan taiKhoan) {
		// TODO Auto-generated method stub
		return dhdao.save(taiKhoan);
	}

	@Override
	public TaiKhoan update(TaiKhoan taiKhoan) {
		// TODO Auto-generated method stub
		return dhdao.save(taiKhoan);
	}

	@Override
	public void delete(String tenTaiKhoan) {
		// TODO Auto-generated method stub
		dhdao.deleteById(tenTaiKhoan);
	}

	@Override
	@Transactional(rollbackOn = { Throwable.class })
	public TaiKhoan save(TaiKhoan taikhoanRequest, boolean role) throws SQLException {

		TaiKhoan TaiKhoanDao = taikhoanRequest;
		taikhoanRequest.setRole(role);
		taikhoanRequest.setMatKhau(bcrypt.encode(taikhoanRequest.getMatKhau()));

		return dhdao.saveAndFlush(TaiKhoanDao);
	}

	@Override
	public String generateAndSendPIN(String email) {
		String pin = generateRandomPIN();
		sendEmail(email, "Your PIN for password reset", "Your PIN is: " + pin);
		return pin;
	}

	private void sendEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
	}

	private String generateRandomPIN() {
		Random random = new Random();
		int pin = 1000 + random.nextInt(9000);
		return String.valueOf(pin);
	}
	
	@Override
	@Transactional(rollbackOn = { Throwable.class })
	public void checkTenTaiKhoan(TaiKhoan taikhoanRequest) throws SQLException {
		if (dhdao.existsById(taikhoanRequest.getTenTaiKhoan())) {
			throw new SQLException("Tên tài khoản đã tồn tại");
		}
	}

	@Override
	public String generateAndSendLinkResetPass(String email) {
		sendEmail(email, "Paus Beauty", "Nhấn vào link để đổi mật khẩu mới: http://localhost:8080/resetpassword/" + email);
		return email;
	}
	
	public boolean isEmailExists(String email) {
	    return dhdao.existsByEmail(email);
	}

	
	
	

}
