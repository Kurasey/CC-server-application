package me.t.kaurami.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.t.kaurami.SpringDemoApplication;
import me.t.kaurami.data.RequestRepository;
import me.t.kaurami.entities.Agent;
import me.t.kaurami.entities.Client;
import me.t.kaurami.entities.Request;
import me.t.kaurami.web.api.RequestController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

@WebMvcTest(RequestController.class)
public class RequestRestControllerTest {

    @MockBean
    RequestRepository requestRepository;


    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    private void setMock(){
        Mockito.when(requestRepository.save(Mockito.any())).thenReturn(null);
    }

    @Test
    void whenPostNotValidRequestThenException() throws Exception {
        String content =objectMapper.writeValueAsString(new Request(null,null, Request.RequestType.LIMIT,""));
        System.out.println(content);
        mockMvc.perform(MockMvcRequestBuilders.post("/requests").content(content).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void thenPostValidRequestThenOk() throws Exception{

        Agent agent = Agent.newAgent("ad", "das");
        Client client = new Client.ClientBuilder().accessID("1231231").name("asdasd").individualTaxpayerNumber("234234234").agent(agent).build();
        String content = objectMapper.writeValueAsString(
                new Request(client, agent, Request.RequestType.LIMIT,""));
        mockMvc.perform(MockMvcRequestBuilders.post("/requests").content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void test() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/requests"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
