package com.javaex.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private SqlSession sqlSession;

	// 글전체 가져오기(리스트만 출력할때)
	public List<BoardVo> selectList() {
		System.out.println("[BoardDao.selectList()]");

		return sqlSession.selectList("board.selectList");
	}

	// 글전체 가져오기(키워드)
	public List<BoardVo> selectList2(String keyword) {
		System.out.println("[BoardDao.selectList2()]");

		return sqlSession.selectList("board.selectList2", keyword);
	}

	// 글전체 가져오기(키워드+페이징)
	public List<BoardVo> selectList3(String keyword, int startRnum, int endRnum) {
		System.out.println("[BoardDao.selectList3()]");
		
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("keyword", keyword);
		map.put("startRnum", startRnum);
		map.put("endRnum", endRnum);
		System.out.println(map);

		return sqlSession.selectList("board.selectList3", map);
	}
	
	
	//전제글 갯수 가져오기
	public int selectTotalCnt(String keyword) {
		System.out.println("[BoardDao.selectTotalCnt()]");
		
		return sqlSession.selectOne("board.selectTotalCnt", keyword);
		
	}
	

	// 글저장
	public int insert(BoardVo boardVo) {
		System.out.println("[BoardDao.insert()]");

		return sqlSession.insert("board.insert", boardVo);
	}

	// 글 1개 가져오기
	public BoardVo select(int no) {
		System.out.println("[BoardDao.select()]");

		return sqlSession.selectOne("board.selcet", no);
	}

	// 조회수 업데이트
	public int updateHit(int no) {
		System.out.println("[BoardDao.updateHit()]");

		return sqlSession.update("board.updateHit", no);
	}

	// 글수정
	public int update(BoardVo boardVo) {
		System.out.println("[BoardDao.update()]");

		return sqlSession.update("board.update", boardVo);
	}

	// 글삭제
	public int delete(BoardVo boardVo) {
		System.out.println("[BoardDao.delete()]");

		return sqlSession.delete("board.delete", boardVo);
	}
	
	
	

}
