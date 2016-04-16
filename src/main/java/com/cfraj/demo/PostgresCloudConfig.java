package com.cfraj.demo;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.common.RelationalServiceInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Profile("cloud")
@Configuration
public class PostgresCloudConfig extends AbstractCloudConfig {

	 public static Logger logger = LoggerFactory.getLogger(PostgresCloudConfig.class);
	 

	@Bean(name = "secondaryDataSource")
	public DataSource gmysqldb01DataSource() {
	 	CloudFactory cloudFactory = new CloudFactory();
    	Cloud cloud = cloudFactory.getCloud();
    	List<ServiceInfo> serviceInfos = cloud.getServiceInfos();
    	for (ServiceInfo serviceInfo : serviceInfos) {
    	    if (serviceInfo instanceof RelationalServiceInfo) {
    	        System.out.println("&&&&&&&&&&&&&&&&&& :"+((RelationalServiceInfo) serviceInfo).getJdbcUrl());
    	        logger.info("Logging Postgress&&&&&&&&&&&&&&&&&& :"+((RelationalServiceInfo) serviceInfo).getJdbcUrl());
    	    }else{
    	    	System.out.println("****************** :"+ serviceInfo.getId());
    	    	logger.info("Logging****************** :"+ serviceInfo.toString());
    	    }
    	}
    	DataSource postgresDB = 
    		    cloud.getServiceConnector("postgresql-db", DataSource.class, null);
		 return postgresDB;
	}
	
	
    @Bean(name = "jdbcSecondaryTemplate")
    public JdbcTemplate jdbcPrimaryTemplate(@Qualifier(value = "secondaryDataSource") DataSource secondaryDataSource) {
        return new JdbcTemplate(secondaryDataSource);
    }

    
    /*
     CREATE TABLE COMPANY(
   ID INT PRIMARY KEY     NOT NULL,
   NAME           TEXT    NOT NULL,
   AGE            INT     NOT NULL,
   ADDRESS        CHAR(50),
   SALARY         REAL
);
insert into COMPANY values (1,'RANA','40','SLC',5000)
     */
}


