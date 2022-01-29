package com.azubike.ellipsis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.azubike.ellipsis.entity.BasicAclUri;

@Repository
public interface BasicAclUriRepository extends CrudRepository<BasicAclUri, Integer> {

}
