package com.organizer.web.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @GetMapping(value = {"/", "/login"})
    public String entry(){
        return "index";
    }
}
