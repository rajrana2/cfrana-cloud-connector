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
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Profile("cloud")
@Configuration
public class MySQLCloudConfig extends AbstractCloudConfig {

	 public static Logger logger = LoggerFactory.getLogger(MySQLCloudConfig.class);
    @Primary 
	@Bean(name = "primaryDataSource")
	public DataSource myqlDataSource() {
	 	CloudFactory cloudFactory = new CloudFactory();
    	Cloud cloud = cloudFactory.getCloud();
    	List<ServiceInfo> serviceInfos = cloud.getServiceInfos();
    	for (ServiceInfo serviceInfo : serviceInfos) {
    	    if (serviceInfo instanceof RelationalServiceInfo) {
    	        System.out.println("&&&&&&&&&&&&&&&&&& :"+((RelationalServiceInfo) serviceInfo).getJdbcUrl());
    	        logger.info("Logging&&&&&&&&&&&&&&&&&& :"+((RelationalServiceInfo) serviceInfo).getJdbcUrl());
    	    }else{
    	    	System.out.println("****************** :"+ serviceInfo.getId());
    	    	logger.info("Logging****************** :"+ serviceInfo.toString());
    	    }
    	}
    	DataSource mysql = 
    		    cloud.getServiceConnector("mysqldb-raj", DataSource.class, null);
		 return mysql;
	}
	
    @Primary
    @Bean(name = "jdbcPrimaryTemplate")
    public JdbcTemplate jdbcSecondaryTemplate(@Qualifier(value = "primaryDataSource") DataSource primaryDataSource) {
        return new JdbcTemplate(primaryDataSource);
    }

	

}


