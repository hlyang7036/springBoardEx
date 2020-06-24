package com.javalec.boardEx.service;

import com.javalec.boardEx.vo.MemberVO;

public interface MemberService {
	
	// 회원가입
	public void register(MemberVO memberVO) throws Exception;
	
	// 로그인
	public MemberVO login(MemberVO memberVO) throws Exception;
	
	// 회원 정보 수정
	public void memberUpdate(MemberVO memberVO) throws Exception;
	
	// 회원 탈퇴
	public void memberDelete(MemberVO memberVO) throws Exception;
	
	// 비민번호 체크
	public int passChk(MemberVO memberVO) throws Exception;
	
	// 아이디 중복 체크
	public int idChk(MemberVO memberVO) throws Exception;
	
	// 유저 이름 중복 체크
	public int userNameChk(MemberVO memberVO) throws Exception;
}
