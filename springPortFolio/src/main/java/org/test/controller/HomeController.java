package org.test.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.test.domain.BoardVO;
import org.test.service.BoardService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Inject
	private BoardService service;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws Exception 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request,Model model,HttpServletResponse response) throws Exception {
		Cookie[] cookie=request.getCookies();
		
		//쿠키 관련
		if(cookie!=null ) 
		{
			model.addAttribute("cookieLength",cookie.length);
			if(cookie.length!=1) 
			{
			
			logger.info("cookie length : "+String.valueOf(cookie.length));
			

			
			//jession id 를 제일 끝에 두기 위한 작업
			for(int i=0;i<cookie.length-1;i++) 
			{
				for(int j=i+1;j<=cookie.length;j++) 
				{
					if(cookie[i].getName().equals("JSESSIONID")) 
					{
						Cookie tempCookie=new Cookie(cookie[i].getName(),cookie[i].getValue());
						
					    cookie[i]=cookie[j];
					    
					    cookie[j]=tempCookie;
						
						}
				}
			}
			
			for(Cookie a:cookie) {
				logger.info("단순한 테스트 : "+a.getName()+" : "+a.getValue());
			}
			
			
			int boardLength=5;
			
			if(cookie.length<5) 
			{
				boardLength=cookie.length;
				
				if(cookie[cookie.length-1].getName().equals("JSESSIONID")) 
				{
					boardLength=cookie.length-1;
				}
			}
			logger.info("보드 렝스!!! : "+boardLength);
			BoardVO[] board=new BoardVO[boardLength];
			
			int count=0;
			for(Cookie c:cookie) {
				if(count<5 && !c.getName().equals("JSESSIONID")) {
					logger.info("최근에 본 게시판 정보: "+c.getName()+" : "+c.getValue());
					int bno=Integer.parseInt(c.getValue());	
					logger.info("count : "+String.valueOf(count));
					
					board[count]=service.readBoard(bno);
					
				}
				count++;
			}
			
			model.addAttribute("recentReadBoard",board);
		}
		  }
		
		

		return "main";
	}
	
}
