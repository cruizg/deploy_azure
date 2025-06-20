package com.cibertec.evaluacionT2_LP2.controller;

import com.cibertec.evaluacionT2_LP2.entity.Detalle_alquiler;
import com.cibertec.evaluacionT2_LP2.service.DetalleAlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DetalleAlquilerController {

    @Autowired
    private DetalleAlquilerService detalleAlquilerService;

    @GetMapping("/detalle_alquiler/listado")
    public String listarDetalles(@RequestParam(value = "filtro", required = false) String filtro, Model model) {
        List<Detalle_alquiler> detalles = (filtro != null && !filtro.trim().isEmpty())
                ? detalleAlquilerService.buscarPorFiltro(filtro.toLowerCase())
                : detalleAlquilerService.listarTodos();
        model.addAttribute("detalles", detalles);
        return "listado_detalle_alquiler";
    }

}