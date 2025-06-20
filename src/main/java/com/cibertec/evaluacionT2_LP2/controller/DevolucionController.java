package com.cibertec.evaluacionT2_LP2.controller;

import com.cibertec.evaluacionT2_LP2.entity.Clientes;
import com.cibertec.evaluacionT2_LP2.entity.Peliculas;
import com.cibertec.evaluacionT2_LP2.service.DevolucionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DevolucionController {

    @Autowired
    private DevolucionService devolucionService;

    @GetMapping("/devoluciones/registrar")
    public String mostrarFormularioDevolucion(
            @RequestParam(value = "clienteId", required = false) Long clienteId,
            Model model) {

        List<Clientes> clientesActivos = devolucionService.clientesConAlquilerActivoORRetrasado();
        List<Peliculas> peliculasAlquiladas = (clienteId != null)
                ? devolucionService.peliculasAlquiladasPorCliente(clienteId)
                : List.of();

        DevolucionForm form = new DevolucionForm();
        if (clienteId != null) form.setClienteId(clienteId);

        model.addAttribute("clientesActivos", clientesActivos);
        model.addAttribute("peliculasAlquiladas", peliculasAlquiladas);
        model.addAttribute("devolucionForm", form);
        return "devolucion";
    }

    @PostMapping("/devoluciones/registrar")
    public String procesarDevolucion(
            @ModelAttribute("devolucionForm") DevolucionForm form,
            RedirectAttributes redirectAttrs) {
        try {
            devolucionService.procesarDevolucion(form.getClienteId(), form.getPeliculaId(), form.getCantidad());
            redirectAttrs.addFlashAttribute("mensajeExito", "Devolución registrada correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/devoluciones/registrar";
    }

    @Data
    public static class DevolucionForm {
        private Long clienteId;
        private Long peliculaId;
        private Integer cantidad; // Campo necesario para devoluciones parciales
    }
}