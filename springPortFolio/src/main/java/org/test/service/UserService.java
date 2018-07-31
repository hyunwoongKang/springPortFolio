package org.test.service;

import org.test.domain.UserVO;

public interface UserService {

	//회원가입
	public void joinMembership(UserVO user)throws Exception;
	
	//닉네임 중복확인
	public UserVO nickNameCheck(String nickName)throws Exception;
	
	//이메일 중복확인
	public UserVO emailCheck(String email)throws Exception;
	
	//로그인
	public UserVO login(UserVO user)throws Exception;
	
	//회원정보 수정
	public void updateUser(UserVO user)throws Exception;
	
	//회원 이미지 수정
	public void updateUserImage(UserVO user)throws Exception;
	
	//아이디 찾끼
	public String findEmail(UserVO user)throws Exception;
	
	//비밀번호 찾끼
	public UserVO findPwd(UserVO user)throws Exception;
	
	//비밀번호 변꼉
	public void modifyPwd(UserVO user)throws Exception;
	
}
