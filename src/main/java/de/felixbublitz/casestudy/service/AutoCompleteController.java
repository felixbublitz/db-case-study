package de.felixbublitz.casestudy.service;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutoCompleteController {

    @RequestMapping("/api/v1/auto-complete/")
    String showDefaultMessage() {
        return "service running";
    }

}