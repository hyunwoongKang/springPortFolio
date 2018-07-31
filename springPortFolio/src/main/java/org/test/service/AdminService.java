package org.test.service;

import java.util.List;

import org.test.domain.BoardTitleVO;
import org.test.domain.UserVO;

public interface AdminService {

	//모든 유저 정보 가져오기
	public List<UserVO> getAllUser()throws Exception;
	
	//유저 지우기
	public void deleteUser(String email)throws Exception;
	
	//타이틀 추가
	public void insertTitle(BoardTitleVO boardTitle)throws Exception;
	
	//타이틀 조회
	public List<BoardTitleVO> readTitle()throws Exception;
	
	//타이틀 삭제
	public void deleteTitle(String title)throws Exception;
	
	//타이틀 수정
	public void modifyTitle(BoardTitleVO boardTitle)throws Exception;
	
	//타이틀 이미지 수정
	public void modifyTitleImg(BoardTitleVO boardTitle)throws Exception;
}
