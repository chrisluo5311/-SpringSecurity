package com.kucw.security;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Lesson92ApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    public void constructUri() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.baeldung.com").path("/junit-5").build();
        System.out.println(uriComponents.toUriString());
        assertEquals("/junit-5", uriComponents.getPath());

        UriComponentsBuilder uriComponents2 = UriComponentsBuilder.fromOriginHeader("http://www.baeldung.com/junit-5");

        System.out.println(uriComponents2.toUriString());
    }

    @Test
    public void constructUriEncoded() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.baeldung.com").path("/junit 5").build().encode();

        assertEquals("/junit%205", uriComponents.toUriString());
    }

    @Test
    public void constructUriFromTemplate() {
        // the keyword in the buildAndExpand will replace the keywords in the brackets {}
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.baeldung.com").path("/{article-name}")
                .buildAndExpand("hello-world");

        assertEquals("/junit-5", uriComponents.toUriString());
    }

    @Test
    public void constructUriWithQueryParameter() {
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http").host("www.google.com")
                .path("/").query("q={keyword}&k={k2}").buildAndExpand("baeldung","helloworld");

        assertEquals("http://www.google.com/?q=baeldung&k=helloworld", uriComponents.toUriString());
    }


}
