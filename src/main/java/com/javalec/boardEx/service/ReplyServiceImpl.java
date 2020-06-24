package com.javalec.boardEx.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.javalec.boardEx.dao.ReplyDAO;
import com.javalec.boardEx.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Inject
	private ReplyDAO dao;
	
	// 댓글 목록
	@Override
	public List<ReplyVO> readReply(int bno) throws Exception {
		return dao.readReply(bno);
	}
	
	// 댓글 등록
	@Override
	public void writeReply(ReplyVO replyVO) throws Exception {
		dao.writeReply(replyVO);
	}
	
	// 댓글 수정
	@Override
	public void updateReply(ReplyVO replyVO) throws Exception {
		dao.updateReply(replyVO);
		
	}
	
	// 댓글 삭제
	@Override
	public void deleteReply(ReplyVO replyVO) throws Exception {
		dao.deleteReply(replyVO);
	}
	
	// 선택된 댓글 조회
	@Override
	public ReplyVO selectReply(int rno) throws Exception {
		
		return dao.selectReply(rno);
	}
	
}
