package com.artsiomhanchar.peopledbweb.web.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("error")
public class ErrorController {

    @GetMapping
    public String getErrorPage() {
        return "error";
    }
}
