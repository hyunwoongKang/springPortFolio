package org.test.service;

import java.util.List;
import java.util.Map;

import org.test.domain.BoardTitleVO;
import org.test.domain.BoardVO;
import org.test.domain.LikeCheck;
import org.test.domain.PageMaker;

public interface BoardService {
	//타이틀 가져오기
	public List<BoardTitleVO> getTitle() throws Exception;
	
	public BoardTitleVO getTitle1(String title)throws Exception;
	
	//카테고리 별 리스트 가져오기
	public List<BoardVO> boardList(PageMaker pk) throws Exception;
	
	//rno를 이용해 해당 게시판 가져오기
	public BoardVO readBoard(int bno)throws Exception;
	
	//타이틀 별 게시판 갯수
	public int countBaord(PageMaker pk)throws Exception;
	
	//게시판 삭제
	public void deleteBoard(int bno,String title)throws Exception;
	
	//게시판 등록
	public void addBoard(BoardVO vo)throws Exception;
	
	//시퀀스 현재 번호 가져오기
	public int getBno()throws Exception;
	
	//게시판 수정하기
	public void modifyBoard(BoardVO vo)throws Exception;
	
	//게시판 베스트5
	public List<BoardVO> getboardBest()throws Exception;
	
	//검색
	public List<BoardVO> searchBoard(String board_title)throws Exception;
	
	//좋아요 ++
	public void addLike(LikeCheck likeCheck)throws Exception;
	
	//좋아요 --
	public void decLike(LikeCheck likeCheck)throws Exception;
	
	//좋아요 체크
	public LikeCheck likeCheck(Map map)throws Exception;
	
	//좋아요 폼 추가
	public void insertLikeCheck(Map map)throws Exception;
	
}
