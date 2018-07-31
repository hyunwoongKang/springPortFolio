package org.test.persistence;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.test.domain.BoardTitleVO;
import org.test.domain.BoardVO;
import org.test.domain.LikeCheck;
import org.test.domain.PageMaker;

@Repository
public class BoardDAOImpl implements BoardDAO{

	@Inject
	private SqlSession session;
	
	private static String namespace="org.test.mapper.BoardMapper";
	
	@Override
	public List<BoardTitleVO> getTitle() throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".allTitle");
	}


	@Override
	public List<BoardVO> BoardList(PageMaker pk) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".boardList",pk);
	}


	@Override
	public BoardVO readBoard(int bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".read",bno);
	}


	@Override
	public int countBoard(PageMaker pk) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".countBoard",pk);
	}


	@Override
	public void deleteBoard(int bno) throws Exception {
		// TODO Auto-generated method stub
		session.delete(namespace+".deleteBoard",bno);
		
	}


	@Override
	public void addBoard(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		
		session.insert(namespace+".addBoard",vo);
		
	}


	@Override
	public int getBno() throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".getbno");
	}


	@Override
	public void addReplyCnt(int bno) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".addReplyCnt",bno);
	}


	@Override
	public void subReplyCnt(int bno) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".subtractReplyCnt",bno);
		
	}


	@Override
	public void modifyBaord(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".updateBoard",vo);
		
	}


	@Override
	public void increaseBoard(int bno) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".increaseBoard",bno);
		
	}


	@Override
	public List<BoardVO> getBoardBest() throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".boardBest");
	}


	@Override
	public List<BoardVO> searchBoard(String board_title) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".searchBoard",board_title);
	}


	@Override
	public void addLike(int bno) throws Exception {
		// TODO Auto-generated method stub
		 session.update(namespace+".incLike",bno);
		
	}

	@Override
	public void decLike(int bno) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".decLike",bno);
	}
	
	
	@Override
	public LikeCheck likeCheck(Map map) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".likeCheck",map);
	}


	@Override
	public void modifyLike(LikeCheck likeCheck) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".modifyLike",likeCheck);
	}


	@Override
	public void insertLikeCheck(Map map) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace+".insertLikeCheck",map);
		
	}


	@Override
	public BoardTitleVO getTitle1(String title) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("fd");
		return session.selectOne(namespace+".getTitle",title);
	}


	@Override
	public void incBoard_cnt(String title) throws Exception {
		// TODO Auto-generated method stub
	    session.update(namespace+".incBoardTitle",title);
	}


	@Override
	public void decBoard_cnt(String title) throws Exception {
		// TODO Auto-generated method stub
		 session.update(namespace+".decBoardTitle",title);
	}

	
}
