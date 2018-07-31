package org.test.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.test.domain.BoardTitleVO;
import org.test.domain.UserVO;
import org.test.persistence.AdminDAO;

@Service
public class AdminServiceImpl implements AdminService{

	@Inject
	private AdminDAO dao;
	
	@Override
	public List<UserVO> getAllUser() throws Exception {
		// TODO Auto-generated method stub
		return dao.getAllUser();
	}

	@Override
	public void deleteUser(String email) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteUser(email);
	}

	@Override
	public void insertTitle(BoardTitleVO boardTitle) throws Exception {
		// TODO Auto-generated method stub
		dao.insertTitle(boardTitle);
	}

	@Override
	public List<BoardTitleVO> readTitle() throws Exception {
		// TODO Auto-generated method stub
		return dao.readTitle();
	}

	@Override
	public void deleteTitle(String title) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteTitle(title);
	}

	@Override
	public void modifyTitle(BoardTitleVO boardTitle) throws Exception {
		// TODO Auto-generated method stub
		dao.modifyTitle(boardTitle);
	}

	@Override
	public void modifyTitleImg(BoardTitleVO boardTitle) throws Exception {
		// TODO Auto-generated method stub
		dao.modifyTitleImg(boardTitle);
	}

	
}
