package org.test.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.test.domain.BoardTitleVO;
import org.test.domain.BoardVO;
import org.test.domain.LikeCheck;
import org.test.domain.PageMaker;
import org.test.domain.UserVO;
import org.test.service.BoardService;

@RequestMapping("/board/*")
@Controller
public class BoardController {
	
	@Inject
	private BoardService service;
	
	private static final Logger logger=LoggerFactory.getLogger(BoardController.class);
	
	@ResponseBody
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
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public void listBoard(@ModelAttribute("pk")PageMaker pk,Model model) throws Exception {
		
		logger.info("List Board Start");
		logger.info(pk.toString());
		
		
		pk.setTotalCount(service.countBaord(pk));
		model.addAttribute("title", service.getTitle1(pk.getTitle()));
		model.addAttribute("list",service.boardList(pk));
		model.addAttribute("pk",pk);
	}
	
	//게시판 읽기
	@RequestMapping(value="/read",method=RequestMethod.GET)
	public void readBoard(@ModelAttribute("pk")PageMaker pk,@RequestParam("bno")int bno,Model model,HttpSession session) throws Exception {
		logger.info("게시판 읽기 시작!!!");
		logger.info("pageMaker : "+pk.toString());
		logger.info("bno : "+bno);
		
		UserVO user=(UserVO)session.getAttribute("login");
		
		if(user !=null) 
		{
		 logger.info("session 확인 : "+user.toString());
		 
		 //좋아요 했는지 여부 확인 후 
		 Map<String,Object> map=new HashMap();
		 map.put("bno", bno);
		 map.put("nickname", user.getNickName());
		 
		LikeCheck likeCheck= service.likeCheck(map);
		
		if(likeCheck!=null) {
			logger.info("좋아요 체크 확인 : "+likeCheck.toString()); 
			model.addAttribute("likeCheck",likeCheck);
			
		}else {
			service.insertLikeCheck(map);
			
		}
		
		 //모델로 보내기
		}
		
		model.addAttribute("pk",pk);
		model.addAttribute("board",service.readBoard(bno));
		
	}

	//게시판 수정 get
	@RequestMapping(value="/modifyBoard",method=RequestMethod.GET)
	public void modifyBoard(Model model,@RequestParam("bno")int bno,@ModelAttribute("pk")PageMaker pk) throws Exception
	{
		
		logger.info("게시판 수정 시작!!!!");
		logger.info("bno : " +bno);
		model.addAttribute("board",service.readBoard(bno));
	}
	
	//게시판 수정 post
	@RequestMapping(value="/modifyBoard",method=RequestMethod.POST)
	public String modifyBoard(RedirectAttributes rttr,BoardVO board,PageMaker pk) throws Exception {
		logger.info("게시판 수정!!!");
		logger.info(board.toString());
		logger.info(pk.toString());
		
		 service.modifyBoard(board);
		
		rttr.addAttribute("title",pk.getTitle());
		rttr.addAttribute("page",pk.getPage());
		rttr.addAttribute("perPageNum",pk.getPerPageNum());
		
		return "redirect:/board/list";
	}
	
	//게시판 삭제
	@RequestMapping(value="/deleteBoard" ,method=RequestMethod.POST)
	public String deleteBoard(RedirectAttributes rttr,PageMaker pk,@RequestParam("bno") int bno) throws Exception {
	logger.info("게시판 삭제 시작!!!");
	logger.info("pk : "+pk.toString());
	logger.info("bno : "+bno);
	
	rttr.addAttribute("title",pk.getTitle());
	rttr.addAttribute("page",pk.getPage());
	rttr.addAttribute("perPageNum",pk.getPerPageNum());
		
	service.deleteBoard(bno,pk.getTitle());
	
		return "redirect:/board/list";
	}
	
	//게시판 등록 GET
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public void addBoardGet(@ModelAttribute("pk")PageMaker pk)
	{
		logger.info("게시판 등록");
		logger.info("pk : "+pk.toString());
	}
	
	//게시판 등록 POST
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String addBoardPost(BoardVO vo,RedirectAttributes rttr) throws Exception {
	
		
		logger.info("게시판 등록 고고");
		logger.info("vo : "+vo.toString());
		
		String Content=vo.getContent().replace("\r\n", "<br>");
		vo.setContent(Content);
		
		
		
		rttr.addAttribute("title",vo.getTitle());	
		rttr.addFlashAttribute("msg","success");
		
		if(vo.getThumbnail()==null) {
			String tempThumbNailPath=vo.getTitle();
			vo.setThumbnail(tempThumbNailPath);
			
		}
		service.addBoard(vo);

		return "redirect:/board/list";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/bestBoard", method=RequestMethod.GET)
	public ResponseEntity<Map<String,List>> betBoard()
	{
		ResponseEntity<Map<String,List>> entity=null;
		
		logger.info("베스트5 컨트롤러 입장!!!!");
		List<BoardVO> boardList;
		try {
			
			boardList = service.getboardBest();
			List<BoardVO> bestBoard =new ArrayList<>();
			int testCount=0;
			String title;
			
			Map map=new HashMap<String,Object>();
			
			for(int i=0;i<boardList.size();i++) 
			{
				logger.info("게시물 베스트 5 : "+boardList.get(i).toString());
			}

			for(int i=0,j=i+1;i<boardList.size();i++,j++) 
			{

				bestBoard.add(boardList.get(i));

				if(j==boardList.size()) 
				{
					map.put(boardList.get(i).getTitle(), bestBoard);
					break;
				}
				
				//이전꺼랑 다르면 새로 리스트 새로 만들고 
				//맵의 이전꺼 저장
				if(!boardList.get(i).getTitle().equals(boardList.get(j).getTitle()) ) 
				{
					map.put(boardList.get(i).getTitle(), bestBoard);
					bestBoard=new ArrayList<>();
				}
				
			}
			
			logger.info("entryset : "+map.entrySet().toString());
			logger.info("keySet : "+map.keySet().toString());
			
			logger.info(boardList.toString());

			entity=new ResponseEntity<Map<String,List>>(map,HttpStatus.OK);
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity=new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			
		}

		
		return entity;
	}
	
	//좋아요
	@RequestMapping(value="/addLike",method=RequestMethod.GET)
	public ResponseEntity<String> addLike(LikeCheck likeCheck){
		ResponseEntity<String> entity=null;
		
		logger.info("좋아요 테스트 : "+likeCheck.toString());
		
		try {
			//이미 체크가 되어있는 상태이면
			if(likeCheck.getLikeCheck()=='Y') {
				service.decLike(likeCheck);
				entity=new ResponseEntity<String>("success",HttpStatus.OK);
			}
			//체크가 안되어있는 상태라면
			else {
				service.addLike(likeCheck);
				entity=new ResponseEntity<String>("success",HttpStatus.OK);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}

	
}





