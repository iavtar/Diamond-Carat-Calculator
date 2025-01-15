package com.iavtar.gems.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Custom404Controller {

    @GetMapping("/404")
    public String notFound() {
        return "404";
    }

}
