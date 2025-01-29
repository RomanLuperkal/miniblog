package org.blog.miniblog.controller;

import lombok.SneakyThrows;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.TestPropertySource;
import org.testconfiguration.WebConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ActiveProfiles("test")
@SpringJUnitWebConfig (classes = {WebConfiguration.class})
@TestPropertySource("classpath:application-test.properties")
public class UserControllerTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }

    @Test
    @SneakyThrows
    public void getLoginTest() {
        mockMvc.perform(get("/login"))

                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("login"));
    }

    @SneakyThrows
    @Test
    public void postLoginSuccessTest() {
        mockMvc.perform(post("/login")
                        .param("login", "testLogin")
                        .param("password", "testPwd"))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts"));
    }

    @Test
    @SneakyThrows
    public void postLoginTestWhenInvalidPassword() {
        mockMvc.perform(post("/login")
                        .param("login", "testLogin")
                        .param("password", "asfasff"))

                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("login"));
    }

    @Test
    @SneakyThrows
    public void getRegisterTest() {
        mockMvc.perform(get("/register"))

                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("register"));
    }

    @Test
    @SneakyThrows
    public void postRegisterTestSuccess() {
        Connection connection = dataSource.getConnection();
        String login = "newLogin";
        String pwd = "pwd";
        String firstName = "Ivan";
        String lastName = "Ivanov";
        int actualCountLogin = 0;

        mockMvc.perform(post("/register")
                        .param("login", login)
                        .param("pwd", pwd)
                        .param("firstName", firstName)
                        .param("lastName", lastName))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
        try(Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery("select count(*) from usr where login = 'newLogin'");
            while (resultSet.next()) {
                actualCountLogin = resultSet.getInt(1);
            }
        }
        assertEquals(actualCountLogin, 1);
    }

    @Test
    @SneakyThrows
    public void postRegisterTestWhenLoginExists() {
        Connection connection = dataSource.getConnection();
        String login = "testLogin";
        String pwd = "pwd";
        String firstName = "Ivan";
        String lastName = "Ivanov";
        int actualCountLogin = 0;
        int expectedCountLogin = 0;
        try(Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery("select count(*) from usr where login = 'testLogin'");
            while (resultSet.next()) {
                expectedCountLogin = resultSet.getInt(1);
            }

            mockMvc.perform(post("/register")
                            .param("login", login)
                            .param("pwd", pwd)
                            .param("firstName", firstName)
                            .param("lastName", lastName))

                    .andExpect(status().isOk())
                    .andExpect(view().name("register"));
            resultSet = st.executeQuery("select count(*) from usr where login = 'testLogin'");
            while (resultSet.next()) {
                actualCountLogin = resultSet.getInt(1);
            }
        }
        assertEquals(actualCountLogin, expectedCountLogin);
    }

    @Test
    @SneakyThrows
    public void testLogout() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("user", "testUser");

        mockMvc.perform(get("/logout").session(session))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
        assertTrue(session.isInvalid());
    }

}
