package org.test.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.domain.UserVO;
import org.test.persistence.UserDAO;



@Service
public class UserServiceImpl implements UserService{

	@Inject
	private UserDAO dao;

	
	@Override
	@Transactional
	public void joinMembership(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		
		for(Object title : user.getInterestList()) {
			dao.incBoardTitle(String.valueOf(title));
		}
		
		dao.joinMembership(user);
	}


	@Override
	public UserVO nickNameCheck(String nickName) throws Exception {
		// TODO Auto-generated method stub
		return dao.nickNameCheck(nickName);
	}


	@Override
	public UserVO emailCheck(String email) throws Exception {
		// TODO Auto-generated method stub
		return dao.emailCheck(email);
	}


	@Override
	public UserVO login(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		return dao.login(user);
	}


	@Override
	public void updateUser(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		dao.updateUser(user);
		
	}


	@Override
	public void updateUserImage(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		dao.updateUserImage(user);
	}


	@Override
	public String findEmail(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		return dao.findEmail(user);
	}


	@Override
	public UserVO findPwd(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		return dao.finePwd(user);
	}


	@Override
	public void modifyPwd(UserVO user) throws Exception {
		// TODO Auto-generated method stub
		dao.modifyPwd(user);
	}

	
	



}
