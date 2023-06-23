package com.cglia.liquibase.config;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.cglia.liquibase.entiry.Customer;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.oxm.xstream.XStreamMarshaller;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfigForXmlAndDb {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSource dataSource;
	
	
	/***********************************************************************************************************************************/
	// XML To DB
	
	@Bean
	public StaxEventItemReader<Customer> xmlReader(){
		StaxEventItemReader<Customer> reader = new StaxEventItemReader<>();
		reader.setResource(new ClassPathResource("customer.xml"));
		reader.setFragmentRootElementName("customer");
		
		Map<String,String> aliasesMap =new HashMap<>();
		aliasesMap.put("customer", "com.cglia.liquibase.entiry.Customer");
		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(aliasesMap);
		marshaller.getXStream().allowTypes(new Class[]{Customer.class});
		
		reader.setUnmarshaller(marshaller);
		return reader;
	}
	@Bean
	public JdbcBatchItemWriter<Customer> xmlWriter(){
		JdbcBatchItemWriter<Customer> writer = new JdbcBatchItemWriter<>();
		writer.setDataSource(dataSource);
		writer.setSql("INSERT INTO Customer(id,firstName,lastName,email,gender,contactNo,country,dob) VALUES(?,?,?,?,?,?,?,?)");
		writer.setItemPreparedStatementSetter(new CustomerPreparedStatementSetter());
		return writer;
	}
	
	@Bean
	public Step stepForXmlToDb(){
		return stepBuilderFactory.get("step1").<Customer,Customer>chunk(100)
				.reader(xmlReader())
				.processor(new CustomerProcessorForXmlToDb())
				.writer(xmlWriter())
				.build();
	}

	@Bean
	@Qualifier("importFromXmlFile")
	public Job importCustomerFromXml(){
		return jobBuilderFactory.get("/importFromXml").incrementer(new RunIdIncrementer()).flow(stepForXmlToDb()).end().build();
	}
	
	
	/****************************************************************************************************************************************/
	// DB To XML
	
	@Bean
	public StaxEventItemWriter<Customer> dbToXmlwriter(){
		StaxEventItemWriter<Customer> writer = new StaxEventItemWriter<>();
		writer.setResource(new FileSystemResource("F://DbToXml/customer.xml"));
		
		Map<String,String> aliasesMap =new HashMap<>();
		aliasesMap.put("customer", "com.cglia.liquibase.entiry.Customer");
		XStreamMarshaller marshaller = new XStreamMarshaller();
		marshaller.setAliases(aliasesMap);
		writer.setMarshaller(marshaller);
		writer.setRootTagName("customers");
		writer.setOverwriteOutput(true);
		return writer;
	}
	
	@Bean
	public JdbcCursorItemReader<Customer> dbToXmlReader() {
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
	public Step stepForDbToXml(){
		return stepBuilderFactory.get("step1").<Customer,Customer>chunk(100)
				.reader(dbToXmlReader())
				.processor(new CustomerProcessorForXmlToDb())
				.writer(dbToXmlwriter())
				.build();
	}

	@Bean
	@Qualifier("exportToXmlFile")
	public Job exportCustomerToXml(){
		return jobBuilderFactory.get("/exportToXml").incrementer(new RunIdIncrementer()).flow(stepForDbToXml()).end().build();
	}
	
	
	
}










