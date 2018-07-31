package org.test.persistence;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.test.domain.BoardTitleVO;
import org.test.domain.UserVO;

@Repository
public class AdminImpl implements AdminDAO{

	@Inject
	private SqlSession session;
	
	private static String namespace="org.test.mapper.AdminMapper";
	
	
	@Override
	public List<UserVO> getAllUser() throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".allUser");
	}


	@Override
	public void deleteUser(String email) throws Exception {
		// TODO Auto-generated method stub
		session.delete(namespace+".deleteUser",email);
	}


	@Override
	public void insertTitle(BoardTitleVO boardTitle) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace+".insertTitle",boardTitle);
	}


	@Override
	public List<BoardTitleVO> readTitle() throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".readTitle");
	}


	@Override
	public void deleteTitle(String title) throws Exception {
		// TODO Auto-generated method stub
		session.delete(namespace+".deleteTitle",title);
		
	}


	@Override
	public void modifyTitle(BoardTitleVO boardTitle) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".modifyTitle",boardTitle);
	}


	@Override
	public void modifyTitleImg(BoardTitleVO boardTitle) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".modifyTitleImg",boardTitle);
	}

}
