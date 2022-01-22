package com.azubike.ellipsis.repository;

import java.util.List;

import com.azubike.ellipsis.entity.JpaCustomer;

//@Repository
public interface JpaCustomerCrudRepository
//extends CrudRepository<JpaCustomer, Integer> 
{
	List<JpaCustomer> findByEmail(String email);

}
