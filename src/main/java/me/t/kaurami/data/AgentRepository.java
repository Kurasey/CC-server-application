package me.t.kaurami.data;

import me.t.kaurami.entities.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AgentRepository extends JpaRepository<Agent, String> {

    Iterable<Agent> findByAgentNameContainingIgnoringCase(String containing);
}
