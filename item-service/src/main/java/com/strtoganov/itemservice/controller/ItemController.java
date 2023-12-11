package com.strtoganov.itemservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @GetMapping("/welcome")
    public String getGreetings() {
        return "This is welcome item message";
    }

    @GetMapping("/")
    public String getItemsList() {
        return "This is item list controller";
    }

}
