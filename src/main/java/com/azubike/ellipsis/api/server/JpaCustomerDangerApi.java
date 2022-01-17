package com.azubike.ellipsis.api.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.azubike.ellipsis.entity.JpaCustomer;
import com.azubike.ellipsis.repository.JpaCustomerCrudRepository;
import com.azubike.ellipsis.repository.JpaCustomerDangerDao;

@RestController
@RequestMapping("/api/sqlinjection/danger/v2")
public class JpaCustomerDangerApi {
	@Autowired
	private JpaCustomerCrudRepository repository;
	@Autowired
	private JpaCustomerDangerDao dao;

	@GetMapping("/customer/{email}")
	public JpaCustomer findCustomerByEmail(@PathVariable(name = "email") String email) {
		List<JpaCustomer> customer = repository.findByEmail(email);
		return (!customer.isEmpty() && customer != null) ? customer.get(0) : null;
	}

	@GetMapping("/customer")
	public List<JpaCustomer> findCustomersByGender(
			@RequestParam(required = true, name = "genderCode") String genderCode) {
		return dao.findByGender(genderCode);
	}

}
