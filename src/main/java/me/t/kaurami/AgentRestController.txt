package me.t.kaurami;

import me.t.kaurami.data.AgentRepository;
import me.t.kaurami.entities.Agent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/data-api/agents", produces = "application/json")
@CrossOrigin({"http://localhost:6001", "http://localhost:8080"})
public class AgentRestController {

    private AgentRepository agentRepository;
    private TestAgent testAgent;

    public AgentRestController(AgentRepository agentRepository, TestAgent testAgent) {
        this.agentRepository = agentRepository;
        this.testAgent = testAgent;
    }

    @GetMapping(params = "all")
    public Optional<Agent> getAgents(){
        testAgent.setAgents();
        Iterable<Agent> agents = agentRepository.findAll();
        Agent agent = agents.iterator().next();
        System.out.println(agent);
        return Optional.of(agent);

    }




}
