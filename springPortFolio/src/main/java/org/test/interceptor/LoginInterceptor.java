package org.test.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.test.domain.UserVO;

public class LoginInterceptor  extends HandlerInterceptorAdapter{
	
	private static final String LOGIN="login";
	private static final Logger logger=LoggerFactory.getLogger(LoginInterceptor.class);

	
	//동작 이전에 가로채는 역활 preHandle
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
	    HttpSession session=request.getSession();
		 logger.info("Login preHandle 작동");
	    
	    if(session.getAttribute(LOGIN)!=null ) {
	    	logger.info("로그인 정보 삭제");
	    	session.removeAttribute(LOGIN);
	    }
	    
		return true;
	}

	//동작 이후에 처리 (화면 처리하기 전) postHandle
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		logger.info("Login postHandle 작동");
		
		HttpSession session=request.getSession();
		UserVO user= (UserVO) request.getAttribute("user");
		logger.info("postHandle UserVO : "+user.toString());
			
		if(user !=null) {
			logger.info("로그인 ");
			session.setAttribute(LOGIN, user);

		}	
		

		logger.info("Login postHandle 작동 끝");
	}
	
}
