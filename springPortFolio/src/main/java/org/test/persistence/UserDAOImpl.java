package org.test.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.test.domain.UserVO;

@Repository
public class UserDAOImpl implements UserDAO{

	@Inject
	private SqlSession session;
	
	private static String namespace="org.test.mapper.UserMapper";
	

	@Override
	public void joinMembership(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace+".joinMembership",user);
	}



	@Override
	public UserVO nickNameCheck(String nickName) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".nickNameCheck",nickName);
	}


	@Override
	public UserVO emailCheck(String email) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".emailCheck",email);
	}


	@Override
	public UserVO login(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".login",user);
	}


	@Override
	public void updateUser(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".modifyUser",user);
	}


	@Override
	public void updateUserImage(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".modifyUserImage",user);
		
	}


	@Override
	public String findEmail(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".findEmail",user);
	}


	@Override
	public UserVO finePwd(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".findPwd",user);
	}


	@Override
	public void modifyPwd(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".modifyPwd",user);
	}



	@Override
	public void incBoardTitle(String title) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".incBoardTitle",title);
		
	}

	

}
