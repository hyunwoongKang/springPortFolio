package org.test.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.test.domain.BoardVO;
import org.test.domain.PageMaker;

public class ReadBoardInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger=LoggerFactory.getLogger(ReadBoardInterceptor.class);
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	
		logger.info("쿠키 등록 인터셉터!!");
		ModelMap modelMap=modelAndView.getModelMap();
		BoardVO boardVO=(BoardVO) modelMap.get("board");
		PageMaker pk=(PageMaker) modelMap.get("pk");
		
		
		logger.info("쿠키에 저장될 보드 데이터 수정: "+boardVO.toString());
		logger.info("쿠키에 저장될 피케이 데이터 수정 : "+pk.toString());
		
		
		String bno=String.valueOf(boardVO.getBno());
		String recentRead="recentRead";
		Cookie[] tempCookie=request.getCookies();
		
		if(tempCookie!=null) {
		recentRead="recentRead"+(tempCookie.length-1);
		}
		
		//최근 게시판에 보여지는 5개의 게시판 중 현재 본 게시판과 일치하는게 있으면 리턴
		//아니면 보여짐
		//같은걸 두번 보면 중복된 걸로 리턴
		
		int length=5;
		logger.info("쿠키 렝크 :  : " +String.valueOf(tempCookie.length));
		
	if(tempCookie.length<5) 
	{
		length=tempCookie.length;
	}
		for(int i=0;i<length;i++) 
		{
				logger.info("interceptor : "+tempCookie[i].getName()+" : "+tempCookie[i].getValue());
				
				if(tempCookie[i].getValue().equals(bno)) 
				{
					logger.info("리턴됨?");
					return;
				}
		}
		
		
	     Cookie cookie =new Cookie(recentRead,bno);
	     cookie.setPath("/");
	     cookie.setMaxAge(60*60*24);
	     response.addCookie(cookie);

	}

}
