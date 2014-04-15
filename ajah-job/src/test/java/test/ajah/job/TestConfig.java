/*
 * PROPRIETARY and CONFIDENTIAL
 * 
 * Copyright 2012 Magellan Distribution Corporation
 * 
 * All rights reserved.
 */
package test.ajah.job;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ajah.job.execute.JobDispatcher;
import com.ajah.job.execute.SimpleJobDispatcher;
import com.jolbox.bonecp.BoneCPDataSource;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Configuration
@ComponentScan(basePackages = { "com.ajah.job" })
@SuppressWarnings("static-method")
public class TestConfig {

	@Bean
	public DataSource dataSource() {
		final BoneCPDataSource dataSource = new BoneCPDataSource();

		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/job");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		dataSource.setIdleConnectionTestPeriodInMinutes(1);
		dataSource.setIdleMaxAgeInMinutes(10);
		dataSource.setMaxConnectionsPerPartition(5);
		dataSource.setMinConnectionsPerPartition(1);
		dataSource.setPartitionCount(1);
		dataSource.setAcquireIncrement(1);
		dataSource.setStatementsCacheSize(100);
		dataSource.setReleaseHelperThreads(3);

		return dataSource;
	}

	@Bean
	JobDispatcher jobDispatcher() {
		return new SimpleJobDispatcher();
	}

}
