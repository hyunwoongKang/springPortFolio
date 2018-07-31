package org.test.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {
	
	//타이틀
	private String title;
	//검색
	private String search;
	//page번호
	private int page;
	//1page당 몇개씩 
	private int perPageNum;
	//query total count
	private int totalCount;
	//시작 페이지 
	private int startPage;
	//끝 페이지
	private int endPage;
	//이전 
	private boolean prev;
	//이후
	private boolean next;

	//몇개씩 a태그
	private int displayPageNum=10;
	
	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public PageMaker() {
		page =1;
		perPageNum=20;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPage() {
		return page;
	}

	
	public void setPage(int page) {
      if(page<=0) {
    	  this.page=1;
    	  return;
      }
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		
		if(perPageNum <=0 || perPageNum>200) {
			perPageNum=20;
		}
		this.perPageNum = perPageNum;
	}
	
	public int getPageStart() {
		return ((this.page-1)*this.perPageNum)+1;
	}

	
	//////////////////////////////////////////////////////////////////////////////////////
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}

	
	private void calcData() {
		// TODO Auto-generated method stub
		//현재 페이지 번호/몇개씩 보여주는지 
		// 나눈 값을 올림
		//올림 후 몇개씩 보여주는지 값을 곱합 = 현재 페이지 기준 끝페이지
		endPage=(int)(Math.ceil(page/(double)displayPageNum)*displayPageNum);
		//현재 페이지 기준 끝 페이지 - 몇개 보여주는지 +1;
		startPage=(endPage-displayPageNum)+1;
		
		int temp=(int)(Math.ceil(totalCount/(double)perPageNum));
		
		if(endPage>temp) {
			endPage=temp;
		}
		prev=startPage==1?false:true;
		next=endPage*perPageNum >=totalCount?false:true;
		
	}

	
	
	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}



 @Override
	public String toString() {
		return "PageMaker [title=" + title + ", search=" + search + ", page=" + page + ", perPageNum=" + perPageNum
				+ ", totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev=" + prev
				+ ", next=" + next + ", displayPageNum=" + displayPageNum + "]";
	}

public String makeUri(int page) {
	UriComponents uri;
	
	if(title!=null) {
	  uri=UriComponentsBuilder.newInstance().queryParam("title", title).queryParam("page", page).queryParam("perPageNum", perPageNum).build();
	}
	else {
	  uri=UriComponentsBuilder.newInstance().queryParam("search",search).queryParam("page", page).queryParam("perPageNum",perPageNum).build();
	}
	 return uri.toUriString();
 }

	
	

	
	
}
