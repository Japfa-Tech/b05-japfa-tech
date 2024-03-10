package com.propensi.sikpi.controller;

import org.springframework.web.bind.annotation.GetMapping;

import com.propensi.sikpi.repository.UserDb;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class AktivitasController {

    @Autowired
    private UserDb userDb;

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            // User is authenticated
            Object principal = authentication.getPrincipal();

            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();
            Long id = userDb.findByUsername(username).getId();

            model.addAttribute("isLoggedIn", true);
            model.addAttribute("idUser", id);


        } else {
            // User is not authenticated
            model.addAttribute("isLoggedIn", false);
        }
    
        return "home";
    }
}
