package org.test.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.domain.BoardTitleVO;
import org.test.domain.BoardVO;
import org.test.domain.LikeCheck;
import org.test.domain.PageMaker;
import org.test.persistence.BoardDAO;

@Service
public class BoardServceImpl implements BoardService{

	@Inject
	private BoardDAO dao;
	
	@Override
	public List<BoardTitleVO> getTitle() throws Exception {
		// TODO Auto-generated method stub
		return dao.getTitle();
	}

	@Override
	public List<BoardVO> boardList(PageMaker pk) throws Exception {
		// TODO Auto-generated method stub
		return dao.BoardList(pk);
	}

	@Transactional
	@Override
	public BoardVO readBoard(int bno) throws Exception {
		// TODO Auto-generated method stu
		//게시판 조회 시, 조회수 증가
		dao.increaseBoard(bno);
		
		return dao.readBoard(bno);
	}

	@Override
	public int countBaord(PageMaker pk) throws Exception {
		// TODO Auto-generated method stub
		return dao.countBoard(pk);
	}

	@Transactional
	@Override
	public void deleteBoard(int bno,String title) throws Exception {
		// TODO Auto-generated method stub
		  dao.decBoard_cnt(title);
	      dao.deleteBoard(bno);
		
	}

	@Transactional
	@Override
	public void addBoard(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		//타이틀 게시판 수 증가
		dao.incBoard_cnt(vo.getTitle());
		dao.addBoard(vo);
		
	}

	@Override
	public int getBno() throws Exception {
		// TODO Auto-generated method stub
		return dao.getBno();
	}

	@Override
	public void modifyBoard(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		dao.modifyBaord(vo);
		
	}

	@Override
	public List<BoardVO> getboardBest() throws Exception {
		// TODO Auto-generated method stub
		return dao.getBoardBest();
	}

	@Override
	public List<BoardVO> searchBoard(String board_title) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchBoard(board_title);
	}

	@Transactional
	@Override
	public void addLike(LikeCheck likeCheck) throws Exception {
		// TODO Auto-generated method stub
		int bno=likeCheck.getBno();
		likeCheck.setLikeCheck('Y');
		dao.addLike(bno);
		dao.modifyLike(likeCheck);
	}
	
	@Transactional
	@Override
	public void decLike(LikeCheck likeCheck) throws Exception {
		// TODO Auto-generated method stub
		int bno=likeCheck.getBno();
		likeCheck.setLikeCheck('N');
		dao.decLike(bno);
		dao.modifyLike(likeCheck);
		
	}

	@Override
	public LikeCheck likeCheck(Map map) throws Exception {
		// TODO Auto-generated method stub
		return dao.likeCheck(map);
	}

	@Override
	public void insertLikeCheck(Map map) throws Exception {
		// TODO Auto-generated method stub
		 dao.insertLikeCheck(map);
	}

	@Override
	public BoardTitleVO getTitle1(String title) throws Exception {
		// TODO Auto-generated method stub
		return dao.getTitle1(title);
	}

	
	
	





}
