package com.cglia.liquibase.controller;


import org.springframework.batch.core.Job;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
    private JobLauncher jobLauncher;
	@Autowired
	@Qualifier("us")
	private Job job;
	@Autowired
	@Qualifier("china")
	private Job jobChina;
	@Autowired
	@Qualifier("brazil")
	private Job jobBrazil;
	@Autowired
	@Qualifier("convert")
	private Job jobConvert;
	@Autowired
	@Qualifier("importFromXmlFile")
	private Job xmlConvert;
	@Autowired
	@Qualifier("exportToXmlFile")
	private Job dbToXmlConvert;
	
	
	private String start = "startAt";
	private String exception = "Something went wrong, Please try later !!.... ";
	
	
    @PostMapping("/importCustomers/us")
    public String importCsvToDBJob() {
    	
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong(start, System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
            return "Customer data got inserted based on the country 'United States'";
        } catch (Exception e) {
            e.printStackTrace();
            return exception;
        }
    }
    
    @PostMapping("/importCustomers/china")
    public String importChinaCustomers() {
    	
    	
    	JobParameters jobParameter = new JobParametersBuilder()
                .addLong(start, System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(jobChina, jobParameter);
            return "Customer data got inserted based on the country 'China'";
        } catch (Exception e) {
            e.printStackTrace();
            return exception;
        }
    }
    
    @PostMapping("/importCustomers/brazil")
    public String importBrazilCustomers() {
    	
    	
    	JobParameters jobParameter = new JobParametersBuilder()
                .addLong(start, System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(jobBrazil, jobParameter);
            return "Customer data got inserted based on the country 'Brazil'";
        } catch (Exception e) {
            e.printStackTrace();
            return exception;
        }
    }

    @PostMapping("/convert")
    public String converToCsv() {
    	JobParameters jobParameter = new JobParametersBuilder()
                .addLong(start, System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(jobConvert, jobParameter);
            return "Customer data is converted into .csv file in the destination 'F://DbToCsv/cdv_output.csv'";
        } catch (Exception e) {
            e.printStackTrace();
            return exception;
        }
    } 
    
    @PostMapping("/importFromXml")
    public String convertXmlToDb() {
    	JobParameters jobParameter = new JobParametersBuilder()
                .addLong(start, System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(xmlConvert, jobParameter);
            return "Customer data is inserted into Database from xml file";
        } catch (Exception e) {
            e.printStackTrace();
            return exception;
        }
    }
    
    @PostMapping("/exportToXml")
    public String convertDbToXml() {
    	JobParameters jobParameter = new JobParametersBuilder()
                .addLong(start, System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(dbToXmlConvert, jobParameter);
            return "Customer data has loded as an xml file in the location 'F://DbToCsv/cdv_output.csv'";
        } catch (Exception e) {
            e.printStackTrace();
            return exception;
        }
    } 
    
}









