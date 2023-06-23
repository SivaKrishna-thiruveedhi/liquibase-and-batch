package com.cglia.liquibase.config;

import com.cglia.liquibase.entiry.Customer;

import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessorForCsv implements ItemProcessor<Customer,Customer> {

	@Override
	public Customer process(Customer customer) throws Exception {
		
		final String country = "\t"+customer.getCountry().toUpperCase()+"\t";
		customer.setCountry(country);
		return customer;
	}

}









//return new Customer(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getGender(), customer.getContactNo(), country, customer.getDob());