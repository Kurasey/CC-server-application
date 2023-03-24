package me.t.kaurami.data;

import me.t.kaurami.entities.Agent;
import org.springframework.data.repository.CrudRepository;

public interface AgentRepository extends CrudRepository<Agent, String> {

    Iterable<Agent> findByAgentNameContaining(String containing);
}
