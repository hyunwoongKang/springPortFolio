package org.test.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.test.domain.BoardTitleVO;
import org.test.service.AdminService;

@Controller
@RequestMapping("/admin/*")
public class AdminController {
	
	//업로드용 기본 경로
	@Resource(name="uploadPath")
	private String uploadPath;
	
	@Inject
	private AdminService service;

	public static final Logger logger=LoggerFactory.getLogger(AdminController.class);
	
	//유저 관리 겟
	@RequestMapping(value="/member",method=RequestMethod.GET)
	public void getMemberManage(Model model) throws Exception {
		
		model.addAttribute("allUser",service.getAllUser());
		
	}
	
	//타이틀 관리 겟
	@RequestMapping(value="/title",method=RequestMethod.GET)
	public void getTitleManage() throws Exception {
		
	}
	
	//유저 삭제
	@RequestMapping(value="/deleteUser",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteUser(@RequestBody Map<String,Object> map){
		ResponseEntity<String> entity =null;
		String email=map.get("email").toString();		

		try {
			service.deleteUser(email);
			entity=new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			
		}
		return entity;
	}
	
	//타이틀 추가
	@RequestMapping(value="/addTitle",method= RequestMethod.POST)
	public ResponseEntity<String> addTitle(String title,MultipartFile file)  {
		ResponseEntity<String> entity=null;
		
		logger.info("관리자 모드 타이틀 추가 정보 : "+title.toString());
		logger.info("파일 정보 : "+file.getOriginalFilename());
		
		//랜덤 생성
		UUID uuid=UUID.randomUUID();
		
		String fileName=uuid.toString()+"_"+file.getOriginalFilename();
		
		String path1=File.separator+"title";
		String path2=path1+File.separator+title;
		
		String resultPath=path2;
		//폴더 생성
		UploadController uc=new UploadController();
		uc.createFolder(uploadPath,path1,path2);
		
		try {
			
			//이미지 저장
			File saveFile=new File(uploadPath+resultPath,fileName);
			FileCopyUtils.copy(file.getBytes(), saveFile);
			
			String title_img=uploadPath+resultPath+File.separator+fileName;
			BoardTitleVO boardTitle=new BoardTitleVO();
			boardTitle.setTitle(title);
			boardTitle.setTitle_img(title_img);
			
			service.insertTitle(boardTitle);
			entity=new ResponseEntity<String>("success",HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		
		return entity;
	}
	
	@RequestMapping(value="/allTitle",method=RequestMethod.GET)
	public ResponseEntity<List<BoardTitleVO>> getAllTitle(){
		ResponseEntity<List<BoardTitleVO>> entity=null;
		
		try {
			entity=new ResponseEntity<List<BoardTitleVO>>(service.readTitle(),HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity=new ResponseEntity<List<BoardTitleVO>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	//타이틀 삭제
	@RequestMapping(value="/deleteTitle",method=RequestMethod.DELETE)
	public ResponseEntity<String> deleteTitle(@RequestBody BoardTitleVO boardTitle) throws Exception{
		ResponseEntity<String> entity=null;
		logger.info("타이틀 삭제!!!" +boardTitle.getTitle());
		service.deleteTitle(boardTitle.getTitle());
		
		entity=new ResponseEntity<String>("success",HttpStatus.OK);
		return entity;
	}
	
	//타이틀 변경
	@RequestMapping(value="/modifyTitle",method= {RequestMethod.PUT,RequestMethod.PATCH})
	public ResponseEntity<String> modifyTitle(@RequestBody BoardTitleVO boardTitle){
		ResponseEntity<String> entity=null;
		logger.info("title변경!!!!!!! "+boardTitle.toString());
		String path=boardTitle.getTitle_img().substring(0, boardTitle.getTitle_img().lastIndexOf("/"));
		String newPath=boardTitle.getTitle_img().substring(0, 34)+boardTitle.getTitle();
		
		logger.info(path);
		logger.info(newPath);
		
		//이전 이미지 저정된 폴더 명 새로운 타이틀로 변경
		File oldFile=new File(path);
		File newFile=new File(newPath);
		oldFile.renameTo(newFile);

		//새로운 타이틀이 적용된 경로로 디비 저장
		String imgPath=newPath+boardTitle.getTitle_img().substring(boardTitle.getTitle_img().lastIndexOf("/"), boardTitle.getTitle_img().length());
		
		try {
			boardTitle.setTitle_img(imgPath);
			service.modifyTitle(boardTitle);
			entity=new ResponseEntity<String>("success",HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//타이틀 이미지 변경
	@RequestMapping(value="/modifyTitleImg",method=RequestMethod.POST)
	public ResponseEntity<String> modifyTitleImg(MultipartFile file,BoardTitleVO boardTitle){
		ResponseEntity<String> entity=null;
		logger.info("타이틀 이미지 변경"+file.toString());
		logger.info("정보 ㅣ "+boardTitle.toString());
		
		File deleteFile=new File(boardTitle.getTitle_img());
		if(deleteFile.exists()) {
			deleteFile.delete();
		}
		//랜덤 생성
		UUID uuid=UUID.randomUUID();
		
		String fileName=uuid.toString()+"_"+file.getOriginalFilename();
		
		String path1=File.separator+"title";
		String path2=path1+File.separator+boardTitle.getTitle();
		
		String resultPath=path2;
		//폴더 생성
		UploadController uc=new UploadController();
		uc.createFolder(uploadPath,path1,path2);
		
		try {
			
			File saveFile=new File(uploadPath+resultPath,fileName);
			FileCopyUtils.copy(file.getBytes(), saveFile);
			
			String title_img=uploadPath+resultPath+File.separator+fileName;
			boardTitle.setTitle_img(title_img);
			
			
			service.modifyTitleImg(boardTitle);
			
			entity=new ResponseEntity<String>("success",HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			entity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}
