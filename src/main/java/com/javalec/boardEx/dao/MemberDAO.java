package com.javalec.boardEx.dao;

import com.javalec.boardEx.vo.MemberVO;

public interface MemberDAO {
	
	// 회원 가입
	public void register(MemberVO memberVO) throws Exception;
	
	// 로그인
	public MemberVO login(MemberVO memberVO) throws Exception;
	
	// 회원 정보 수정
	public void updateMember(MemberVO memberVO) throws Exception;
	
	// 회원 탈퇴
	public void deleteMember(MemberVO memberVO) throws Exception;
	
	// 비밀번호 체크
	public int passChk(MemberVO memberVO) throws Exception;
	
	// 아이디 중복체크
	public int idChk(MemberVO memberVO) throws Exception;
	
	// 유저 이름 중복체크
	public int userNameChk(MemberVO memberVO) throws Exception;
	
}
