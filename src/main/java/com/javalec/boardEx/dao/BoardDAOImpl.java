package com.javalec.boardEx.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.javalec.boardEx.vo.BoardVO;
import com.javalec.boardEx.vo.Criteria;
import com.javalec.boardEx.vo.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	private SqlSession sqlsession;
	
	// 게시글 등록
	@Override
	public void write(BoardVO boardVO) throws Exception {
		sqlsession.insert("boardMapper.insert", boardVO);
		
	}
	
	// 게시글 목록 조회
	@Override
	public List<BoardVO> list(SearchCriteria scri) throws Exception {
		return sqlsession.selectList("boardMapper.listPage2", scri);
	}
	
	// 게시글 총 갯수
	@Override
	public int listCount(SearchCriteria scri) throws Exception {
		// TODO Auto-generated method stub
		return sqlsession.selectOne("boardMapper.listCount", scri);
	}
	
	// 게시글 상세 보기
	@Override
	public BoardVO read(int bno) throws Exception {
		
		return sqlsession.selectOne("boardMapper.read", bno);
	}

	// 게시글 수정
	@Override
	public void update(BoardVO boardVO) throws Exception {
		sqlsession.update("boardMapper.update", boardVO);
		
	}
	
	// 게시글 삭제
	@Override
	public void delete(int bno) throws Exception {
		sqlsession.delete("boardMapper.delete", bno);
		
	}
	
	// 첨부 파일 업로드
	@Override
	public void insertFile(Map<String, Object> map) throws Exception {
		
		sqlsession.insert("boardMapper.insertFile", map);
		
	}
	
	// 첨부 파일 조회
	@Override
	public List<Map<String, Object>> selectFileList(int bno) throws Exception {
		
		return sqlsession.selectList("boardMapper.selectFileList", bno);
	}
	
	// 첨부 파일 다운로드
	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		
		
		return sqlsession.selectOne("boardMapper.selectFileInfo", map);
	}
	
	// 첨부 파일 수정
	@Override
	public void updateFile(Map<String, Object> map) throws Exception {
		sqlsession.update("boardMapper.updateFile", map);
		
	}
	
	// 게시판 조회수
	@Override
	public void boardHit(int bno) throws Exception {
		sqlsession.update("boardMapper.boardHit", bno);
	}

}
