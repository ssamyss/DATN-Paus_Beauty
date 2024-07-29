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

	// Set the upload directory relative to your project or an absolute path
	private static final String UPLOAD_DIR = "src/main/resources/static/assets/images/";

	@Override
	public File save(MultipartFile file, String folder) {
		try {
			// Construct the path to the directory
			Path uploadPath = Paths.get(UPLOAD_DIR, folder);

			// Create directories if they do not exist
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			// Clean and create a unique filename
			String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
			String fileName = System.currentTimeMillis() + "-" + originalFileName;

			// Construct the file path and save the file
			Path filePath = uploadPath.resolve(fileName).normalize();
			Files.copy(file.getInputStream(), filePath);

			// Return the saved file
			File saveFile = filePath.toFile();
			System.out.println("File saved at: " + saveFile.getAbsolutePath());
			return saveFile;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error saving file: " + e.getMessage());
		}
	}
}
