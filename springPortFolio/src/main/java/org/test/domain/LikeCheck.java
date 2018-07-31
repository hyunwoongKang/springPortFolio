package org.test.domain;

public class LikeCheck {
	
	private Integer bno;
	private String nickname;
	private char likeCheck;
	public Integer getBno() {
		return bno;
	}
	public void setBno(Integer bno) {
		this.bno = bno;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public char getLikeCheck() {
		return likeCheck;
	}
	public void setLikeCheck(char likeCheck) {
		this.likeCheck = likeCheck;
	}
	@Override
	public String toString() {
		return "LikeCheck [bno=" + bno + ", nickname=" + nickname + ", likeCheck=" + likeCheck + "]";
	}
	
	
	
}
