package org.test.interceptor;

import java.security.PrivateKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.test.domain.RsaVO;
import org.test.util.RSAUtil;

public class RsaInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger=LoggerFactory.getLogger(RsaInterceptor.class);
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception 
	{
		// TODO Auto-generated method stub
		logger.info("RSA POST 인터셉터 확인 !!!!");
		HttpSession session = request.getSession();
		PrivateKey key=(PrivateKey)session.getAttribute("RSAprivateKey");
		
		if(key!=null) {
			logger.info("PrivateKey : "+key);
			session.removeAttribute("RSAprivateKey");
		}
		
		RSAUtil rsaUtil=new RSAUtil();
		RsaVO rsa=rsaUtil.createRSA();
		
		logger.info("RsaVO : "+rsa.toString());
		modelAndView.addObject("modulus", rsa.getModulus());
		modelAndView.addObject("exponent", rsa.getExponent());
		session.setAttribute("RSAprivateKey", rsa.getPriavateKey());

	}


}


