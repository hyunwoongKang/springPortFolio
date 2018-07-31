
package org.test.controller;

import java.security.PrivateKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.test.domain.UserVO;
import org.test.service.BoardService;
import org.test.service.UserService;
import org.test.util.RSAUtil;

@Controller
@RequestMapping("/user/*")
public class UserController {

	@Inject
	private UserService service;
	
	@Inject
	private BoardService boardService;

	@Inject 
	Mail mail;

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@RequestMapping(value = "/joinUser", method = RequestMethod.GET)
	public void getJoinUser(Model model) throws Exception {
		logger.info("JoinUser GET Start!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		model.addAttribute("title",boardService.getTitle());
		
	}

	//회원가입
	@RequestMapping(value = "/joinUser", method = RequestMethod.POST)
	public String postJoinUser(UserVO user) throws Exception {
		logger.info("joinUser POST Start!!!!!!!!!!!!!!!!!!!!!");
		logger.info("joinUser POST UserVO : " + user.toString());

		service.joinMembership(user);

		return "redirect:/";
	}
	
	//닉네임중복확인
	@ResponseBody
	@RequestMapping(value="/nickNamecheck" ,method=RequestMethod.POST)
	public ResponseEntity<String> nickNameCheck(String nickName){
		ResponseEntity<String> entity=null;
		logger.info("닉네임 중복 확인 : "+nickName);
		
			try {
					
				if(service.nickNameCheck(nickName) == null) {
					logger.info("중복되는 닉네임 없음");
					entity=new ResponseEntity<String>("yes",HttpStatus.OK);
				}else {
					logger.info("중복되는 닉네임 있음");
	
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				entity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			
		
		
		return entity;
	}
	
	//이메일 중복확인
		@ResponseBody
		@RequestMapping(value="/emailcheck" ,method=RequestMethod.POST)
		public ResponseEntity<String> emailCheck(String email){
			ResponseEntity<String> entity=null;
			logger.info("이메일 중복 확인 : "+email);
			
				try {
					if(service.emailCheck(email) == null) {
						logger.info("중복되는 이메일 없음");
						entity=new ResponseEntity<String>("yes",HttpStatus.OK);
					}else {
						logger.info("중복되는 이메일 있음");
						entity=new ResponseEntity<String>("no",HttpStatus.OK);
		
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					entity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
				}
				
			
			
			return entity;
			
		}
			
		//로그인
		@RequestMapping(value="/login",method=RequestMethod.POST)
		public ResponseEntity<String> login(UserVO user,HttpServletRequest request,HttpSession session) throws Exception{
			ResponseEntity<String> entity=null;
			
			logger.info(user.toString());
			
			
			PrivateKey key=(PrivateKey)session.getAttribute("RSAprivateKey");
			
			if(key == null)
			{
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			
			session.removeAttribute("RSAprivateKey");
			
			RSAUtil rsaUtil=new RSAUtil();
			
			try {
				
			   String email=rsaUtil.getDecryptText(key, user.getEmail());
			   String upw=rsaUtil.getDecryptText(key, user.getUpw());
			   
			   user.setEmail(email);
			   user.setUpw(upw);
			   
			   
			   user=service.login(user);
			   
				//postHanle의 userVO 값을 주기 위한 
				request.setAttribute("user", user);
				
				entity=new ResponseEntity<String>("success",HttpStatus.OK);
				logger.info("로그인 성공");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.info(e.getMessage());
				entity=new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
			return entity;
		}
		
		//회원정보 변경
		@RequestMapping(value="/updateUserInfo",method=RequestMethod.POST)
		public String updateUserInfo(UserVO user,HttpSession session,HttpServletRequest request) throws Exception {
			logger.info("회원정보 변경 !!! ");
			logger.info("회원 정보 수정 내용 : "+user.toString());
		
			service.updateUser(user);
			
			request.setAttribute("user", service.login(user));
			
			return"redirect:/";
		}
		
		//아이디 찾기
		@ResponseBody
		@RequestMapping(value="/findEmail",method=RequestMethod.POST)
		public ResponseEntity<Map<String,Object>> findEmail(UserVO user) throws Exception
		{
			ResponseEntity<Map<String,Object>> entity=null;
			Map<String,Object> map=new HashMap();
			
			logger.info("아이디 찾기");
			logger.info(user.toString());
			
			String email=service.findEmail(user);
			logger.info("email : "+email);
			
			if(email!=null) {
				logger.info("이메일 널 아님");
				map.put("result", "success");
				map.put("email",email);
			}
			else {
				logger.info("이메일 널 임");
				map.put("result","false");
			}
			entity=new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);

			return entity;
		}
		
		//비밀번호 찾기
		@RequestMapping(value="/findUpw",method=RequestMethod.POST)
		public ResponseEntity<String> findPwd(UserVO user)
		{	
			ResponseEntity<String> entity=null;
			logger.info("비밀번호 찾기");
			logger.info(user.toString());
			
			try {
				UserVO userVO=service.findPwd(user);
				logger.info(userVO.toString());
				
				//임시 비밀번호 랜덤함수로 생성하고
	String tempPwd=Arrays.asList(1,2,3,4,5,6).stream().map(x-> x%2==0?String.valueOf((int)((Math.random()*100)+1)):String.valueOf((char)((Math.random() * 26) + 65))).collect(Collectors.joining());
				logger.info("임시 패스워드 : "+tempPwd);
				
				
				//비밀번호를 찾는 유저 이메일로 임시 비밀번호 보냄
	
				mail.sendMail(userVO.getEmail(), "임시 비밀번호 입니다.",tempPwd+" 해당 비밀번호로 로그인 해주세요.");
				
				//해당 아이디에 비밀번호를 임시번호로 변경하고
				userVO.setUpw(tempPwd);
			    service.modifyPwd(userVO);
					
			    
				entity=new ResponseEntity<String>("success",HttpStatus.OK);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				entity=new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
			
			return entity;
		}
		
		//로그아웃 처리
		@RequestMapping(value="/logout",method=RequestMethod.GET)
		public ResponseEntity<String> userLogout(HttpSession session){
			ResponseEntity<String> entity=null;
			logger.info("로그아웃 처리!");
		    Object login=session.getAttribute("login");

		    if(login!=null) {
		    	session.removeAttribute("login");
		    	session.invalidate();
		    	
		    	entity=new ResponseEntity<String>("success",HttpStatus.OK);
		    }
			
			
			return entity;
		}
		

}
