package org.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.test.domain.PageMaker;
import org.test.domain.ReplyVO;
import org.test.service.ReplyService;

@RestController
@RequestMapping("/reply")
public class ReplyController {
	
	@Inject
	private ReplyService service;
	
	private static final Logger logger=LoggerFactory.getLogger(ReplyController.class);
	
	//모든 댓글 가져오기  get
	@RequestMapping(value="/all/{bno}/{page}",method=RequestMethod.GET)
	public ResponseEntity<Map<String,Object>> getAllReply(@PathVariable("bno") int bno,@PathVariable("page") int page){
		
		ResponseEntity<Map<String,Object>> entity=null;
		
		logger.info("모든 댓글 가져오기 시작!!!");
		logger.info("bno : "+bno);
		
		PageMaker pk=new PageMaker();
		
		try {
			
			int rnoTotalCount=service.getRnocount(bno);
			logger.info(bno+"게시판 총 댓글 수 : "+rnoTotalCount );
			logger.info("현재 페이지 : "+page);
			pk.setPage(page);
			pk.setPerPageNum(10);
			pk.setTotalCount(rnoTotalCount);
		
			
			logger.info("pk : "+pk.toString());
			
		    Map<String,Object> map=new HashMap();
		    
		    map.put("reply", service.getAllReply(bno,pk));
		    map.put("pk", pk);
		    
		    logger.info(service.getAllReply(bno,pk).toString());
			
			entity=new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity=new ResponseEntity<Map<String,Object>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//댓글 추가  post
	@RequestMapping(value="/addReply",method=RequestMethod.POST)
	public ResponseEntity<String> addReplay(ReplyVO reply){
		ResponseEntity<String> entity=null;
		
		logger.info("댓글 추가!!!!!!!");
		logger.info(reply.toString());
		
		try {
			service.addReply(reply);
			entity=new ResponseEntity<String>("success",HttpStatus.OK);
			logger.info("댓글 추가 성공");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			logger.info("댓글 추가 실패");
		}
		
		return entity;
		
		
	}
	
	
	//댓글 삭제 delete
	@RequestMapping(value="/delete/{rno}",method=RequestMethod.DELETE)
	public ResponseEntity<String> replyDelete(@PathVariable("rno") int rno,@RequestBody ReplyVO vo)
	{
		logger.info("댓글 삭제 시작!!!!!!");
		logger.info(vo.toString());
		ResponseEntity<String> entity=null;
		
		try {
			service.removeReply(vo);
			entity=new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//댓글 수정  put,patch
	@RequestMapping(value="/modify",method= {RequestMethod.PUT,RequestMethod.PATCH})
	public ResponseEntity<String> modifyReply(@RequestBody ReplyVO vo){
		ResponseEntity<String> entity=null;
		
		logger.info("댓글 수정 !!");
	    logger.info(vo.toString());
		try {
			service.modifyReply(vo);
			entity=new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
		
		
	}
	
}








