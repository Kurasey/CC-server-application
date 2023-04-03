package me.t.kaurami.web.api;

import me.t.kaurami.data.ClientRepository;
import me.t.kaurami.entities.Client;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/clients", produces = "application/json")
@CrossOrigin(origins = "http://localhost:60001")
public class ClientsRestController {

    private ClientRepository clientRepository;

    public ClientsRestController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping(params = "all")
    public Iterable<Client> getClients(){
        return clientRepository.findAll();
    }
}
