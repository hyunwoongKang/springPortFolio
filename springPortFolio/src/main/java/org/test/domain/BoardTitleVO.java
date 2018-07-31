package org.test.domain;

public class BoardTitleVO {

	private String title;
	private String title_img;
	private Integer readUser_cnt;
	private Integer board_cnt;
	private Integer num;
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle_img() {
		return title_img;
	}
	public void setTitle_img(String title_img) {
		this.title_img = title_img;
	}
	public Integer getReadUser_cnt() {
		return readUser_cnt;
	}
	public void setReadUser_cnt(Integer readUser_cnt) {
		this.readUser_cnt = readUser_cnt;
	}
	public Integer getBoard_cnt() {
		return board_cnt;
	}
	public void setBoard_cnt(Integer board_cnt) {
		this.board_cnt = board_cnt;
	}
	@Override
	public String toString() {
		return "BoardTitleVO1 [title=" + title + ", title_img=" + title_img + ", readUser_cnt=" + readUser_cnt
				+ ", board_cnt=" + board_cnt + ", num=" + num + "]";
	}

	
	
}
