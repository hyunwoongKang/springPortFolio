package org.test.persistence;

import org.test.domain.UserVO;

public interface UserDAO {
	//회원가입(아이디,비번,이메일,폰,닉네임)
	public void joinMembership(UserVO user)throws Exception;
	//닉네임 중복확인
	public UserVO nickNameCheck(String nickName)throws Exception;
	//이메일 중복확인
	public UserVO emailCheck(String email)throws Exception;
	//로그인
	public UserVO login(UserVO user)throws Exception;
	//회원정보 업데이트
	public void updateUser(UserVO user)throws Exception;
	//회원 이미지 업데이트
	public void updateUserImage(UserVO user)throws Exception;
	//아이디찾기
	public String findEmail(UserVO user)throws Exception;
	//비밀번호 찾기
	public UserVO finePwd(UserVO user)throws Exception;
	//비밀번호 변경
	public void modifyPwd(UserVO user)throws Exception;
	//구독자 수 증가
	public void incBoardTitle(String title)throws Exception;
}
