package org.test.persistence;

import java.util.List;
import java.util.Map;

import org.test.domain.BoardTitleVO;
import org.test.domain.BoardVO;
import org.test.domain.LikeCheck;
import org.test.domain.PageMaker;

public interface BoardDAO {
	
	//타이틀 다 가져오기
	public List<BoardTitleVO> getTitle() throws Exception;
	
	//타이틀 가져오기
	public BoardTitleVO getTitle1(String title)throws Exception;
	
	//카테고리 별 게시판 가져오기
	public List<BoardVO> BoardList(PageMaker pk) throws Exception;
	
	//rno를 이용해 해당 맞는 게시판 가져오기
	public BoardVO readBoard(int bno)throws Exception;

	//타이틀 별 게시판 갯수
	public int countBoard(PageMaker pk)throws Exception;
	
	//게시판 삭제
	public void deleteBoard(int bno)throws Exception;
	
	//게시판 등록
	public void addBoard(BoardVO vo)throws Exception;
	
	//시퀀스 가져오기
	public int getBno()throws Exception;
	
	//댓글 등록시 해당 게시판 댓글 수 증가
	public void addReplyCnt(int bno)throws Exception;
	
	//댓글 제거시 해당 게시판 댓글 수 감소
	public void subReplyCnt(int bno)throws Exception;
	
	//게시판 수정하기
	public void modifyBaord(BoardVO vo)throws Exception;
	
	//게시판 조회수 증가
	public void increaseBoard(int bno)throws Exception;
	
	//게시판 베스트5
	public List<BoardVO> getBoardBest()throws Exception;
	
	//검색
	public List<BoardVO> searchBoard(String board_title)throws Exception;
	
	//좋아요 ++
	public void addLike(int bno)throws Exception;
	
	//좋아요 --
	public void decLike(int bno)throws Exception;
	
	//좋아요 체크
    public LikeCheck likeCheck(Map map)throws Exception;
    
    //좋아요 체크 변경
    public void modifyLike(LikeCheck likeCheck)throws Exception;
    
    //종아요 정보 추가
    public void insertLikeCheck(Map map)throws Exception;
    
    //게시물 추가시 타이틀 테이블 게시물 수 증가
    public void incBoard_cnt(String title)throws Exception;
    
  //게시물 삭제시 타이틀 테이블 게시물 수 감소
    public void decBoard_cnt(String title)throws Exception;
    
}	
