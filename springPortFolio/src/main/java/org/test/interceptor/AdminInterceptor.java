package org.test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.test.domain.UserVO;

public class AdminInterceptor extends HandlerInterceptorAdapter{

	private static final String LOGIN="login";
	private static final Logger logger=LoggerFactory.getLogger(AdminInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("AdminInterceptor preHandle !!!!!!");
		HttpSession session=request.getSession();
		UserVO user=(UserVO)session.getAttribute(LOGIN);
		
		if(user==null || user.getAdminCheck()=='N') {
			response.sendRedirect("/");
		}
		
		return true;
	}

}
