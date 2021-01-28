package com.javaex.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javaex.dao.UserDao;
import com.javaex.service.UserService;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	// *필드
	@Autowired
	private UserService userService;

	// *생성자

	// *g.s 생략

	// 회원가입 폼
	@RequestMapping(value = "/joinForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String joinForm() {
		System.out.println("/user/joinForm");

		return "user/joinForm";
	}

	// 회원가입
	@RequestMapping(value = "/join", method = { RequestMethod.GET, RequestMethod.POST })
	public String join(@ModelAttribute UserVo userVo) {
		System.out.println("/user/join");
		System.out.println(userVo.toString());

		int count = userService.join(userVo);

		return "user/joinOk";
	}

	// 로그인 폼
	@RequestMapping(value = "/loginForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String loginForm() {
		System.out.println("/user/loginForm");

		return "user/loginForm";
	}

	// 로그인
	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public String login(@ModelAttribute UserVo userVo, HttpSession session) {
		System.out.println("/user/login");
		System.out.println(userVo.toString());

		UserVo authUser = userService.login(userVo);
		
		if (authUser != null) { // 성공했을때
			session.setAttribute("authUser", authUser);
			return "redirect:/";
		} else { //실패했을때
			return "redirect:/user/loginForm?result=fail";
		}
	}

	// 로그아웃
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public String logout(HttpSession session) {
		System.out.println("/user/logout");

		session.removeAttribute("authUser");
		session.invalidate();
		return "redirect:/";

	}
	
	
	// 회원정보 수정 폼
	@RequestMapping(value = "/modifyForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyForm(HttpSession session, Model model) {
		System.out.println("/user/modifyForm");

		// 세션에서 no값 가져오기
		int no = ((UserVo) session.getAttribute("authUser")).getNo();
		
		// 회원정보 가져오기
		UserVo userVo = userService.modifyForm(no);
		
		// jsp에 데이터 보내기
		model.addAttribute("userVo", userVo);

		return "user/modifyForm";
	}

	// 회원정보 수정
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String modify(@ModelAttribute UserVo userVo, HttpSession session) {
		System.out.println("/user/modify");

		// 세션에 사용자 정보 가져오기
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		// 세션에서 no값 가져오기
		int no = authUser.getNo();

		// vo에 no정보 추가
		userVo.setNo(no);

		// 회원정보 수정
		int count = userService.modify(userVo);

		// 세션에 이름변경
		authUser.setName(userVo.getName());

		return "redirect:/";
	}
	

}