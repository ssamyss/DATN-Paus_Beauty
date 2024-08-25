package com.poly.Interceptor;




import java.net.URLEncoder;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import com.poly.entity.TaiKhoan;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class AuthInterceptor implements HandlerInterceptor {
	@Autowired
	HttpSession session;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		TaiKhoan user = (TaiKhoan) session.getAttribute("user"); // lấy từ session
		
		
		String error = "";
		if (!(user != null)) { // chưa đăng nhập
			error = "Vui lòng đăng nhập để truy cập !";
			error = URLEncoder.encode(error, "UTF-8");
			
		}
		
		
		// không đúng vai trò
		else if (!user.isRole() && uri.startsWith("/loginAdmin")) {
			error = "Xin lỗi, bạn không có quyền truy cập !";
			error = URLEncoder.encode(error, "UTF-8");
		}
		if (error.length() > 0) { // có lỗi
			session.setAttribute("security-uri", uri);
			response.sendRedirect("/loginAdmin?error=" + error);
			return false;
		}
		return true;
	}
}
