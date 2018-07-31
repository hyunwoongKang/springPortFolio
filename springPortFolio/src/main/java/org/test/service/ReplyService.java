package org.test.service;

import java.util.List;

import org.test.domain.PageMaker;
import org.test.domain.ReplyVO;

public interface ReplyService {
	
	//댓글 전부
	public List<ReplyVO> getAllReply(int bno,PageMaker pk)throws Exception;
	
	//댓글 추가
	public void addReply(ReplyVO reply)throws Exception;
	
	//댓글 수정
	public void modifyReply(ReplyVO reply)throws Exception;
	
	//댓글 삭제
	public void removeReply(ReplyVO vo)throws Exception;
	
	//해당 게시판 댓글수 가져오기
	public int getRnocount(int bno)throws Exception;

}
