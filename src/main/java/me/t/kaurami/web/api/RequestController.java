package me.t.kaurami.web.api;

import me.t.kaurami.data.RequestRepository;
import me.t.kaurami.entities.Request;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/requests", produces = "application/json")
@CrossOrigin(origins = "http://localhost:60001")
public class RequestController {

    private RequestRepository requestRepository;

    public RequestController(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @GetMapping(params = "all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('CREDIT_CONTROLLER', 'ADMIN')")
    public Iterable<Request> getRequests() {
        return requestRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('CREATE_REQUEST')")
    public Request saveRequest(@RequestBody @Valid Request request){
        return requestRepository.save(request);
    }

    @Deprecated
    @PutMapping(path = "/{requestId}", consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAuthority('CREATE_REQUEST')")
    public Request putRequest(@PathVariable("requestId") Long requestId,
                              @RequestBody @Valid Request request){
        return requestRepository.save(request);
    }

    @DeleteMapping("/{requestId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('DELETE_REQUEST')")
    public void deleteRequest(@PathVariable("requestId") Long requestId) {
        requestRepository.deleteById(requestId);
    }
}