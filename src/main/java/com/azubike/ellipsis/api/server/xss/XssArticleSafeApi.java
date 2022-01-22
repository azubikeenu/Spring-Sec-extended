package com.azubike.ellipsis.api.server.xss;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.api.response.xss.XssArticleResponse;
import com.azubike.ellipsis.entity.XssArticle;
import com.azubike.ellipsis.repository.XssArticleRepository;

@RestController
@RequestMapping("/api/xss/safe/v1/article")
@CrossOrigin(origins = "*")
public class XssArticleSafeApi {
	@Autowired
	private XssArticleRepository xssArticleRepository;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public String create(@RequestBody XssArticle article) {
		XssArticle savedArticle = xssArticleRepository.save(article);
		return "Article saved as " + savedArticle;

	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public XssArticleResponse search(@RequestParam(name = "query", required = true) String query) {
		List<XssArticle> articles = xssArticleRepository.findByArticleContainsIgnoreCase(query);
		XssArticleResponse response = new XssArticleResponse();
		response.setResult(articles);
		if (articles.size() < 100)
			response.setQueryCount("Seach result for " + query + "retuns " + articles.size());
		else
			response.setQueryCount("Seach result for " + query + " is too long please narrow your search");
		return response;
	}

}
