package com.poly.serviceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.poly.service.UploadService;

import jakarta.servlet.ServletContext;

@Service
public class UploadServiceImpl implements UploadService {

	 private static final String UPLOAD_DIR = "assets/images/";

	    @Autowired
	    private ResourceLoader resourceLoader;

	    public File save(MultipartFile file, String folder) {
	        try {
	            // Đường dẫn thư mục tĩnh
	            String staticPath = resourceLoader.getResource("classpath:/static/").getFile().getAbsolutePath();

	            // Tạo đường dẫn đến thư mục lưu trữ ảnh
	            Path uploadPath = Paths.get(staticPath, UPLOAD_DIR, folder);
	            Files.createDirectories(uploadPath);

	            // Tạo tên tệp duy nhất
	            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
	            String fileName = System.currentTimeMillis() + "-" + originalFileName;

	            // Lưu file
	            Path filePath = uploadPath.resolve(fileName).normalize();
	            Files.copy(file.getInputStream(), filePath);

	            // Trả về tệp đã lưu
	            File saveFile = filePath.toFile();
	            System.out.println("Tệp được lưu tại: " + saveFile.getAbsolutePath());
	            return saveFile;
	        } catch (IOException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Lỗi khi lưu tệp: " + e.getMessage());
	        }
	    }
}
