package com.azubike.ellipsis.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.azubike.ellipsis.entity.JpaCustomer;

@Component
public class JpaCustomerSafeDao {
	@Autowired
	private EntityManager entityManager;

	public List<JpaCustomer> findByGender(String gender) {
		final String QUERY = "FROM JpaCustomer WHERE gender = :gender";
		return entityManager.createQuery(QUERY, JpaCustomer.class).setParameter("gender", gender).getResultList();
	}

}
