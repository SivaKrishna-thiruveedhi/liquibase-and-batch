package com.cglia.liquibase.config;
import com.cglia.liquibase.entiry.Customer;

import com.cglia.liquibase.repo.CustomerRepository;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
@Component
public class SpringBatchConfigForCSVToDb {
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory;
	@Autowired
    private StepBuilderFactory stepBuilderFactory;
	@Autowired
    private CustomerRepository customerRepository;


    /******************************************************************************************************************************/
	// .csv file To DB
	
	
	@Bean
    public FlatFileItemReader<Customer> reader() {
        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/customers.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<Customer> lineMapper() {
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "firstName", "lastName", "email", "gender", "contactNo", "country", "dob");

        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;

    }

    @Bean
    public CustomerProcessorForUs processor() {
        return new CustomerProcessorForUs();
    }

    @Bean
    public RepositoryItemWriter<Customer> writer() {
        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<>();
        writer.setRepository(customerRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step stepForUS() {
        return stepBuilderFactory.get("csv-step").<Customer, Customer>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @Qualifier("us")
    public Job runJob() {
        return jobBuilderFactory.get("importCustomers/us")
                .flow(stepForUS()).end().build();

    }
    
    @Bean
    public CustomerProcessorForChina chinaProcessor() {
        return new CustomerProcessorForChina();
    }
    
    @Bean
    public Step stepForChina() {
        return stepBuilderFactory.get("csv-step").<Customer, Customer>chunk(10)
                .reader(reader())
                .processor(chinaProcessor())
                .writer(writer())
                .build();
    }
    
    @Bean
    @Qualifier("china")
    public Job runJobByChina() {
        return jobBuilderFactory.get("importCustomers/china")
                .flow(stepForChina()).end().build();

    }
    
    
    
    
    @Bean
    public CustomerProcessorForBrazil brazilProcessor() {
        return new CustomerProcessorForBrazil();
    }
    
    @Bean
    public Step stepForBrazil() {
        return stepBuilderFactory.get("csv-step").<Customer, Customer>chunk(10)
                .reader(reader())
                .processor(brazilProcessor())
                .writer(writer())
                .build();
    }
    
    @Bean
    @Qualifier("brazil")
    public Job runJobByBrazil() {
        return jobBuilderFactory.get("importCustomers/brazil")
                .flow(stepForBrazil())
                .end().build();

    }
}


















