package com.azubike.ellipsis.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.azubike.ellipsis.entity.XssArticle;

@Repository
public interface XssArticleRepository extends CrudRepository<XssArticle, Integer> {
	List<XssArticle> findByArticleContainsIgnoreCase(String article);
}
