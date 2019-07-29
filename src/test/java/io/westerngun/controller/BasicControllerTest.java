package io.westerngun.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BasicController.class, secure = false)
public class BasicControllerTest {

    @MockBean
    private DumbService dumbService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void dumbTest() throws Exception {
        // given
        // when you forget to stub service, the bug happens

        // when
        mockMvc.perform(post("/bar")
                .header(ACCEPT, APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\": \"nothing\"}"))
        // then
                .andDo(print())
                .andExpect(status().isOk());
    }
}