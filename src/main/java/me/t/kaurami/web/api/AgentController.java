package me.t.kaurami.web.api;


import me.t.kaurami.data.AgentRepository;
import me.t.kaurami.entities.Agent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/agents", produces = "application/json")
@CrossOrigin(origins = "http://localhost:60001")
public class AgentController {

    private AgentRepository agentRepository;

    public AgentController(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @GetMapping(params = "all")
    public Iterable<Agent> getAgents(){
        return agentRepository.findAll();
    }
}
