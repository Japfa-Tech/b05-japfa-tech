package com.propensi.sikpi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PageController {
    @Autowired
    ServerProperties serverProperties;

    @RequestMapping("port")
    public String ActivePort(Model model) {
        model.addAttribute("port", serverProperties.getPort());

        return "active-port";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        return "login";
    }

    @PostMapping(value = "/log-out")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "home";
    }    

    @GetMapping("/access-denied")
    public String deny() {
        return "access-denied";
    }
}
