package org.test.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.test.domain.UserVO;
import org.test.service.BoardService;
import org.test.service.UserService;
import org.test.util.MediaUtils;


@Controller
public class UploadController {

	//업로드용 기본 경로
		@Resource(name="uploadPath")
		private String uploadPath;
		
		@Inject
		private BoardService service;
		
		@Inject
		private UserService serviceUser;
		
		private static final Logger logger=LoggerFactory.getLogger(UploadController.class);
		
		//썸네일 등록
		@RequestMapping(value="/uploadThumbNail",method=RequestMethod.POST,produces="text/prain;charset=UTF-8")
		public ResponseEntity<String> uploadThumbNaul(MultipartFile file,String title,String nickName)throws Exception{
			ResponseEntity<String> entity=null;
			
			logger.info("썸네일~~~");
			
			int bno=service.getBno();
			String resultPath="";
			
			//uuid
			UUID uuid=UUID.randomUUID();
			
			//uuid+파일 이름
			String fileName=uuid.toString()+"_"+file.getOriginalFilename();
			
		     logger.info("uuid+file.originalName : "+fileName);
		     logger.info("bno : "+bno);
		     
		     //이미지 파일 저장될 폴더 생성(uploadPath/title/nickName/bno)
		     String titlePath=File.separator+title;
		     String nickNamePath=titlePath+File.separator+nickName;
		     String bnoPath=nickNamePath+File.separator+bno;
		     String thumbNailPath=bnoPath+File.separator+"thumbNail";
		     
		     String path=thumbNailPath;
		     
		     //폴더 생성
		    	logger.info("썸네일");
		    	createFolder(uploadPath,titlePath,nickNamePath,bnoPath,thumbNailPath);
		     
		     //이미지 저장
		     File saveFile=new File(uploadPath+path,fileName);
		     FileCopyUtils.copy(file.getBytes(), saveFile);
		    
		    	 
		    resultPath=makeThumbnail(uploadPath,path,fileName);
		     
		     return new ResponseEntity<String>(resultPath,HttpStatus.CREATED);
			
		} 
		
		//썸네일 변경
		@RequestMapping(value="/deleteThumbNail",method=RequestMethod.POST)
		public ResponseEntity<String> deleteThumbNail(@RequestParam("file")String file){
			ResponseEntity<String> entity=null;
			
			logger.info("썸네일 변경!!!!");
			logger.info("file : "+file);
			
			String filePath=file.substring(0, 53);
			logger.info(filePath);
			
			File test= new File(filePath);
			File[] list=test.listFiles();
			
			for(int i=0;i<list.length;i++) {
				logger.info("삭제 할 데이터 : "+list[i].getPath());
				list[i].delete();
			}
			
			
			return new ResponseEntity<String>("deleted",HttpStatus.OK);
		}
		
		
		@RequestMapping(value="/uploadContentImage",method=RequestMethod.POST,produces="text/prain;charset=UTF-8")
		public ResponseEntity<String> uploadContentImage(MultipartFile file,String title,String nickName) throws Exception{
			ResponseEntity<String> entity=null;
			
			logger.info("에이작스 업로드 이미지 작동 !!!!");
			logger.info("image file : "+file.toString());
			logger.info("image OrginalName : "+file.getOriginalFilename());
			logger.info("image Size : "+file.getSize());
			logger.info("image ContentType : "+file.getContentType());
			logger.info("title : "+title);
			logger.info("nickName : "+nickName);
			
			
			int bno=service.getBno();
			String resultPath="";
			
			//uuid
			UUID uuid=UUID.randomUUID();
			
			//uuid+파일 이름
			String fileName=uuid.toString()+"_"+file.getOriginalFilename();
			
		     logger.info("uuid+file.originalName : "+fileName);
		     logger.info("bno : "+bno);
		     
		     //이미지 파일 저장될 폴더 생성(uploadPath/title/nickName/bno)
		     String titlePath=File.separator+title;
		     String nickNamePath=titlePath+File.separator+nickName;
		     String bnoPath=nickNamePath+File.separator+bno;
		     
		     String path="";
		     
		     //폴더 생성
		   
		    	 logger.info("일반 이미지");
		    	 createFolder(uploadPath,titlePath,nickNamePath,bnoPath);
		    	 path=bnoPath;
		     //이미지 저장
		     File saveFile=new File(uploadPath+path,fileName);
		     FileCopyUtils.copy(file.getBytes(), saveFile);
		    
		    	 
		    resultPath=uploadPath+path+File.separator+fileName;
		     
		     return new ResponseEntity<String>(resultPath,HttpStatus.CREATED);
		 
		     
		}
		

		@RequestMapping(value="/uploadUserImage",method=RequestMethod.POST,produces="text/prain;charset=UTF-8")
		public ResponseEntity<String> uploadContentUserImage(MultipartFile file,UserVO user,HttpSession session,HttpServletRequest request) throws Exception{
			ResponseEntity<String> entity=null;
			
			logger.info("유저 이미지 변경 에이작스 업로드 이미지 작동 !!!!");
			logger.info("image file : "+file.toString());
			logger.info("image OrginalName : "+file.getOriginalFilename());
			logger.info("image Size : "+file.getSize());
			logger.info("image ContentType : "+file.getContentType());
			logger.info(user.toString());
			
			String resultPath="";
			
			//uuid
			UUID uuid=UUID.randomUUID();
			
			//uuid+파일 이름
			String fileName=uuid.toString()+"_"+file.getOriginalFilename();
			
		     logger.info("uuid+file.originalName : "+fileName);
		     
		     //이미지 파일 저장될 폴더 생성(uploadPath/title/nickName/bno)
		     String userImagePath=File.separator+user.getNickName();	
		     
		     String path="";
		     
		     //폴더 생성
		    logger.info("userImage 변경");
		    createFolder(uploadPath,userImagePath);
		    path=userImagePath;

		    //이미지 저장
		     File saveFile=new File(uploadPath+path,fileName);
		     FileCopyUtils.copy(file.getBytes(), saveFile);
		    	 
		    resultPath=File.separator+"displayFile?fileName="+uploadPath+path+File.separator+fileName;
		    logger.info("resultPath : "+resultPath);
		    user.setUser_image(resultPath);
		    serviceUser.updateUserImage(user);
		    
		    request.setAttribute("user", serviceUser.login(user));
		    
		     return new ResponseEntity<String>("success",HttpStatus.OK);
		 
		     
		}
		
		
	  // 경로로 폴더 만들기
	  public static void createFolder(String uploadPath,String ...paths) {
		  
		  logger.info("uploadPath : "+uploadPath);
		  logger.info("paths : "+paths.toString());
		  
		  if(new File(uploadPath,paths[paths.length-1]).exists()) {
			  return;
		  }
		  
		  logger.info("paths[paths.length-1] : "+paths[paths.length-1]);
		  
		  for(String path:paths) {
			  logger.info("path : "+path);
			  File makeDir=new File(uploadPath+path);
			  if(!makeDir.exists()) {
				  makeDir.mkdirs();
			  }
			  
		  }
		  
		  
	  }
	  
	  
	  //썸네일 만들기
	  public String makeThumbnail(String uploadPath,String path,String fileName) throws IOException {
			
		  logger.info("썸네일 : "+uploadPath);
		  
		   BufferedImage sourImg = ImageIO.read(new File(uploadPath + path, fileName));
			BufferedImage destImg = Scalr.resize(sourImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 200);

			
			String thumbnailName = uploadPath + path + File.separator + "s_" + fileName;

			logger.info(thumbnailName);
			
			File newFile = new File(thumbnailName);
			// 확장자 전까지 짜르기
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);

			// 썸네일 크기 변경 , 썸네일 네임 , 경로
			ImageIO.write(destImg, formatName.toUpperCase(), newFile);

			logger.info("makeThumnail");
			logger.info("uploadPath : " + uploadPath);
			logger.info("path : " + path);
			logger.info("fileName : " + fileName);

			return thumbnailName.replace(File.separatorChar, '/');
	  }
	  
	  
	  //다시 확인 필요
		@RequestMapping("/displayFile")
		public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
			
			ResponseEntity<byte[]> entity=null;
	     	InputStream in = null;
			try {
				// 이미지 . png || jpeg  등 이미지 파일 여부 확인 하기 위해 확장자만
				String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
				
				MediaType mType = MediaUtils.getMediaType(formatName);

				HttpHeaders headers = new HttpHeaders();
				
				in = new FileInputStream(fileName);
				
				
				//이미지 파일 인지 여부 확인
					headers.setContentType(mType);

				entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
			} finally {
				in.close();
			}
			

			return entity;
		}
		
		
}
