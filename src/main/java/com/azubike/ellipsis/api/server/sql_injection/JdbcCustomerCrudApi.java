package com.azubike.ellipsis.api.server.sql_injection;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.api.request.sqlInjection.JdbcCustomerPatchRequest;
import com.azubike.ellipsis.entity.JdbcCustomer;
import com.azubike.ellipsis.repository.JdbcCustomerCrudRepository;

@RestController
@RequestMapping("api/sqlinjection/crud/v1")
@Validated
public class JdbcCustomerCrudApi {

	@Autowired
	JdbcCustomerCrudRepository repository;

	@GetMapping("/customer/{email}")
	public JdbcCustomer findCustomerByEmail(@PathVariable(required = true, name = "email") String email) {
		List<JdbcCustomer> queryResult = repository.findByEmail(email);
		return (queryResult != null && !queryResult.isEmpty()) ? queryResult.get(0) : null;
	}

	@GetMapping("/customer")
	public List<JdbcCustomer> findCustomerByGender(
			@Pattern(regexp = "^[MF]$", message = "Invalid gender") @RequestParam(required = true, name = "genderCode") String genderCode) {
		return repository.findByGender(genderCode);
	}

	@PostMapping("/customer")
	public void createCustomer(@RequestBody(required = true) @Valid JdbcCustomer customer) {
		// repository.save(customer);
	}

	@PatchMapping("/customer/{customerId}")
	public void updateCustomerFullName(@RequestBody(required = true) JdbcCustomerPatchRequest request,
			@PathVariable(required = true, name = "customerId") int customerId) {
		// repository.updateCustomerFullName(customerId, request.getNewFullName());
	}
}
