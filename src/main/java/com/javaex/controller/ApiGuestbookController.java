package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javaex.service.GuestbookService;
import com.javaex.vo.GuestbookVo;

@Controller
@RequestMapping(value = "/api/guestbook" )
public class ApiGuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	//글작성(ajax)
	@RequestMapping(value = "/write")
	public String write(@ModelAttribute GuestbookVo guestbookVo) {
		System.out.println("[ApiGuestbookController] /write");
		System.out.println(guestbookVo);
		
		//입력된vo전달하고 저장vo를 받아야함
		guestbookService.writeResultVo(guestbookVo);
		
		return "";
	}
	
	
}
