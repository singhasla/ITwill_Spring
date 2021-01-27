package com.myspring.pro28.ex01;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;



//파일 업로드 컨트롤러 
//파일과 
@Controller
public class FileUploadController  {
	private static final String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo";
	
	//업로드 선택 화면인 uploadForm.jsp로 가기 위한 메소드 
	@RequestMapping(value="/form")
	public String form() {
	    return "uploadForm";
	  }
	
	//다중 파일 업로드 요청을 받았을때 호출되는 메소드 
	@RequestMapping(value="/upload",method = RequestMethod.POST)
	public ModelAndView upload(
								MultipartHttpServletRequest multipartRequest,
								HttpServletResponse response)throws Exception{
		//업로드할 다중파일 정보 한글처리
		multipartRequest.setCharacterEncoding("utf-8");
		//다중파일업로드시 입력한 아이디,이름과 업로드할 파일정보를 저장할 HashMap생성 
		Map map = new HashMap();
		//uploadForm.jsp에 존재하는 <input>의 name속성값들을 모두 Enumeraction배열객체에 담아 반환 받는다.
		Enumeration enu=multipartRequest.getParameterNames();
		
		//Enumeraction배열객체에  <input>의 name속성값들이 저장되어 있는 동안 반복
		while(enu.hasMoreElements()){
			//Enumeraction배열객체에 저장되어 있는 <input>의 name속성값을 차례대로 꺼내온다.
			String name=(String)enu.nextElement();
			//<input>태그의 name속성값에 해당하는 value속성값들을 차례대로 꺼내와 얻는다.
			String value=multipartRequest.getParameter(name);
			
			//System.out.println(name+", "+value);
			//입력한 아이디,이름과 업로드할 파일 정보들을 key와 value 한쌍 형태로 HashMap에 차례대로 저장한다.
			map.put(name,value);
		}
		//파일을 업로드 한 후 반환된 파일 이름이 저장된 List를 반환 받아 HashMap에 저장
		List fileList= fileProcess(multipartRequest);
		map.put("fileList", fileList);
		
	
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map);
		mav.setViewName("result");
		return mav;
	}
	
	
	private List<String> fileProcess(MultipartHttpServletRequest multipartRequest) throws Exception{
		
		List<String> fileList= new ArrayList<String>();
		//첨부된 파일 이름을 가져옵니다.
		Iterator<String> fileNames = multipartRequest.getFileNames();
		
		while(fileNames.hasNext()){
			
			String fileName = fileNames.next();
			//파일 이름에 대한 MultipartFile객체를 가져 옵니다.
			MultipartFile mFile = multipartRequest.getFile(fileName);
			//실제 업로드된 파일 이름을 가져옵니다.
			String originalFileName=mFile.getOriginalFilename();
			//실제 업로드된 파일 이름을 List에 저장
			fileList.add(originalFileName);
			
			File file = new File(CURR_IMAGE_REPO_PATH +"\\"+ fileName);
			
			//첨부된 파일이 있는지 체크 합니다.
			if(mFile.getSize()!=0){ //File Null Check
				//경로에 파일이 없으면 그 경로에 해당하는 디렉터리를 만든 후 파일을 생성합니다.
				if(! file.exists()){ 
					if(file.getParentFile().mkdirs()){ 
						file.createNewFile(); 
					}
				}
				//임시로 저장된 multipartFile을 실제 파일로 전송합니다.
				mFile.transferTo(new File(CURR_IMAGE_REPO_PATH +"\\"+ originalFileName)); 
			}
		}
		//첨부한 파일 이름이 저장된 리스트를 반환 
		return fileList;
	}
}




