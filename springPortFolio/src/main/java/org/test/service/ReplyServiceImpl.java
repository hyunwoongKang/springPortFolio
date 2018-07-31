package org.test.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.domain.PageMaker;
import org.test.domain.ReplyVO;
import org.test.persistence.BoardDAO;
import org.test.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService{
	
	@Inject
	private ReplyDAO dao;
	
	@Inject
	private BoardDAO boardDao;

	//모든 댓글 가져오기
	@Override
	public List<ReplyVO> getAllReply(int bno,PageMaker pk) throws Exception {
		// TODO Auto-generated method stub
		return dao.getAllReply(bno,pk);
	}
	
	//모든 댓글 추가하기 
	//댓글 추가하면 해당 게시판 번호 댓글 수 추가
	@Transactional
	@Override
	public void addReply(ReplyVO reply) throws Exception {
		// TODO Auto-generated method stub
		
		dao.addReply(reply);
		int bno=reply.getBno();
		boardDao.addReplyCnt(bno);
	}
	
	//댓글 추가하면 해당 게시판 번호 댓글 수 감소
	@Transactional
	@Override
	public void removeReply(ReplyVO vo) throws Exception {
		// TODO Auto-generated method stub
		int rno=vo.getRno();
		int bno=vo.getBno();
		dao.removeReply(rno);
		boardDao.subReplyCnt(bno);
	}

	@Override
	public void modifyReply(ReplyVO reply) throws Exception {
		// TODO Auto-generated method stub
		dao.modifyReply(reply);
	}

	@Override
	public int getRnocount(int bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.getRnoCount(bno);
	}

}
