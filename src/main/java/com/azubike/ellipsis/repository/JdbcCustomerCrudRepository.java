package com.azubike.ellipsis.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azubike.ellipsis.entity.JdbcCustomer;

@Repository
public interface JdbcCustomerCrudRepository extends CrudRepository<JdbcCustomer, Integer> {
	List<JdbcCustomer> findByEmail(String email);

	List<JdbcCustomer> findByGender(String genderCode);

	@Query("CALL update_jdbc_customer(:customerId , :newFullName)")
	void updateCustomerFullName(@Param("customerId") int customerId, @Param("newFullName") String newFullName);

}
