package org.test.domain;

import java.util.Arrays;
import java.util.List;

public class UserVO {
	

	private String upw;
	private String email;
	private String phone;
	private Integer point;
	private String nickName;
	private String name;
	private String user_image;
    private char adminCheck;
    private List interestList;


	public List getInterestList() {
		return interestList;
	}
	public void setInterestList(List interestList) {
		this.interestList = interestList;
	}
	public char getAdminCheck() {
		return adminCheck;
	}
	public void setAdminCheck(char adminCheck) {
		this.adminCheck = adminCheck;
	}
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUpw() {
		return upw;
	}
	public void setUpw(String upw) {
		this.upw = upw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	@Override
	public String toString() {
		return "UserVO [upw=" + upw + ", email=" + email + ", phone=" + phone + ", point=" + point + ", nickName="
				+ nickName + ", name=" + name + ", user_image=" + user_image + ", adminCheck=" + adminCheck
				+ ", interestList=" + interestList + "]";
	}


	

}
