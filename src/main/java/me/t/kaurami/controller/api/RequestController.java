package me.t.kaurami.controller.api;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "request/table", produces ="application/json")
@CrossOrigin(origins = "localhost:62002")
public class RequestController {
    

}
