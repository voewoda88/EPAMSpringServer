package com.example.myspring.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class AccessCounterService {
    private int counter;

    public void add() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }
}
