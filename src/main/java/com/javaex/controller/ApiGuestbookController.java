package com.javaex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.javaex.service.GuestbookService;
import com.javaex.vo.GuestbookVo;

@Controller
@RequestMapping(value = "/api/guestbook" )
public class ApiGuestbookController {

	@Autowired
	private GuestbookService guestbookService;

	//전체리스트 가졍오기(ajax)
	@ResponseBody
	@RequestMapping(value = "/list")
	public List<GuestbookVo> list() {
		System.out.println("[ApiGuestbookController] /list");
		
		return guestbookService.getList();
	}
	
	
	//글작성(ajax)
	@ResponseBody
	@RequestMapping(value = "/write")
	public GuestbookVo write(@ModelAttribute GuestbookVo guestbookVo) {
		System.out.println("[ApiGuestbookController] /write");
		System.out.println(guestbookVo);
		
		//입력된vo전달하고 저장vo를 받아야함
		return guestbookService.writeResultVo(guestbookVo);
	}
	
	
	
	
}
