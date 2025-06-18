package com.cibertec.evaluacionT2_LP2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/inicio"})
    public String inicio() {
        return "inicio"; // Busca inicio.html en templates
    }
}