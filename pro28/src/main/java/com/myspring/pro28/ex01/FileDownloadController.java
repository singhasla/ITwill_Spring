package com.myspring.pro28.ex01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


//버퍼 기능을 이용해 빠르게 브라우저로 이미지 파일을 전송 하여 다운로드 시키는 역할 
//@Controller
public class FileDownloadController {
	//다운로드할 이미지 파일 저장 위치를 지정합니다.
	private static String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo";

	@RequestMapping("/download")
	protected void download(
							//다운로드할 이미지 파일 이름을 전달 받습니다.
							 @RequestParam("imageFileName") String imageFileName,
			                 HttpServletResponse response)throws Exception {
		
		//버퍼에 모아둔 이미지 파일의 데이터들을 웹브라우저로 출력할(내보내어 다운로드 시킬) 출력 스트림 통로 객체 생성 
		OutputStream out = response.getOutputStream();
		
		//다운로드할 파일 경로를 포함한 이미지파일이름을 문자열로 만든다
		String downFile = CURR_IMAGE_REPO_PATH + "\\" + imageFileName;
		
		//다운로드할 파일 객체를 생성
		File file = new File(downFile);

	
		response.setHeader("Cache-Control", "no-cache");
		//헤더에 파일 이름을 설정합니다.
		response.addHeader("Content-disposition", "attachment; fileName=" + imageFileName);
		
		//다운로드할 이미지 파일에 접근해 이미지의 데이터들을 바이트단위로 읽어 들일 스트림통로 객체 생성
		FileInputStream in = new FileInputStream(file);
		
		//버퍼
		byte[] buffer = new byte[1024 * 8];
		
		//버퍼를 이용해 한번에 8kbyte씩 다운로드할 이미지파일의 데이터를 웹브라우저로 전송 합니다.
		while (true) {
			int count = in.read(buffer); // 버퍼에 읽어들인 바이트 개수 반환 , 읽어 들이지 못하면 -1을 반환 
			if (count == -1) // 버퍼의 마지막에 도달했는지 체크
				break;
			out.write(buffer, 0, count);
		}
		in.close();
		out.close();
	}

}
