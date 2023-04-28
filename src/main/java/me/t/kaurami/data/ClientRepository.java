package me.t.kaurami.data;

import me.t.kaurami.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends JpaRepository<Client, String> {

    Iterable<Client> findByAgentAgentNameContainingIgnoringCaseAndNameContainingIgnoringCase(String agentName, String containing);
}
