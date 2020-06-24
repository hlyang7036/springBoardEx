package com.javalec.boardEx.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {

	private int totalCount;				// 전체 게시물의 수, 전체 게시물의 수가 있어야 이를 기반으로 페이징처리가 되기때문
	private int startPage;				// 현재 페이지기준에서 시작 페이지, 현재 페이지를 기준으로 보여지는 페이징처리의 처음과 끝 만약 현재 12페이지면
										// 페이징의 처음은 11부터 20까지보여지고, 현재 27페이지면 21부터 30까지의 페이지가 보여지게된다
	private int endPage;				// 현재 페이지를 기준으로 마지막페이지
	private boolean prev;				// 이전으로 이동하는 버튼이 생길 조건이 되는지를 나타내는 변수
	private boolean next;				// 다음으로 이동하는 버튼이 생길 조건이 되는지를 나타내는 변수
	private int disPlayPageNum = 10;	// 화면 하단에 보여지는 페이지의 갯수 
	private Criteria cri;				// 페이지 안에 페이징에 필요한 게시물들의 정보를 담거나 계산하는 클래스
	
	public void setCri(Criteria cri) {		
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public int getStartPage() {
		return startPage;
	}
	
	public int getEndPage() {
		return endPage;
	}
	
	public boolean isPrev() {
		return prev;
	}
	
	public boolean isNext() {
		return next;
	}
	
	public int getDisplayPageNum() {
		return disPlayPageNum;
	}
	
	public Criteria getCri() {
		return cri;
	}
	public void calcData() {
		
		// 현재 페이지를 기준으로 표시되는 페이지 번호의 마지막이 됨
		endPage = (int)(Math.ceil(cri.getPage() / (double)disPlayPageNum) * disPlayPageNum);
		// 만약 현재 페이지가 3페이지라면 (3/10 올림)*10을해서 나오는 10이 페이지에 표시되는 마지막 페이지번호가 된다
		
		// 현재 페이지를 기준으로 표시되는 페이지 번호의 처음이 됨
		startPage = (endPage - disPlayPageNum) + 1;
		// 출력되는 마지막 페이지 번호가 10이니까 (10-10)+1의 결과인 1이 출력되는 페이지의 첫번호가 된다
		
		
		/*
		 * 만약 전체 게시글이 100개일때 10개씩 보여주게 된다면 총 10개의 페이지가 존재하게 된다
		 * 그런데 20개씩 보여준다면 5개의 페이지가 존재하는것이 맞으나 endPage의 값은 20이 된다
		 * 그래서 만들 수 있는 총 페이지수를 계산하여 endPage의 값을 보정해 주면
		 * endPage의 번호는 만들어질 수 있는 총 페이지 수가 된다
		 * 만들어 질 수 있는 페이지의 최대값인 tempEndPage의 값과 endPage의 값을 비교하여
		 * endPage의 값을 보정한다
		 */
		
		// 전체 게시글의 수를 보여지는 게시글의 수로 나눠서 전체 페이지의 수를 구하는 변수
		int tempEndPage = (int)(Math.ceil(totalCount / (double)cri.getPerPageNum()));
		
		// 전체 페이지와 현재 페이지의 마지막을 비교하여 마지막값을 보정한다
		if (endPage > tempEndPage) { 	
			endPage = tempEndPage;
		}
		// startPage의 값이 1이면 false를 반환하여 이전버튼이 비활성화된다
		prev = startPage == 1 ? false : true;
		// endPage값과 보여지는 게시물의 수를 곱한 값과 총 게시물의 수를 비교하여 총 게시물보다 보여지는 값이 더 많을 경우
		// false를 반환하여 다음버튼이 비활성화되고 반대라면 true값으로 다음 버튼이 활성화된다
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
	}
	
	public String makeQuery(int page) {
		UriComponents uriComponents =
			UriComponentsBuilder.newInstance()
								.queryParam("page", page)
								.queryParam("perPageNum", cri.getPerPageNum())
								.build();
		
		return uriComponents.toUriString();
	}
	
	public String makeSearch(int page) {
		UriComponents uriComponents =
				UriComponentsBuilder.newInstance()
									.queryParam("page", page)
									.queryParam("perPageNum", cri.getPerPageNum())
									.queryParam("searchType", ((SearchCriteria)cri).getSearchType())
									.queryParam("keyword", encoding(((SearchCriteria)cri).getKeyword()))
									.build();
		return uriComponents.toUriString();
	}
	
	public String encoding(String keyword) {
		if (keyword == null || keyword.trim().length() == 0) {
			return "";
		}
		try {
			return URLEncoder.encode(keyword, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
