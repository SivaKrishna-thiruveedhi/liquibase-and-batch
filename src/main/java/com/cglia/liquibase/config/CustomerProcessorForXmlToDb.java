package com.cglia.liquibase.config;

import com.cglia.liquibase.entiry.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessorForXmlToDb implements ItemProcessor<Customer, Customer>{

	@Override
	public Customer process(Customer customer) throws Exception {
		return customer;
	}
}