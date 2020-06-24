package com.javalec.boardEx.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javalec.boardEx.service.BoardService;
import com.javalec.boardEx.service.ReplyService;
import com.javalec.boardEx.vo.BoardVO;
import com.javalec.boardEx.vo.PageMaker;
import com.javalec.boardEx.vo.ReplyVO;
import com.javalec.boardEx.vo.SearchCriteria;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	BoardService service;
	
	@Inject
	ReplyService replyService;
	
	// 게시글 작성 페이지 
	@RequestMapping(value = "/board/writeView", method = RequestMethod.GET)
	public void writeView() throws Exception{
		logger.info("writeView");
		
	}
	
	// 게시글 작성  
	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String writeView(BoardVO boardVO, MultipartHttpServletRequest mpRequest) throws Exception{
		logger.info("writeView");
		System.out.println("boardVO.getMno() : " +boardVO.getMno());
		service.write(boardVO, mpRequest);
		return "redirect:/board/list";
	}
	
	// 게시글 목록
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(Model model, @ModelAttribute("scri") SearchCriteria scri) throws Exception{
		logger.info("list");
		model.addAttribute("list",service.list(scri));
		
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(scri);
		pageMaker.setTotalCount(service.listCount(scri));
		
		model.addAttribute("pageMaker", pageMaker);
		
		return "board/list";
	}
	
	// 게시글 상세 페이지
	@RequestMapping(value = "/readView", method = RequestMethod.GET)
	public String read(Model model, @ModelAttribute("scri") SearchCriteria scri, BoardVO boardVO) throws Exception{
		logger.info("read");
		
		model.addAttribute("read", service.read(boardVO.getBno()));
		model.addAttribute("scri", scri);
		
		// 게시글 상세 안에 댓글 리스트
		/*
		 * List<ReplyVO> replyList = replyService.readReply(boardVO.getBno());
		 * model.addAttribute("replyList", replyList);
		 */
		
		// 게시글안에 첨부된 파일 리스트를 가져옴
		List<Map<String, Object>> fileList = service.selectFileList(boardVO.getBno());
		model.addAttribute("file", fileList);
		
		return "/board/readView"; 
	}
	
	// 파일 다운로드
	@RequestMapping(value="fileDown")
	public void fileDown(@RequestParam Map<String, Object> map, HttpServletResponse response) throws Exception {
		
		Map<String, Object> resultMap = service.selectFileInfo(map);
		String storedFileName = (String)resultMap.get("STORED_FILE_NAME");
		String originalFileName = (String)resultMap.get("ORG_FILE_NAME");
		
		// 파일을 저장했던 위치에서 첨부파일을 읽어 byte형식으로 변환한다
		byte fileByte[] = org.apache.commons.io.FileUtils.readFileToByteArray(new File("D:\\mp\\file\\"+storedFileName)); 
		
		response.setContentType("application/octet-stream");
		response.setContentLength(fileByte.length);
		response.setHeader("Content-Disposition", "attachment; fileName=\""+URLEncoder.encode(originalFileName, "UTF-8")+"\";");
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	// 게시판 수정 페이지
	@RequestMapping(value = "/updateView", method = RequestMethod.GET)
	public String updateView(Model model, @ModelAttribute("scri") SearchCriteria scri, BoardVO boardVO) throws Exception {
		logger.info("updateView");
		
		model.addAttribute("update", service.read(boardVO.getBno()));
		model.addAttribute("scri", scri);
		
		List<Map<String, Object>> fileList = service.selectFileList(boardVO.getBno());
		model.addAttribute("file", fileList);
		return "/board/updateView";
	}
	
	// 게시글 수정
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr,
							@RequestParam(value="fileNoDel[]") String[] files,
							@RequestParam(value="fileNameDel[]") String[] fileName,
							MultipartHttpServletRequest mpRequest) throws Exception {
		logger.info("update");
		
		
		service.update(boardVO, files, fileName, mpRequest);

		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/list";
	}
	
	// 게시글 삭제
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(BoardVO boardVO, @ModelAttribute("scri") SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		logger.info("delete");
		
		service.delete(boardVO.getBno());
		
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/list";
		
	}
	
	// 댓글 목록 ajax
	@ResponseBody
	@RequestMapping(value="/replyList", method=RequestMethod.GET)
	public List<ReplyVO> replyList(BoardVO boardVO) throws Exception{
		logger.info("replyList");
		List<ReplyVO> replyList =  replyService.readReply(boardVO.getBno()); 
		
		
		return replyList;
	}
	
	// 댓글 작성
	@RequestMapping(value="/replyWrite", method = RequestMethod.POST)
	public String writeReply(ReplyVO replyVO, SearchCriteria scri, RedirectAttributes rttr) throws Exception {
		logger.info("reply write");
		System.out.println("replyVO.getBno() : "+replyVO.getBno());
		replyService.writeReply(replyVO);
		
		rttr.addAttribute("bno", replyVO.getBno());
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		return "redirect:/board/readView";
	}

	// 댓글 수정 GET
	@RequestMapping(value = "/replyUpdateView", method= RequestMethod.GET)
	public String updateReply(Model model, SearchCriteria scri, ReplyVO replyVO) throws Exception {
		
		logger.error("reply update View");
		
		model.addAttribute("replyUpdate", replyService.selectReply(replyVO.getRno()));
		model.addAttribute("scri", scri);
		return "board/replyUpdateView";
	}
	
	
	// 댓글 수정 POST
	@ResponseBody
	@RequestMapping(value = "/replyUpdate", method = RequestMethod.POST)
	public void updateReply(ReplyVO replyVO) throws Exception {
		logger.info("reply update");
		
		System.out.println("replyVO.toString() : "+replyVO.toString());
		replyService.updateReply(replyVO);
		
		
//		  rttr.addAttribute("bno", replyVO.getBno()); rttr.addAttribute("page",
//		  scri.getPage()); rttr.addAttribute("perPageNum", scri.getPerPageNum());
//		  rttr.addAttribute("searchType", scri.getSearchType());
//		  rttr.addAttribute("keyword", scri.getKeyword());
		 
		
//		return "redirect:/board/readView";
	}
	
	// 댓글 삭제 ajax
	@ResponseBody  
	@RequestMapping(value="/replyDelete", method=RequestMethod.POST)
	public void replyDelete(ReplyVO replyVO) throws Exception {
		logger.info("replyDelete");
		System.out.println("replyVO.getRno() :" +replyVO.getRno());
		replyService.deleteReply(replyVO);
		  
	}
	
	/*
	 * // 댓글 삭제 GET
	 * 
	 * @RequestMapping(value = "/replyDeleteView", method = RequestMethod.GET)
	 * public String deleteReply(Model model, ReplyVO replyVO, SearchCriteria scri)
	 * throws Exception { logger.info("reply delete view");
	 * 
	 * model.addAttribute("replyDelete",
	 * replyService.selectReply(replyVO.getRno())); model.addAttribute("scri",
	 * scri);
	 * 
	 * return "/board/replyDeleteView"; }
	 */
	
	
	/*
	 * // 댓글 삭제 POST
	 * 
	 * @RequestMapping(value = "/replyDelete", method = RequestMethod.POST) public
	 * String deleteReply(ReplyVO replyVO, SearchCriteria scri, RedirectAttributes
	 * rttr) throws Exception{ logger.info("reply delete");
	 * System.out.println("replyVO.getRno() : "+replyVO.getRno());
	 * replyService.deleteReply(replyVO);
	 * 
	 * rttr.addAttribute("bno", replyVO.getBno()); rttr.addAttribute("page",
	 * scri.getPage()); rttr.addAttribute("perPageNum", scri.getPerPageNum());
	 * rttr.addAttribute("searchType", scri.getSearchType());
	 * rttr.addAttribute("keyword", scri.getKeyword());
	 * 
	 * 
	 * return "redirect:/board/readView"; }
	 */	 
	
	
	/*
	 * // 댓글 삭제 ajax
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping(value="/replyDelete", method=RequestMethod.POST) public
	 * String replyDelete(ReplyVO replyVO, SearchCriteria scri, RedirectAttributes
	 * rttr) throws Exception { logger.info("replyDelete");
	 * System.out.println("replyVO.getRno() :" +replyVO.getRno());
	 * replyService.deleteReply(replyVO);
	 * 
	 * rttr.addAttribute("bno", replyVO.getBno()); rttr.addAttribute("page",
	 * scri.getPage()); rttr.addAttribute("perPageNum", scri.getPerPageNum());
	 * rttr.addAttribute("searchType", scri.getSearchType());
	 * rttr.addAttribute("keyword", scri.getKeyword());
	 * 
	 * return "redirect:/board/readView"; }
	 */
	
	/*
	 * // 댓글 수정 POST
	 * 
	 * @RequestMapping(value = "/replyUpdate", method = RequestMethod.POST) public
	 * String updateReply(ReplyVO replyVO, SearchCriteria scri, RedirectAttributes
	 * rttr) throws Exception { logger.info("reply update");
	 * 
	 * System.out.println("replyVO.toString() : "+replyVO.toString());
	 * replyService.updateReply(replyVO);
	 * 
	 * 
	 * rttr.addAttribute("bno", replyVO.getBno()); rttr.addAttribute("page",
	 * scri.getPage()); rttr.addAttribute("perPageNum", scri.getPerPageNum());
	 * rttr.addAttribute("searchType", scri.getSearchType());
	 * rttr.addAttribute("keyword", scri.getKeyword());
	 * 
	 * 
	 * return "redirect:/board/readView"; }
	 */

	
}
