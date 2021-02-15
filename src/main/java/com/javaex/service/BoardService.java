package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.BoardDao;
import com.javaex.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;

	// 리스트(리스트만 출력할때)
	public List<BoardVo> getBoardList() {
		System.out.println("[BoardService.getBoardList()]");

		return boardDao.selectList();
	}

	// 리스트(리스트+검색)
	public List<BoardVo> getBoardList2(String keyword) {
		System.out.println("[BoardService.getBoardList2()]");

		List<BoardVo> boardList = boardDao.selectList2(keyword);

		return boardList;
	}

	// 리스트(리스트+검색+페이징)
	public Map<String, Object> getBoardList3(String keyword, int crtPage) {
		System.out.println("[BoardService.getBoardList3()]");

		//////////////////////////////////
		//리스트 구하기
		//////////////////////////////////
		
		//페이지당 글갯수
		int listCnt = 10;

		//현재 페이지 crtPage;
		crtPage = (crtPage > 0) ? crtPage : (crtPage = 1);
		
		//시작글 번호 startRnum:  1page-->1, 2page-->11, 3page-->3  
		int startRnum = (crtPage-1) * listCnt + 1;  
		
		//끝글 번호 endRnum: 1page-->(1,10), 2page-->(11,20), 3page-->(21,30) 
		int endRnum = (startRnum + listCnt) - 1;
		
	    List<BoardVo> boardList = boardDao.selectList3(keyword, startRnum, endRnum);

	    
	    //////////////////////////////////
	    //페이징 계산
	    /////////////////////////////////
	    
	    //페이지당 버튼 갯수
	    int pageBtnCount = 5;
	    
	    //전체 글갯수 구하기
	    int totalCount = boardDao.selectTotalCnt(keyword);
	    
	    //1 --> 1 ~ 5  1/5.0 -->0.2-->1.0-->1 * 5  ---> 5
	    //2 --> 1 ~ 5
	    //3 --> 1 ~ 5
	    //4 --> 1 ~ 5
	    //5 --> 1 ~ 5
	    //6 --> 6 ~ 10
	    //7 --> 6 ~ 10
	    //마지막 버튼 번호
	    int endPageBtnNo =  (int)Math.ceil(crtPage/(double)pageBtnCount) * pageBtnCount;
	                                       
	    //시작 버튼 번호
	    int startPageBtnNo = endPageBtnNo - (pageBtnCount - 1);
	    
	    //다음버튼 boolean
	    boolean next ;
	    if(endPageBtnNo * listCnt < totalCount) { // 5 * 10  < 51
	    	next = true;
	    }else {                                   // 5 * 19  < 35
	    	next = false;
	    	endPageBtnNo = (int)Math.ceil(totalCount/(double)listCnt);	   
	    }
	    
	    //이전버튼 boolean
	    boolean prev ;
	    if(startPageBtnNo != 1) {
	    	prev = true;
	    }else {
	    	prev = false;
	    }
	    
	    //boardList, prev, startPageBtnNo, endPageBtnNo, next   --> jsp map 전달
	    Map<String, Object> pMap = new HashMap<String, Object>();
	    pMap.put("boardList", boardList);
	    pMap.put("prev", prev);
	    pMap.put("startPageBtnNo", startPageBtnNo);
	    pMap.put("endPageBtnNo", endPageBtnNo);
	    pMap.put("next", next);
	    
		return pMap;
	}

	// 글쓰기
	public int addBoard(BoardVo boardVo) {
		System.out.println("[BoardService.addBoard()]");
		
		return boardDao.insert(boardVo);
	}

	// 글가져오기
	public BoardVo getBoard(int no, String type) {
		System.out.println("[BoardService.getBoard()]");

		if ("read".equals(type)) {// 읽기 일때는 조회수 올림
			boardDao.updateHit(no);
			BoardVo boardVo = boardDao.select(no);
			return boardVo;

		} else { // 수정 일때는 조회수 올리지 않음
			BoardVo boardVo = boardDao.select(no);
			return boardVo;
		}

	}

	// 글수정
	public int modifyBoard(BoardVo boardVo) {
		System.out.println("[BoardService.modifyBoard()]");

		return boardDao.update(boardVo);
	}

	// 글삭제
	public int removeBoard(BoardVo boardVo) {
		System.out.println("[BoardService.removeBoard()]");

		return boardDao.delete(boardVo);
	}

}
