package me.t.kaurami.web.api;

import me.t.kaurami.data.RequestRepository;
import me.t.kaurami.entities.Request;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/requests", produces = "application/json")
@CrossOrigin(origins = "http://localhost:60001")
public class RequestController {

    private RequestRepository requestRepository;

    public RequestController(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @GetMapping(params = "all")
    public Iterable<Request> getRequests() {
        return requestRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Request saveRequest(@RequestBody Request request){
        return requestRepository.save(request);
    }

    @Deprecated
    @PutMapping(path = "/{requestId}", consumes = "application/json")
    public Request putRequest(@PathVariable("requestId") Long requestId,
                              @RequestBody Request request){
        return requestRepository.save(request);
    }

    @DeleteMapping("/{requestId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteRequest(@PathVariable("requestId") Long requestId) {
        requestRepository.deleteById(requestId);
    }
}