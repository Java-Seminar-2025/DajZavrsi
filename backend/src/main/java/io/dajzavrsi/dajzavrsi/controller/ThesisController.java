package io.dajzavrsi.dajzavrsi.controller;

import io.dajzavrsi.dajzavrsi.model.Thesis;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThesisController {
    @GetMapping("/api/theses")
    public Thesis getTheses(){
        Thesis rad = new Thesis(
                1L, "Web application", "Duje Budiša","Declined");
        return rad;
    }
}
