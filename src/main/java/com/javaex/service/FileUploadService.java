package com.javaex.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	
	public void restore(MultipartFile file) {
		System.out.println("[FileUploadService.restore()]");
		System.out.println(file.getOriginalFilename());
		
		//db저장할 정보 수집///////////////////////////////////////////
		String saveDir = "C:\\javaStudy\\upload";
		
		//오리지널 파일이름
		String orgName = file.getOriginalFilename();
		System.out.println("orgName:" + orgName);
		
		//확장자
		String exName = orgName.substring(orgName.lastIndexOf("."));
		System.out.println("exName:" + exName);
		
		//서버 저장파일 이름
		String saveName = System.currentTimeMillis()+UUID.randomUUID().toString() + exName;
		System.out.println("saveName:" + saveName);
		
		//서버 파일패스 -->저장경로
		String filePath = saveDir + "\\" + saveName;
		System.out.println("filePath:" + filePath);
		
		//파일사이즈
		long fileSize = file.getSize();
		System.out.println("fileSize:" + fileSize);
		
		
		//서버 하드디스크 저장
		
	}
	
	
}
