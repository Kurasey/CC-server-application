package me.t.kaurami.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.t.kaurami.security.SecurityConfig;
import me.t.kaurami.data.RequestRepository;
import me.t.kaurami.entities.Agent;
import me.t.kaurami.entities.Client;
import me.t.kaurami.entities.Request;
import me.t.kaurami.web.api.RequestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(RequestController.class)
@Import(SecurityConfig.class)
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
    @WithMockUser(authorities = {"CREATE_REQUEST"})
    void whenPostNotValidRequestThenException() throws Exception {
        String content = objectMapper.writeValueAsString(new Request(null,null, Request.RequestType.LIMIT,""));
        System.out.println(content);
        mockMvc.perform(MockMvcRequestBuilders.post("/requests").with(csrf()).content(content).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "CREATE_REQUEST")
    void whenPostValidRequestThenOk() throws Exception{
        Agent agent = Agent.newAgent("ad", "das");
        Client client = Client.builder().accessID("1231231").name("asdasd").individualTaxpayerNumber("234234234").agent(agent).build();
        String content = objectMapper.writeValueAsString(
                new Request(client, agent, Request.RequestType.LIMIT,""));
        mockMvc.perform(MockMvcRequestBuilders.post("/requests").with(csrf()).content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @WithMockUser(authorities = {"DELETE_REQUEST"})
    void whenDeleteThenStatusNoContent() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/requests/1").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void whenAdminGetAllThenOk() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/requests?all"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithAnonymousUser
    void whenAnonymousUserThenRedirectLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/requests?all"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithMockUser(roles = "ANOTHER")
    void whenAnotherUserThenForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/requests?all"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }


}
