package com.javalec.boardEx.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.javalec.boardEx.dao.MemberDAO;
import com.javalec.boardEx.vo.MemberVO;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Inject
	MemberDAO dao;
		
	// 회원 가입
	@Override
	public void register(MemberVO memberVO) throws Exception {
		dao.register(memberVO);
		
	}
	
	// 로그인
	@Override
	public MemberVO login(MemberVO memberVO) throws Exception {
		
		return dao.login(memberVO);
	}
	
	// 회원 정보 수정
	@Override
	public void memberUpdate(MemberVO memberVO) throws Exception {
		
		dao.updateMember(memberVO);
	}
	
	// 회원 탈퇴
	@Override
	public void memberDelete(MemberVO memberVO) throws Exception {
		
		dao.deleteMember(memberVO);
		
	}
	
	//  비밀번호 체크
	@Override
	public int passChk(MemberVO memberVO) throws Exception {
		
		int result = dao.passChk(memberVO);
		
		return result;
	}
	
	// 아이디 중복 체크
	@Override
	public int idChk(MemberVO memberVO) throws Exception {
		
		int result = dao.idChk(memberVO);
		return result;
	}
	
	// 유저 이름 중복체크
	@Override
	public int userNameChk(MemberVO memberVO) throws Exception {
		
		int result = dao.userNameChk(memberVO);
		return result;
	}

}
