package com.javaex.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;

	// 회원가입-->회원정보저장
	public int insert(UserVo userVo) {
		System.out.println("user dao insert");
		System.out.println(userVo.toString());

		return sqlSession.insert("user.insert", userVo);
	}

	// 로그인 -->회원정보 가져오기
	public UserVo selectUser(UserVo userVo) {
		System.out.println("user dao selectUser");
		System.out.println(userVo.toString());

		return sqlSession.selectOne("user.selectUser", userVo);
	}

	// 회원정보 수정폼 --> 회원정보 가져오기
	public UserVo selectUser(int no) {
		System.out.println("user dao selectUser no");
		System.out.println(no);

		return sqlSession.selectOne("user.selectUserByNo", no);
	}

	// 회원정보 수정
	public int update(UserVo userVo) {
		System.out.println("user dao update");
		System.out.println(userVo.toString());

		return sqlSession.update("user.update", userVo);
	}
	
	//회원가임 id체크
	public UserVo selectOne(String id) {
		System.out.println("user dao selectOne " + id);
		return sqlSession.selectOne("user.selectById", id);
		
	}
	

}
