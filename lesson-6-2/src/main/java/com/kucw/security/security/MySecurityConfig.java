package com.kucw.security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails userTest1 = User
                .withUsername("test1")
                .password("{noop}111")
                .roles("ADMIN", "USER")
                .build();

        UserDetails userTest2 = User
                .withUsername("test2")
                .password("{noop}222")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userTest1, userTest2);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                //設定 Session 的創建機制
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
                //設定 CSRF 保護 - 前端請求POST、PUT、DELETE的api時，需要帶上 X-XSRF-TOKEN，才能通過CSRF的檢查
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())//在cookie裡面回傳token給前端
                        .csrfTokenRequestHandler(createCsrfHandler())
                        .ignoringRequestMatchers("/register","/userLogin")//針對特定的POST、PUT、DELETE的api去關閉他們的CSRF檢查
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())

                .authorizeHttpRequests(request -> request
                        .requestMatchers("/register","userLogin").permitAll()
                        .anyRequest().authenticated()
                )

                .build();
    }

    private CsrfTokenRequestAttributeHandler createCsrfHandler() {
        CsrfTokenRequestAttributeHandler csrfHandler = new CsrfTokenRequestAttributeHandler();
        csrfHandler.setCsrfRequestAttributeName(null);//request attribute的名字(預設:_csrf)

        return csrfHandler;
    }
}
