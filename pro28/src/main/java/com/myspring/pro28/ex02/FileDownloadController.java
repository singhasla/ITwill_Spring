package com.myspring.pro28.ex02;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.coobird.thumbnailator.Thumbnails;

@Controller
public class FileDownloadController {
	private static String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo";
	/*
	@RequestMapping("/download")
	protected void download(@RequestParam("imageFileName") String imageFileName,
			                 HttpServletResponse response) throws Exception {
		OutputStream out = response.getOutputStream();
		String filePath = CURR_IMAGE_REPO_PATH + "\\" + imageFileName;
		File image = new File(filePath);
		
		//확장자를 제외한 원본 이미지 파일의 이름을 가져 옵니다.
		int lastIndex = imageFileName.lastIndexOf(".");
		String fileName = imageFileName.substring(0,lastIndex);
		
		//원본 이미지 파일 이름과 같은 이름의 썸네일 파일에 대한 File객체를 생성합니다.
		File thumbnail = new File(CURR_IMAGE_REPO_PATH+"\\"+"thumbnail"+"\\"+fileName+".png");
		
		//원본 이미지 파일을 가로 세로가 50픽셀인 png형식의  썸네일 이미지 파일로 생성합니다.
		if (image.exists()) { 
			thumbnail.getParentFile().mkdirs();
		    Thumbnails.of(image).size(50,50).outputFormat("png").toFile(thumbnail);
		}

		
		//생성된 썸네일 파일을 브라우저로 전송해서 표시 합니다.
		FileInputStream in = new FileInputStream(thumbnail);
		byte[] buffer = new byte[1024 * 8];
		while (true) {
			int count = in.read(buffer); // ���ۿ� �о���� ���ڰ���
			if (count == -1) // ������ �������� �����ߴ��� üũ
				break;
			out.write(buffer, 0, count);
		}
		in.close();
		out.close();
	}
	*/
	
	/*
		썸네일을 생성하지 않고  썸네일이미지를  바로 웹브라우저에 출력하기
		
		-  쇼핑몰의 상품 목록 이미지 같은 경우 썸네일 이미지 파일을 따로 생성할 필요없이  
		      썸네일 이미지의 정보를 InputStream으로 읽어 들인 후 OutPutStream통로로 웹브라우저 화면으로 바로 내보내서 표시할수 있다.
	*/
	@RequestMapping("/download")
	protected void download(@RequestParam("imageFileName") String imageFileName,
			                 HttpServletResponse response) throws Exception {
		OutputStream out = response.getOutputStream();
		String filePath = CURR_IMAGE_REPO_PATH + "\\" + imageFileName;
		File image = new File(filePath);
		int lastIndex = imageFileName.lastIndexOf(".");
		String fileName = imageFileName.substring(0,lastIndex);
		File thumbnail = new File(CURR_IMAGE_REPO_PATH+"\\"+"thumbnail"+"\\"+fileName+".png");
		
		if (image.exists()) { 
			//원본 이미지에 대한 썸네일 이미지를 생성 한 후 OutputStream객체에 할당합니다.
			Thumbnails.of(image).size(50,50).outputFormat("png").toOutputStream(out);
		}else {
			return;
		}
		//썸네이일 이미지를 OutputStream통로를 통해 브라우저로 전송합니다.
		byte[] buffer = new byte[1024 * 8];
		out.write(buffer);
		out.close();
	}
	
}
