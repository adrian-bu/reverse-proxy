package de.adrianbuch.reverse.proxy.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {
    
    @GetMapping({"", "/", "/index", "/home"})
    public String getHome() {
        return "homepage";
    }
}
