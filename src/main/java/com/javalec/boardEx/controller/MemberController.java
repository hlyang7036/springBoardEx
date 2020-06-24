package com.javalec.boardEx.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.javalec.boardEx.service.MemberService;
import com.javalec.boardEx.vo.MemberVO;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject
	MemberService service;
	
	@Inject
	BCryptPasswordEncoder pwdEncoder;

	// 회원가입 get
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String getRegister() throws Exception {
		logger.info("get Register");

		return "member/register";
	}

	// 회원가입 post
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String postRegister(MemberVO memberVO) throws Exception {
		logger.info("post Register");
		int result = service.idChk(memberVO);
		try {
			if (result == 1) {
				return "/member/register";	// 입력된 아이디가 존재하면 -> 다시 회원가입 페이지로 이동
			} else if (result == 0) {
				String inputPass = memberVO.getUserPass();
				String pwd = pwdEncoder.encode(inputPass);
				memberVO.setUserPass(pwd);
				service.register(memberVO);	// 아이디가 존재하지 않는다면 -> 회원가입절차
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
		
		return "redirect:/";
	}

	// 회원 정보 수정 GET
	@RequestMapping(value = "/memberUpdateView", method = RequestMethod.GET)
	public String registerUpdate() throws Exception {
		logger.info("memberUpdate get");
		
		return "member/memberUpdateView";
	}

	// 회원 정보 수정 POST
	@RequestMapping(value = "/memberUpdate", method = RequestMethod.POST)
	public String registerUpdate(MemberVO memberVO, HttpSession session) throws Exception {
		logger.info("memberUpdate post");
		
		String pwd = pwdEncoder.encode(memberVO.getUserPass());
		
		memberVO.setUserPass(pwd);
		
		service.memberUpdate(memberVO);

		session.invalidate(); // 세션을 끊어줌

		return "redirect:/"; // 로그인 페이지로 이동
	}
	
	// 회원탈퇴 GET
	@RequestMapping(value="/memberDeleteView", method=RequestMethod.GET)
	public String memberDelete() throws Exception {
		logger.info("memberDelete get");
		return "member/memberDeleteView";
		
	}
	
	// 회원탈퇴 POST
	@RequestMapping(value="/memberDelete", method=RequestMethod.POST)
	public String memberDeleteView(MemberVO memberVO, HttpSession session, RedirectAttributes rttr) throws Exception {
		logger.info("memberDelete post");
		
		/*
		 * // 세션에 있는 member를 가져와 member변수에 넣어준다 MemberVO member =
		 * (MemberVO)session.getAttribute("member");
		 * 
		 * // 세션에서 member에 넣은 member의 password String sessionPass =
		 * member.getUserPass();
		 * 
		 * // 유저확인을 위해 사용자가 입력했던 memberVo에 있는 password String voPass =
		 * memberVO.getUserPass();
		 * 
		 * if (!(sessionPass.equals(voPass))) { rttr.addFlashAttribute("msg", false);
		 * return "redirect:/member/memberDeleteView"; }
		 */		
		service.memberDelete(memberVO);
		session.invalidate();
		
		return "redirect:/";
	}
	
	// ajax 비밀번호 체크
	@ResponseBody
	@RequestMapping(value="/passChk", method=RequestMethod.POST)
	public boolean passChk(MemberVO memberVO) throws Exception {
		logger.info("passChk post");
		/*
		 * int result = service.passChk(memberVO); // 아이디와 비밀번호가 일치하면 result에는 1이 들어가게된다
		 */
		MemberVO login = service.login(memberVO);
		boolean pwdChk = pwdEncoder.matches(memberVO.getUserPass(), login.getUserPass());
		System.out.println(pwdChk);
		return pwdChk;
	}
	
	// ajax 아이디 중복 체크
	@ResponseBody
	@RequestMapping(value="/idChk", method=RequestMethod.POST)
	public int idChk(MemberVO memberVO) throws Exception {
		logger.info("idChk post");
		
		int result =  service.idChk(memberVO); // 아이디가 일치하면 1이 일치하지 않으면 0이 결과값에 들어간다
		System.out.println("idChk result : " + result);
		return result;
	}
	
	// ajax 유저이름 중복 체크
	@ResponseBody
	@RequestMapping(value="/userNameChk", method=RequestMethod.POST)
	public int userNameChk(MemberVO memberVO) throws Exception {
		logger.info("userNameChk");
		
		
		int result = service.userNameChk(memberVO);
		System.out.println("userNameChk result : " + result);
		
		return result;
	}
	
	
	// 로그인
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(MemberVO memberVO, HttpServletRequest req, RedirectAttributes rttr) throws Exception {
		logger.info("post login");

		HttpSession session = req.getSession(); // 서블릿에 요정된 사용자의 세션 정보를 가져와 세션에 담는다
		MemberVO login = service.login(memberVO); // member정보를 서버에서 가져와 저장
		/*
		 * boolean pwdMatch = pwdEncoder.matches(memberVO.getUserPass(),
		 * login.getUserPass()); // 로그인시 입력된 비밀번호와 서버에 저장된 비밀번호를 비교하여 // true/false를
		 * 반환한다
		 */
		
		boolean pwdMatch;
		// 로그인시 틀린 아이디 입력시 서버에서 가져오는 로그인 정보가 없기 때문에 널에 대한 처리를 해준다
		if (login != null) {
			pwdMatch = pwdEncoder.matches(memberVO.getUserPass(), login.getUserPass());
		} else {
			pwdMatch = false;
		}
		
		
		if (login != null && pwdMatch == true) { 	// member의 정보가 널이 아니고 비밀번호가 일치하면
			session.setAttribute("member", login);  // member정보를 세션에 담는다
			
		} else {
			session.setAttribute("member", null);	// 세션에 member의 정보를 null로 처리하여 세션에 담는다
			rttr.addFlashAttribute("msg", false); 	// redirect할때 url에 보내는 정보를 없앤다
		}
		return "redirect:/";
	}

	// 로그아웃
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) throws Exception {
		logger.info("get logout");
		session.invalidate();	// 세션 종료

		return "redirect:/";
	}

}
