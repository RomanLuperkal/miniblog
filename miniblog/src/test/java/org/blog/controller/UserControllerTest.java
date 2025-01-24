package org.blog.controller;

import lombok.SneakyThrows;
import org.blog.configuration.H2Configuration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@ActiveProfiles("test")
@SpringJUnitConfig(classes = H2Configuration.class)
public class UserControllerTest {

    @Autowired
    private DataSource dataSource;

    @Test
    @SneakyThrows
    public void test() {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from usr");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("login"));
        }
    }

    @Test
    @SneakyThrows
    public void test2() {
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from usr");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("pwd"));
        }
    }
}
