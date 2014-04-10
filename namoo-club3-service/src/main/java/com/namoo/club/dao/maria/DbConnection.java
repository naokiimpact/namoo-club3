package com.namoo.club.dao.maria;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class DbConnection {
	//
	private static DbConnection instance = new DbConnection();
	private String driver;
	private String username;
	private String password;
	private String url;
	
	private DataSource dataSource;
	
	private DbConnection() {
		//
		loadProperties();
		
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setUrl(url);
		
		dataSource = ds;	// 업캐스팅
	}
	
	private void loadProperties() {
		Properties prop = new Properties();
		InputStream is = this.getClass().getResourceAsStream("jdbc.properties");
		
		try {
			prop.load(is);
			
			this.driver		= prop.getProperty("database.driver");
			this.username	= prop.getProperty("database.username");
			this.password	= prop.getProperty("database.password");
			this.url		= prop.getProperty("database.url");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		//
		return instance.dataSource.getConnection();
	}
}
