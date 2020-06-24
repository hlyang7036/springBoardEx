package com.javalec.boardEx.vo;


public class Criteria {
	
	private int page;			// 현재 페이지 번호
	private int perPageNum;		// 한 페이지당 출력되는 게시글의 갯수 
	private int rowStart;
	private int rowEnd;
	
	// 페이지 기본값 초기화 생성자
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
	}
	
	public void setPage(int page) {		// 현재 페이지가 0보다 작으면 1로 페이지값을 설정
		if (page <= 0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}
	
	public void setPerPageNum(int perPageNum) {		// 보여줄 게시글리스트의 수가 0보다 작거나 100을 넘어가면 보여줄 게시글리스트의 수를
		if (perPageNum <= 0 || perPageNum >100) {	// 10개로 설정한다 게시글리스트의 한계를 설정
			this.perPageNum = 10;
			return;
		}
		this.perPageNum = perPageNum;
	}
	
	public int getPage() {
		return page;
	}
	
	public int getPageStart() {					// 현재 페이지 게시글의 시작위치를 계산하는 메소드
		return (this.page-1) * perPageNum;		// 만약 1페이지면 페이지의 시작은 0부터가 되고 
	}											// 2페이지면 페이지의 시작은 10이 된다
	
	public int getPerPageNum() {
		return this.perPageNum;
	}
	
	public int getRowStart() {						// 페이지에서 시작되는 게시글의 처음을 구하는 메소드
		rowStart = ((page - 1) * perPageNum) +1;	// 만약 10페이지이면 (9*10)+1= 91로 91부터 시작된다 
		return rowStart;
	}
	
	public int getRowEnd() {
		rowEnd = rowStart + perPageNum -1;			// 페이지에서 게시글의 마지막을 구하는 메소드
		return rowEnd;								// 만약 91부터 시작되면 +10 -1 = 100으로 그 페이지의 마지막 게시글의 번호는 100이 된다
	}
	
	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + ", rowStart=" + rowStart + ", rowEnd=" + rowEnd
				+ "]";
	}
	
}
