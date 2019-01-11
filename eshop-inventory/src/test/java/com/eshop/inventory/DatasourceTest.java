package com.eshop.inventory;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zaxxer.hikari.HikariDataSource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DatasourceTest {

	@Autowired
	private HikariDataSource dataSource;
	
	@Test
	public void test01() throws SQLException {
		System.out.println(dataSource);
		System.out.println(dataSource.getConnection());
	}
	
}
