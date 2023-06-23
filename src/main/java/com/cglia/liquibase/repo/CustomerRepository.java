package com.cglia.liquibase.repo;

import com.cglia.liquibase.entiry.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
