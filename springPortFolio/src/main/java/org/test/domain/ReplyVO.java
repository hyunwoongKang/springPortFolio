package org.test.domain;

import java.util.Date;

public class ReplyVO {
	
	//tbl_user bno 를 fk
	private Integer bno;
	//reply number pk
	private Integer rno;
	private String replyText;
	private String replyer;
	private Date updateDate;
	private String  user_image;
	
	

	public Integer getBno() {
		return bno;
	}
	public void setBno(Integer bno) {
		this.bno = bno;
	}
	public Integer getRno() {
		return rno;
	}
	public void setRno(Integer rno) {
		this.rno = rno;
	}
	public String getReplyText() {
		return replyText;
	}
	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	public String getReplyer() {
		return replyer;
	}
	public void setReplyer(String replyer) {
		this.replyer = replyer;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	@Override
	public String toString() {
		return "ReplyVO [bno=" + bno + ", rno=" + rno + ", replyText=" + replyText + ", replyer=" + replyer
				+ ", updateDate=" + updateDate + ", user_image=" + user_image + "]";
	}
	
	
	
	
	
}
