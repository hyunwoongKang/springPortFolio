package org.test.domain;

import org.springframework.web.multipart.MultipartFile;

public class MultiFileVO {

	private MultipartFile multiFile;

	public MultipartFile getMultiFile() {
		return multiFile;
	}

	public void setMultiFile(MultipartFile multiFile) {
		this.multiFile = multiFile;
	}

	@Override
	public String toString() {
		return "MultiFileVO [multiFile=" + multiFile + "]";
	}
	
	
	
}
