package com.cglia.liquibase.config;

import com.cglia.liquibase.entiry.Customer;

import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessorForBrazil implements ItemProcessor<Customer,Customer> {

	@Override
	public Customer process(Customer customer) throws Exception {
		 if(customer.getCountry().equalsIgnoreCase("brazil")) {
	            return customer;
	        }else{
	            return null;
	        }
	}

}
