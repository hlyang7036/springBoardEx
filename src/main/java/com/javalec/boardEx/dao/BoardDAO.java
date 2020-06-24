package com.javalec.boardEx.dao;

import java.util.List;
import java.util.Map;

import com.javalec.boardEx.vo.BoardVO;
import com.javalec.boardEx.vo.Criteria;
import com.javalec.boardEx.vo.SearchCriteria;

public interface BoardDAO {
	
	// 게시글 작성
	public void write(BoardVO boardVO) throws Exception;
	
	// 게시글 목록 조회
	public List<BoardVO> list(SearchCriteria scri) throws Exception;
	
	// 게시글 총 갯수
	public int listCount(SearchCriteria scri) throws Exception;
	
	// 게시글 조회
	public BoardVO read(int bno) throws Exception;
	
	// 게시글 수정
	public void update(BoardVO boardVO) throws Exception;
	
	// 게시글 삭제
	public void delete(int bno) throws Exception;
	
	// 첨부파일 업로드
	public void insertFile(Map<String, Object> map) throws Exception;
	
	// 첨부 파일 조회
	public List<Map<String, Object>> selectFileList(int bno) throws Exception;
	
	// 첨부 파일 다운로드
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception;
	
	// 첨부 파일 수정
	public void updateFile(Map<String, Object> map) throws Exception;
	
	// 게시판 조회수
	public void boardHit(int bno) throws Exception;
	
}
