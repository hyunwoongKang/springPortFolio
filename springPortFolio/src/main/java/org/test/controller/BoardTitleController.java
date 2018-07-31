package org.test.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.test.domain.BoardTitleVO;
import org.test.service.BoardService;

@RestController
@RequestMapping("/title/")
public class BoardTitleController {

	@Inject
	private BoardService service;
	
	private static final Logger logger=LoggerFactory.getLogger(BoardController.class);
	
	@RequestMapping(value="/getTitle",method=RequestMethod.GET)
	public ResponseEntity<List<BoardTitleVO>> getTitle(){
		ResponseEntity<List<BoardTitleVO>> entity=null;

		logger.info("getTitle Start!!");
		
		try {
			entity=new ResponseEntity<List<BoardTitleVO>>(service.getTitle(),HttpStatus.OK);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity=new ResponseEntity<List<BoardTitleVO>>(HttpStatus.BAD_REQUEST);
		}
		
		
		return entity;
	}
	

	
	
	
}
