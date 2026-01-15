package com.chaiaurjava.api_gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

    @GetMapping("/fallback/catalog")
    public String fallback(){
        return "Catalog Service is down";
    }
}
