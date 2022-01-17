package com.azubike.ellipsis.repository;

import java.util.List;

import com.azubike.ellipsis.entity.JdbcCustomer;

//@Repository
public interface JdbcCustomerCrudRepository
// extends CrudRepository<JdbcCustomer, Integer>
{
	List<JdbcCustomer> findByEmail(String email);

	List<JdbcCustomer> findByGender(String genderCode);

//	@Query("CALL update_jdbc_customer(:customerId , :newFullName)")
//	void updateCustomerFullName(@Param("customerId") int customerId, @Param("newFullName") String newFullName);

}
