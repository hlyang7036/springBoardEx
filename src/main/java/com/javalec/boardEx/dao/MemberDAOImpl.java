package com.javalec.boardEx.dao;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.javalec.boardEx.vo.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	@Inject
	SqlSession sql;
	
	// 회원 가입
	@Override
	public void register(MemberVO memberVO) throws Exception {
		sql.insert("memberMapper.register", memberVO);
		
	}
	
	// 로그인
	@Override
	public MemberVO login(MemberVO memberVO) throws Exception {
		
		return sql.selectOne("memberMapper.login", memberVO);
	}
	
	// 회원 정보 수정
	@Override
	public void updateMember(MemberVO memberVO) throws Exception {
		
		// vo에 담긴 파라메터들을 memberMapper.xml에 memberMapper라는 nameSpace에
		// 아이디가 memberUpdate인 쿼리에 파라미터들을 넣어 준다
		sql.update("memberMapper.memberUpdate", memberVO);
	}
	
	// 회원 탈퇴
	@Override
	public void deleteMember(MemberVO memberVO) throws Exception {
		
		// MemberVO에 담긴 값들을 보내줍니다.
		// 그럼 xml에서 memberMapper.memberDelete에 보시면
		//  #{userId}, #{userPass}에 파라미터값이 매칭이 되겠지요.		
		sql.delete("memberMapper.memberDelete", memberVO);
	}

	// 비밀번호 체크
	@Override
	public int passChk(MemberVO memberVO) throws Exception {
		
		int result =  sql.selectOne("memberMapper.passChk", memberVO);
		return result;
	}
	
	// 아이디 중복 체크
	@Override
	public int idChk(MemberVO memberVO) throws Exception {
		
		int result = sql.selectOne("memberMapper.idChk", memberVO);
		return result;
	}
	
	// 유저 이름 중복체크
	@Override
	public int userNameChk(MemberVO memberVO) throws Exception {
		
		int result = sql.selectOne("memberMapper.userNameChk", memberVO);
		return result;
	}
	

}
