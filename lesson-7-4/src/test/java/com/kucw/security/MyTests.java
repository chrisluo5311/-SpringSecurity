package com.kucw.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MyTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testWelcome_noCsrfToken() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/welcome")
                .with(httpBasic("test1", "111"));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(403));
    }

    @Test
    public void testWelcome_withCsrfToken() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/welcome")
                .with(httpBasic("test1", "111"))
                .with(csrf());//在請求時戴上X-XSRF-TOKEN

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }

    //針對公開api
    @Test
    public void testRegister_noCsrfToken() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/register");

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(200));
    }
}