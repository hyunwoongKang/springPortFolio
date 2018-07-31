package org.test.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

//확장자를 가지고 이미지 파일 여부를 확인하는 클래스
public class MediaUtils {
	
	private static Map<String,MediaType> type;
	
	static 
	{
		type=new HashMap<String,MediaType>();
		type.put("JPG", MediaType.IMAGE_JPEG);
		type.put("GIF", MediaType.IMAGE_GIF);
		type.put("PNG", MediaType.IMAGE_PNG);
		
	}
	public static MediaType getMediaType(String mediaType) 
	{
		return type.get(mediaType.toUpperCase());
	}

}
