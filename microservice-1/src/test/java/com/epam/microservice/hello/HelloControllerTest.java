package com.epam.microservice.hello;


import com.epam.microservice.hello.service.BusinessLogicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(HelloController.class)
public class HelloControllerTest {

//    @Autowired
//    private HelloController helloResource;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusinessLogicService service;

    @Test
    public void shouldReturnHello() throws Exception {

        //when(service.returnSmth()).thenReturn("mocked hello");
        this.mockMvc.perform(
                get("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("mocked hello")));
    }
}
