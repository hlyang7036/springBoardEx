package com.javalec.boardEx.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.javalec.boardEx.dao.BoardDAO;
import com.javalec.boardEx.util.FileUtils;
import com.javalec.boardEx.vo.BoardVO;
import com.javalec.boardEx.vo.SearchCriteria;

@Service
public class BoardServiceImpl implements BoardService {

	@Inject
	private BoardDAO dao;
	
	@Resource(name = "fileUtils")
	private FileUtils fileUtils;
	
	// 게시글 작성
	@Override
	public void write(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception {
		dao.write(boardVO);
		
		List<Map<String, Object>> list = fileUtils.parseInsertFileInfo(boardVO, mpRequest);
		int size = list.size();
		for (int i = 0; i < size; i++) {
			System.out.println("list.get(i).toString() : "+ list.get(i).toString());
			dao.insertFile(list.get(i));	// 리스트별로 파일을 저장하는 로직을 비지니스에서 처리
		}
		
	}
	
	// 게시글 목록 조회
	@Override
	public List<BoardVO> list(SearchCriteria scri) throws Exception {
		
		return dao.list(scri);
	}
	
	// 게시글 총 갯수
	@Override
	public int listCount(SearchCriteria scri) throws Exception {
		
		return dao.listCount(scri);
	}
	
	// 게시글 상세 보기
	/*
	 * 게시물의 조회수는 게시물테이블을 update하는과정에 있기 때문에 트랜잭션을 통해서 먼저 update를 완료해야한다. 그렇지 않으면
	 * 게시물의 수정 삭제 과정에서 hit컬럼의 업데이트가 완료되지 않은 상태가 되고, 커밋이 완료되지 않은 상태에서는 다른 데이터의 수정 삭제가
	 * 제한된다.
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED)
	@Override
	public BoardVO read(int bno) throws Exception {
		dao.boardHit(bno);
		return dao.read(bno);
	}
	
	// 게시글 수정
	@Override
	public void update(BoardVO boardVO, String[] files, String[] fileName, MultipartHttpServletRequest mpRequest) throws Exception {
		
		dao.update(boardVO);
		
		List<Map<String, Object>> list = fileUtils.parseUpdateFileInfo(boardVO, files, fileName, mpRequest);
		Map<String, Object> tempMap = null;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			tempMap = list.get(i);
			if (tempMap.get("IS_NEW").equals("Y")) {
				dao.insertFile(tempMap);
			} else {
				dao.updateFile(tempMap);
			}
		}
		
		
	}
	
	// 게시글 삭제
	@Override
	public void delete(int bno) throws Exception {
		dao.delete(bno);
		
	}
	
	// 첨부 파일 조회
	@Override
	public List<Map<String, Object>> selectFileList(int bno) throws Exception {
		
		return dao.selectFileList(bno);
	}
	
	// 첨부 파일 다운로드
	@Override
	public Map<String, Object> selectFileInfo(Map<String, Object> map) throws Exception {
		
		return dao.selectFileInfo(map);
	}
	
	// 첨부 파일 수정
	@Override
	public void updateFile(Map<String, Object> map) throws Exception {
		
		dao.updateFile(map);
	}
	

}
