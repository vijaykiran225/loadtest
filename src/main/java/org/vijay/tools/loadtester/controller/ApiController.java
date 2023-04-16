package org.vijay.tools.loadtester.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vijay.tools.loadtester.processors.Processor;

import java.util.Map;

@RestController
public class ApiController {

    @RequestMapping(value = "/command/{id}",method = RequestMethod.GET)
    public ResponseEntity entry(@PathVariable("id") int count) {
        Processor p= new Processor();
        Map<Long, Integer> trigger = p.trigger(count);
        return ResponseEntity.ok(trigger);
    }
}
