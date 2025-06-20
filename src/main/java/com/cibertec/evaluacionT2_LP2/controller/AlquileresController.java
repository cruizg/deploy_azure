package com.cibertec.evaluacionT2_LP2.controller;

import com.cibertec.evaluacionT2_LP2.entity.Alquileres;
import com.cibertec.evaluacionT2_LP2.service.AlquileresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List; 

@Controller
public class AlquileresController {

    @Autowired
    private AlquileresService alquileresService;

    @GetMapping("/alquileres/registrar")
    public String mostrarFormularioRegistrar(Model model) {
        model.addAttribute("alquilerForm", new AlquilerForm());
        model.addAttribute("clientes", alquileresService.listarClientes());
        model.addAttribute("peliculas", alquileresService.listarPeliculas());
        return "registrar_alquiler";
    }

    @PostMapping("/alquileres/registrar")
    public String procesarRegistroAlquiler(
            @ModelAttribute("alquilerForm") AlquilerForm form,
            RedirectAttributes redirectAttrs) {
        try {
            alquileresService.registrarAlquiler(form.getClienteId(), form.getPeliculaId(), form.getCantidad());
            redirectAttrs.addFlashAttribute("mensajeExito", "Alquiler registrado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/alquileres/registrar";
    }

  @GetMapping("/alquileres/listado")
public String listarAlquileres(Model model) {
    List<Alquileres> alquileres = alquileresService.listarTodosAlquileres();
    model.addAttribute("alquileres", alquileres);
    return "listado_alquileres";
}

    @PostMapping("/alquileres/eliminar")
    public String eliminarAlquiler(
            @RequestParam("idAlquiler") Long idAlquiler,
            RedirectAttributes redirectAttrs) {
        try {
            alquileresService.eliminarAlquiler(idAlquiler);
            redirectAttrs.addFlashAttribute("mensajeExito", "Alquiler eliminado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "No se pudo eliminar el alquiler.");
        }
        return "redirect:/alquileres/listado";
    }

    public static class AlquilerForm {
        private Long clienteId;
        private Long peliculaId;
        private Integer cantidad;

        public Long getClienteId() { return clienteId; }
        public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
        public Long getPeliculaId() { return peliculaId; }
        public void setPeliculaId(Long peliculaId) { this.peliculaId = peliculaId; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}