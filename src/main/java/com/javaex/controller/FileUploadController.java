package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.service.FileUploadService;

@Controller
@RequestMapping("/fileUp")
public class FileUploadController {

	@Autowired
	private FileUploadService fileUploadService;
	
	//파일 등록 폼
	@RequestMapping(value = "/form", method = {RequestMethod.GET, RequestMethod.POST})
	public String form() {
		System.out.println("[FileUploadController.form()]");
		
		return "/fileUpload/form";
	}
	
	
	//파일 업로드
	@RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
	public String upload(@RequestParam("file") MultipartFile file) {
		System.out.println("[FileUploadController.upload()]");
		
		fileUploadService.restore(file);
		
		return "";
	}
	
	
}
