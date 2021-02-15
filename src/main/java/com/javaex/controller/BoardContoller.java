package com.javaex.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaex.service.BoardService;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardContoller {

	@Autowired
	private BoardService boardService;

	// 리스트(리스트만 출력할때)
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Model model) {
		System.out.println("[BoardContoller.list()]");

		List<BoardVo> boardList = boardService.getBoardList();
		model.addAttribute("boardList", boardList);
		return "board/list";
	}
	
	
	// 리스트(리스트+검색)
	@RequestMapping(value = "/list2", method = { RequestMethod.GET, RequestMethod.POST})
	public String list2(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, Model model) {
		System.out.println("[BoardContoller.list2()]");
		
		List<BoardVo> boardList = boardService.getBoardList2(keyword);
		model.addAttribute("boardList", boardList);
		
		return "board/list2";
	}
	
	
	// 리스트(리스트+검색+페이징)
	@RequestMapping(value = "/list3", method = { RequestMethod.GET, RequestMethod.POST})
	public String list3(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword, 
						@RequestParam(value = "crtPage", required = false, defaultValue = "1" ) int crtPage,
						Model model) {
		System.out.println("[BoardContoller.list3()]");
		//System.out.println("keyword=" + keyword);
		//System.out.println("crtPage=" + crtPage);
		
		Map<String, Object> pMap= boardService.getBoardList3(keyword, crtPage);
		System.out.println(pMap);
		
		model.addAttribute("pMap", pMap);
		
		return "board/list3";
	}
		
	

	// 게시판 글쓰기 폼
	@RequestMapping(value = "/writeForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String writeForm() {
		System.out.println("[BoardContoller.writeForm()]");

		return "board/writeForm";
	}

	
	// 게시판 글쓰기
	@RequestMapping(value = "/write", method = { RequestMethod.GET, RequestMethod.POST })
	public String write(@ModelAttribute BoardVo boardVo, HttpSession session) {
		System.out.println("[BoardContoller.write()]");

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		boardVo.setUserNo(authUser.getNo());
		boardService.addBoard(boardVo);

		return "redirect:/board/list";
	}
	
	// 게시판 글읽기
	@RequestMapping(value = "/read", method = { RequestMethod.GET, RequestMethod.POST })
	public String read(@RequestParam("no") int no, Model model) {
		System.out.println("[BoardContoller.read()]");

		BoardVo boardVo = boardService.getBoard(no, "read");
		model.addAttribute("boardVo", boardVo);

		return "board/read";
	}

	// 게시판 글수정 폼
	@RequestMapping(value = "/modifyForm", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyform(@RequestParam int no, Model model) {
		System.out.println("[BoardContoller.modifyform()]");

		BoardVo boardVo = boardService.getBoard(no, "modify");
		model.addAttribute("boardVo", boardVo);
		return "board/modifyForm";
	}

	// 게시판 글수정
	@RequestMapping(value = "/modify", method = { RequestMethod.GET, RequestMethod.POST })
	public String modify(@ModelAttribute BoardVo boardVo, HttpSession session, Model model) {
		System.out.println("[BoardContoller.modify()]");

		// 로그인한 사용자의 글만 수정하도록 세션의 userNo도 입력(쿼리문에서 검사)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		boardVo.setUserNo(authUser.getNo());
		boardService.modifyBoard(boardVo);

		return "redirect:/board/list";
	}

	// 게시판 글삭제
	@RequestMapping(value = "/remove", method = { RequestMethod.GET, RequestMethod.POST })
	public String remove(@ModelAttribute BoardVo boardVo, HttpSession session) {
		System.out.println("[BoardContoller.remove()]");

		// 로그인한 사용자의 글만 삭제하도록 세션의 userNo도 입력(쿼리문에서 검사)
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		boardVo.setUserNo(authUser.getNo());
		boardService.removeBoard(boardVo);

		return "redirect:/board/list";
	}


}
