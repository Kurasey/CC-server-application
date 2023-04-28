package me.t.kaurami.web.api;

import me.t.kaurami.data.ClientRepository;
import me.t.kaurami.entities.Client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/clients", produces = "application/json")
@CrossOrigin(origins = "http://localhost:60001")
public class ClientsRestController {

    private ClientRepository clientRepository;

    public ClientsRestController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping(params = "all")
//    @PreAuthorize("hasAuthority('READ_CLIENTS')")
    public ResponseEntity<Iterable<Client>> getClients() {
        Iterable<Client> clients = clientRepository.findAll();
        if (clients.iterator().hasNext()) {
            return ResponseEntity.ok(clients);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<Iterable<Client>> findByNamePart(@RequestParam("agentName")String agentName, @RequestParam("namePart") String namePart) {
        return ResponseEntity.ok(clientRepository.findByAgentAgentNameContainingIgnoringCaseAndNameContainingIgnoringCase(agentName, namePart));
    }

    @GetMapping(params = "count")
    @ResponseStatus(HttpStatus.OK)
    public Long getCount(){
        return clientRepository.count();
    }
}
