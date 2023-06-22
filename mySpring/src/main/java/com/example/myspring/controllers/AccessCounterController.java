package com.example.myspring.controllers;

import com.example.myspring.service.AccessCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessCounterController
{
    @Autowired
    private AccessCounterService counter;
    @GetMapping("/counter")
    public int getAccessCounter() {
        return counter.getCounter();
    }

}

