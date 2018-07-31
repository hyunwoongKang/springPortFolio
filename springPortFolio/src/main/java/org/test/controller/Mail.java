package org.test.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class Mail {
	
	@Inject
	private JavaMailSender mailSender;
	
	private static final Logger logger= LoggerFactory.getLogger(Mail.class);
	
	@RequestMapping(value = "/emailSender", method = RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> emailSender(String email) {

		ResponseEntity<Map<String,Object>> entity = null;
		logger.info("이메일 전송 POST");
		logger.info("수취인 이메일 : " + email);

		// 상대 아뒤
		String receive_email = email;
		String mail_title = "인증번호 요청";

		String number = Arrays.asList(1, 2, 3, 4).stream()
				.map(x -> x % 2 == 0 ? String.valueOf((int) ((Math.random() * 100) + 1))
						: String.valueOf((char) ((Math.random() * 26) + 65)))
				.collect(Collectors.joining());
		logger.info("number : " + number);

		try {

			logger.info("이메일 전송 OK");
		
			sendMail(receive_email,mail_title,number);
			
			Map<String,Object> map=new HashMap();
			map.put("number", number);
			entity = new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
		} 
		
		catch (Exception e) {
			// TODO: handle exception
			logger.info("이메일 전송 실패");
			entity = new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}

		return entity;
	}
	
	//메일 보내기 
	public void sendMail(String receive_email,String mail_title, String text) throws MessagingException {
		
		
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

		
		String sender_email = "woo0ng2916@gmail.com";
		
		messageHelper.setFrom(sender_email); // 보내는사람 생략하거나 하면 정상작동을 안함
		messageHelper.setTo(receive_email); // 받는사람 이메일
		messageHelper.setSubject(mail_title); // 메일제목은 생략이 가능하다
		messageHelper.setText("인증번호 : " +text); // 메일 내용
		
		mailSender.send(message);
		logger.info("메일 보냄");
	}
}
