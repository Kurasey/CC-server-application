package me.t.kaurami.web.api;


import me.t.kaurami.data.AgentRepository;
import me.t.kaurami.entities.Agent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/agents", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class AgentController{

    private AgentRepository agentRepository;

    public AgentController(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @GetMapping(params = "all")
    public ResponseEntity<Iterable<Agent>> getAgents(){
        Iterable<Agent> agents = agentRepository.findAll();
        if (agents.iterator().hasNext()) {
            return ResponseEntity.ok(agents);
        }
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<Iterable<Agent>> findLikeName(@RequestParam("namePart") String namePart) {
        return ResponseEntity.ok(agentRepository.findByAgentNameContainingIgnoringCase(namePart));
    }

    @GetMapping(params = "count")
    @ResponseStatus(HttpStatus.OK)
    public Long getCount(){
        return agentRepository.count();
    }
}
