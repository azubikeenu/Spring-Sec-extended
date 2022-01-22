package com.azubike.ellipsis.entity;

import org.springframework.data.annotation.Id;

public class XssArticle {

	@Id
	private int articleId;
	private String article;

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

}
