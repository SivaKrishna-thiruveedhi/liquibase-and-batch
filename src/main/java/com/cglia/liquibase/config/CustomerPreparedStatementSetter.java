package com.cglia.liquibase.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.cglia.liquibase.entiry.Customer;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

public class CustomerPreparedStatementSetter implements ItemPreparedStatementSetter<Customer> {

	@Override
	public void setValues(Customer customer, PreparedStatement ps) throws SQLException {
		ps.setInt(1, customer.getId());
		ps.setString(2, customer.getFirstName());
		ps.setString(3, customer.getLastName());
		ps.setString(4, customer.getEmail());
		ps.setString(5, customer.getGender());
		ps.setString(6, customer.getContactNo());
		ps.setString(7, customer.getCountry());
		ps.setString(8, customer.getDob());
	}

}