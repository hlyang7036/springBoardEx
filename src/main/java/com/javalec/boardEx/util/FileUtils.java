package com.javalec.boardEx.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.javalec.boardEx.vo.BoardVO;

@Component("fileUtils")
public class FileUtils {
	
	private static final String filePath = "D:\\mp\\file\\";
	
	public List<Map<String, Object>> parseInsertFileInfo(BoardVO boardVO, 
												MultipartHttpServletRequest mpRequest) throws Exception{
		
		/*
		Iterator은 데이터들의 집합체? 에서 컬렉션으로부터 정보를 얻어올 수 있는 인터페이스입니다.
		List나 배열은 순차적으로 데이터의 접근이 가능하지만, Map등의 클래스들은 순차적으로 접근할 수가 없습니다.
		Iterator을 이용하여 Map에 있는 데이터들을 while문을 이용하여 순차적으로 접근합니다.
		 */
		
		Iterator<String> iterator = mpRequest.getFileNames();	// 요청된 파일의 이름을 iterator 컬렉션에 넣는다
		
		MultipartFile multipartFile = null;		// 파일이 담길 변수
		String originalFileName = null;			// 파일의 원래 이름이 담길 변수
		String originalFileExtension = null;	// 파일의 확장자를 담기 위한 변수
		String storedFileName = null;			// 랜덤하게 변환된 파일 이름이 담길 변수
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();	// 리스트 안에 맵객체를 넣기위한 변수
		Map<String, Object> listMap = null;		// 파일에 대한 정보를 담을 맵 컬렉션 변수 
		
		int bno = boardVO.getBno();	// 게시글의 번호를 담을 변수
		
		File file = new File(filePath);	// 파일의 위치를 정하기 위한 파일변수
		if (file.exists() == false) {	// 만약 디렉토리 파일 이름이 존재하지 않으면
			file.mkdirs();				// 디렉토리를 만든다
			
		}
		
		while (iterator.hasNext()) {	// iterator 컬랙션에 값이 존재하는동안
			multipartFile = mpRequest.getFile(iterator.next());		// 요청된 파일중 iterator에서 파일명에 맞는 객체를 가져와서 저장
			if (multipartFile.isEmpty() == false) {					// 만약 요청된 파일이 존재하면
				originalFileName = multipartFile.getOriginalFilename();	// 원본파일의 이름을 가져와서 파일이름 변수에 저장
				originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));	// 원본파일의 확장자를 파일 확장자 변수에 저장
				storedFileName = getRandomString() + originalFileExtension;	// 원본 파일의 이름을 랜덤아이디로 만들어서 확장자와 합쳐서 저장
				
				
				file = new File(filePath + storedFileName);		// 파일 경로와 랜던아이디로 변환된 파일명으로 파일을 정의한다
				multipartFile.transferTo(file); 	// 정의된 파일을 지정된 경로에 생성한다
				listMap = new HashMap<String, Object>();	// 저장된 맵을 해쉬맵으로 만든다
				listMap.put("BNO", bno);							// 맵안에 값을 넣는다
				listMap.put("ORG_FILE_NAME", originalFileName);
				listMap.put("STORED_FILE_NAME", storedFileName);
				listMap.put("FILE_SIZE", multipartFile.getSize());
				list.add(listMap);									// 리스트에 값을 넣은 맵객체를 저장한다
			}
			
			
		}
		return list;
	}
	
	public List<Map<String, Object>> parseUpdateFileInfo(BoardVO boardVO, String[] files, String[] fileName,
								MultipartHttpServletRequest mpRequest) throws Exception {
		
		Iterator<String> iterator = mpRequest.getFileNames();
		MultipartFile multipartFile = null;
		String originalFileName = null;
		String originalFileExtenstion = null;
		String storedFileName = null;
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> listMap = null;
		int bno = boardVO.getBno();
		
		while (iterator.hasNext()) {
			multipartFile = mpRequest.getFile(iterator.next());
			if (multipartFile.isEmpty()==false) {
				originalFileName = multipartFile.getOriginalFilename();
				originalFileExtenstion = originalFileName.substring(originalFileName.lastIndexOf("."));
				storedFileName = getRandomString() + originalFileExtenstion;
				multipartFile.transferTo(new File(filePath+storedFileName));
				listMap = new HashMap<String, Object>();
				listMap.put("IS_NEW", "Y");
				listMap.put("BNO", bno);
				listMap.put("ORG_FILE_NAME", originalFileName);
				listMap.put("STORED_FILE_NAME", storedFileName);
				listMap.put("FILE_SIZE", multipartFile.getSize());
				list.add(listMap);
				
			}
			
		}
		if (files != null && fileName != null) {
			for (int i = 0; i < fileName.length; i++) {
				listMap = new HashMap<String, Object>();
				listMap.put("IS_NEW", "N");
				listMap.put("FILE_NO", files[i]);
				list.add(listMap);
			}
		}
		
		return list;
	}
	
	public static String getRandomString () {
		
		return UUID.randomUUID().toString().replace("-", "");	// 랜덤아이디를 32잘로 생선한다 "-"가 있다면 없앤다
	}
	
}
