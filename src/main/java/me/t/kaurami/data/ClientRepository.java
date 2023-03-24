package me.t.kaurami.data;

import me.t.kaurami.entities.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, String> {

    Iterable<Client> findByNameContaining(String containing);
}
