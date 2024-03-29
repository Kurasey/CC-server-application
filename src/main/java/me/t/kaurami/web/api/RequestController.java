package me.t.kaurami.web.api;

import me.t.kaurami.data.AgentRepository;
import me.t.kaurami.data.ClientRepository;
import me.t.kaurami.data.RequestRepository;
import me.t.kaurami.entities.Agent;
import me.t.kaurami.entities.Client;
import me.t.kaurami.entities.Request;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/requests", produces = "application/json")
@CrossOrigin(origins = "http://localhost:60001")
public class RequestController {

    private RequestRepository requestRepository;

    public RequestController(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @GetMapping(params = "all")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Request> getRequests() {
        return requestRepository.findAll();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Request> getRequest() {
        return requestRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Request saveRequest(@RequestBody @Valid Request request) {
        return requestRepository.save(request);
    }

    @PutMapping(path = "/{requestId}", consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Request putRequest(@PathVariable("requestId") Long requestId,
                              @RequestBody @Valid Request request){

        return requestRepository.save(request);
    }

    @DeleteMapping("/{requestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRequest(@PathVariable("requestId") Long requestId) {
        requestRepository.deleteById(requestId);
    }

    @GetMapping(params = "count")
    @ResponseStatus(HttpStatus.OK)
    public Long getCount(){
        return requestRepository.count();
    }
}