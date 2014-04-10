package com.namoo.club.dao;

import java.io.IOException;
import java.io.InputStream;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.namoo.club.dao.factory.DaoFactory;
import com.namoo.club.dao.factory.DaoFactory.DbType;

public class UserDaoTest {

	UserDao userDao;
	IDatabaseTester databaseTester; 
	
	@Before
	public void setUp() throws Exception {
		
		userDao = DaoFactory.createFactory(DbType.MariaDB).getUserDaoMaria();
		prepareDatabaseTester();
		databaseTester.setSetUpOperation(DatabaseOperation.REFRESH);
		// DatabaseOperation.REFRESH 기존에 같은 데이터가 있으면 정의된 데이터로 교체 
		// DatabaseOperation.INSERT 그냥 인서트 ( 같은 데이터가 있으면 듀플리케이트 에러 발생 )
		// DatabaseOperation.CLEAN_INSERT 테이블의 모든 로우를 삭제하고 정의된 데이터 인서트
		databaseTester.onSetup();
	}
	
	@After
	public void tearDown() throws Exception {
		databaseTester.setTearDownOperation(DatabaseOperation.DELETE);
		databaseTester.onTearDown();	
	}
	
	@Test
	public void test() {
		userDao.readUser("email");
	}
	
	private void prepareDatabaseTester() throws DataSetException, IOException {
		//
		String driverClass = "org.mariadb.jdbc.Driver";
		String connectionUrl = "jdbc:mariadb://localhost:3306/namooclubdb";
		String username = "namoouser";
		String password = "namoouser";
		
		databaseTester = new JdbcDatabaseTester(driverClass, connectionUrl, username, password);
		databaseTester.setDataSet(readDataset());
		
	}
	
	private IDataSet readDataset() throws DataSetException, IOException {
		//
		InputStream is = this.getClass().getResourceAsStream("dataset.xml");
		IDataSet dataset = new FlatXmlDataSet(is);
		return dataset;
	}
}
