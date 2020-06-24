package com.javalec.boardEx.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.javalec.boardEx.vo.BoardVO;
import com.javalec.boardEx.vo.Criteria;
import com.javalec.boardEx.vo.SearchCriteria;

public interface BoardService {
	
	// 게시글 작성
	public void write(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception;
	
	// 게시글 목록 조회
	public List<BoardVO> list(SearchCriteria scri) throws Exception;
	
	// 게시글 총 갯수
	public int listCount(SearchCriteria scri) throws Exception;
	
	// 게시글 조회
	public BoardVO read(int bno) throws Exception;
	
	// 게시글 수정
	public void update(BoardVO boardVO, String[] files, String[] fileName, MultipartHttpServletRequest mpRequest) throws Exception; 
	
	// 게시글 삭제
	public void delete(int bno) throws Exception;
	
	// 첨부 파일 조회
	public List<Map<String, Object>> selectFileList(int bno) throws Exception;
	
	// 첨부 파일 다운로드
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception;
	
	// 첨부 파일 수정
	public void updateFile(Map<String, Object> map) throws Exception;
	
	
}
