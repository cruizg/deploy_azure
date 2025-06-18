package com.cibertec.evaluacionT2_LP2.controller;

import com.cibertec.evaluacionT2_LP2.entity.Detalle_alquiler;
import com.cibertec.evaluacionT2_LP2.repository.Detalle_alquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class Detalle_alquilerController {

    @Autowired
    private Detalle_alquilerRepository detalleRepo;

    // Listar todos los detalles de alquiler o buscar por filtro
    @GetMapping("/detalle_alquiler")
    public String listarDetalles(
            @RequestParam(value = "filtro", required = false) String filtro,
            Model model) {
        List<Detalle_alquiler> detalles;
        if (filtro != null && !filtro.trim().isEmpty()) {
            detalles = detalleRepo.buscarPorClientePeliculaOEstado(filtro.toLowerCase());
        } else {
            detalles = detalleRepo.findAll();
        }
        model.addAttribute("detalles", detalles);
        return "detalle_alquiler"; // nombre de tu vista Thymeleaf
    }
}