package com.kucw.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MyTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCors() throws Exception {
        //模擬一個 preflight 請求
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .options("/hello")
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", "http://www.example.com");


        mockMvc.perform(requestBuilder)
                //檢查是否有返回CORS的header
                .andExpect(header().exists("Access-Control-Allow-Origin"))//是否在header中存在Access-Control-Allow-Origin
                .andExpect(header().string("Access-Control-Allow-Origin", "*"))//檢查值是否為*
                .andExpect(header().exists("Access-Control-Allow-Methods"))
                .andExpect(header().string("Access-Control-Allow-Methods", "GET"))
                .andExpect(status().is(200));
    }
}
