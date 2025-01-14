package com.iavtar.gems.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalculatorController {

    @GetMapping("/dashboard")
    public String calculatorPage(Model model) {
        return "Calculator";
    }

}
