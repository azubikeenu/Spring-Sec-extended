package com.azubike.ellipsis.api.response.dos;

import java.util.List;

import com.azubike.ellipsis.entity.HexColor;

public class HexColorsApiPaginationResponse {
	private List<HexColor> hexColors;
	private int size ;
	private int page ;
	private int totalPages ;
	public List<HexColor> getHexColors() {
		return hexColors;
	}
	public void setHexColors(List<HexColor> hexColors) {
		this.hexColors = hexColors;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	

}
