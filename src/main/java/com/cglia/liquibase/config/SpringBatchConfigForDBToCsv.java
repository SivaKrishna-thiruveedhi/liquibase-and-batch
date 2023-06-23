package com.cglia.liquibase.config;

import java.sql.ResultSet;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.cglia.liquibase.entiry.Customer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfigForDBToCsv {
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory;
	@Autowired
    private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private DataSource dataSource;
	
	
	/****************************************************************************************************************************/
	// DB To .csv file
	
	
	@Bean
	public JdbcCursorItemReader<Customer> newReader() {
		JdbcCursorItemReader<Customer> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(dataSource);
		reader.setSql("select * from customer");
		reader.setRowMapper(new RowMapper<Customer>() {
			
			@Override
			public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Customer customer = new Customer();
				customer.setId(rs.getInt(1));
				customer.setFirstName(rs.getString(2));
				customer.setLastName(rs.getString(3));
				customer.setEmail(rs.getString(4));
				customer.setGender(rs.getString(5));
				customer.setContactNo(rs.getString(6));
				customer.setCountry(rs.getString(7));
				customer.setDob(rs.getString(8));
				return customer;
			}
		});
		return reader;
	}
	
	@Bean
	public FlatFileItemWriter<Customer> newWriter() {
		
		FlatFileItemWriter<Customer> writer = new FlatFileItemWriter<>();
		writer.setResource(new FileSystemResource("F://DbToCsv/cdv_output.csv"));
		DelimitedLineAggregator<Customer> aggregator = new DelimitedLineAggregator<>();
		BeanWrapperFieldExtractor<Customer> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] {"id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob"});
		aggregator.setFieldExtractor(fieldExtractor);
		writer.setLineAggregator(aggregator);
		return writer;
	}
		
	@Bean
	public Step executeStep() {
		return stepBuilderFactory.get("executeStep").<Customer, Customer>chunk(10)
				.reader(newReader()).processor(new CustomerProcessorForCsv())
				.writer(newWriter()).build();
	}
	
	@Bean
	@Qualifier("convert")
	public Job processJob() {
		return jobBuilderFactory.get("/convert/csv").incrementer(new RunIdIncrementer()).flow(executeStep()).end().build();
	}
}





















