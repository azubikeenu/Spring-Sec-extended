package com.azubike.ellipsis.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.azubike.ellipsis.entity.JdbcCustomer;

@Repository
public class JdbcCustomerDangerRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcCustomer findCustomerByEmail(String email) {
		final String QUERY = "SELECT * FROM jdbc_customer WHERE email = '" + email + "' AND email is not null ";
		return jdbcTemplate.queryForObject(QUERY, this::maptoCustomer);
	}

	public List<JdbcCustomer> findCustomersByGender(String gender) {
		final String QUERY = "SELECT * FROM jdbc_customer WHERE gender = '" + gender + "'";
		return jdbcTemplate.query(QUERY, this::maptoCustomer);
	}

	public void createNewCustomer(JdbcCustomer customer) {
		final String QUERY = "INSERT INTO jdbc_customer (full_name,email,birth_date,gender) VALUES (" + "'"
				+ customer.getFullName() + " ', " + "'" + customer.getEmail() + " ', " + "'" + customer.getBirthDate()
				+ " ', " + "'" + customer.getGender() + " ' )";
		jdbcTemplate.execute(QUERY);
	}

	public void updateCustomerFullName(int customerId, String newFullName) {
		final String STORED_PROCEDURE = "CALL update_jdbc_customer('" + customerId + "' , '" + newFullName + "')";
		jdbcTemplate.execute(STORED_PROCEDURE);
	}

	private JdbcCustomer maptoCustomer(ResultSet rs, int rowNumber) throws SQLException {
		JdbcCustomer customer = new JdbcCustomer();
		Optional.ofNullable(rs.getDate("birth_date")).ifPresent(date -> customer.setBirthDate(date.toLocalDate()));
		customer.setEmail(rs.getString("email"));
		customer.setFullName(rs.getString("full_name"));
		customer.setGender(rs.getString("gender"));
		customer.setCustomerId(rs.getInt("customer_id"));
		return customer;

	}
}
