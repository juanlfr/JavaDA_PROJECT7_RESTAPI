package com.nnk.springboot;

import com.nnk.springboot.controllers.LoginController;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebAppSecurityTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private LoginController controller;

    @BeforeEach
    public void setup() {
        mock = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void contextLoads()  {
        assertThat(controller).isNotNull();
    }

    @Test
    public void shouldReturnDefaultMessageTest() throws Exception {
        mock.perform(get("/login")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void userLoginTest () throws Exception {
        mock.perform(formLogin("/login").user("juan").password("juan")).andExpect(authenticated());
    }
    @Test
    public void userLoginFailTest() throws Exception {
        mock.perform(formLogin("/login").user("admin").password("user")).andExpect(unauthenticated());
    }

}
