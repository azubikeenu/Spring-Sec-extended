package com.azubike.ellipsis.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.azubike.ellipsis.entity.JdbcCustomer;

@Repository
public class JdbcCustomerSafeRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JdbcCustomer findCustomerByEmail(String email) {
		final String QUERY = "SELECT * FROM jdbc_customer WHERE email = ? AND email is not null ";
		return jdbcTemplate.queryForObject(QUERY, this::maptoCustomer, email);
	}

	public List<JdbcCustomer> findCustomersByGender(String genderCode) {
		final String QUERY = "SELECT * FROM jdbc_customer WHERE gender = ? ";
		return jdbcTemplate.query(QUERY, this::maptoCustomer, genderCode);
	}

	public void createNewCustomer(JdbcCustomer customer) {
		final String QUERY = "INSERT INTO jdbc_customer (full_name,email,birth_date,gender) VALUES (:fullName,:email,:birthDate,:gender)";
		MapSqlParameterSource mappedParameterSouce = new MapSqlParameterSource()
				.addValue("fullName", customer.getFullName()).addValue("email", customer.getEmail())
				.addValue("birthDate", customer.getBirthDate()).addValue("gender", customer.getGender());
		namedParameterJdbcTemplate.update(QUERY, mappedParameterSouce);
	}

	public void updateCustomerFullName(int customerId, String newFullName) {
		final String STORED_PROCEDURE = "CALL update_jdbc_customer(:customerId , :newFullName)";
		MapSqlParameterSource mappedParameterSouce = new MapSqlParameterSource().addValue("fullName", newFullName)
				.addValue("customerId", customerId);
		namedParameterJdbcTemplate.update(STORED_PROCEDURE, mappedParameterSouce);
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
