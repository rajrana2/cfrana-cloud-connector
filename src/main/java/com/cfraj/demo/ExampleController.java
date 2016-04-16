package com.cfraj.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	    @Autowired
	    @Qualifier("jdbcPrimaryTemplate")
	    public JdbcTemplate jdbcPrinaryTemplate;

	    @Autowired
	    @Qualifier("jdbcSecondaryTemplate")
	    public JdbcTemplate jdbcSecondaryTemplate;



	@RequestMapping("/")
	public String hello() {
		String primary = jdbcPrinaryTemplate.queryForObject("select first_name from employees where emp_no = 1", String.class);
		String secondary=jdbcSecondaryTemplate.queryForObject("SELECT address FROM COMPANY WHERE id=1", String.class);
		return primary+"-"+secondary;
	}



}
