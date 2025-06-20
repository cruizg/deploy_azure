package com.cibertec.evaluacionT2_LP2.controller;

import com.cibertec.evaluacionT2_LP2.entity.Peliculas;
import com.cibertec.evaluacionT2_LP2.service.PeliculasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PeliculasController {

    @Autowired
    private PeliculasService peliculasService;

    @GetMapping("/peliculas")
    public String listarPeliculas(
            @RequestParam(value = "filtro", required = false) String filtro,
            Model model) {
        if (filtro != null && !filtro.trim().isEmpty()) {
            model.addAttribute("peliculas", peliculasService.buscarPorTituloOGenero(filtro));
        } else {
            model.addAttribute("peliculas", peliculasService.listarTodas());
        }
        model.addAttribute("pelicula", new Peliculas());
        return "peliculas";
    }

    @PostMapping("/peliculas/guardar")
    public String procesarPelicula(
            @ModelAttribute("pelicula") Peliculas pelicula,
            @RequestParam("accion") String accion,
            RedirectAttributes redirectAttrs) {

        if ("agregar".equals(accion)) {
            if (pelicula.getId_pelicula() == null) {
                if (peliculasService.buscarPorTitulo(pelicula.getTitulo()).isPresent()) {
                    redirectAttrs.addFlashAttribute("mensajeError", "El título ya está registrado.");
                    return "redirect:/peliculas";
                }
                try {
                    peliculasService.guardar(pelicula);
                    redirectAttrs.addFlashAttribute("mensajeExito", "Película agregada correctamente.");
                } catch (Exception e) {
                    redirectAttrs.addFlashAttribute("mensajeError", "Ocurrió un error al agregar la película.");
                }
            }
        } else if ("eliminar".equals(accion)) {
            if (pelicula.getId_pelicula() != null) {
                try {
                    peliculasService.eliminarPorId(pelicula.getId_pelicula());
                    redirectAttrs.addFlashAttribute("mensajeExito", "Película eliminada correctamente.");
                } catch (Exception e) {
                    redirectAttrs.addFlashAttribute("mensajeError", "No se puede eliminar la película porque tiene resgistros de alquiler asociados.");
                }
            }
        } else if ("editar".equals(accion) || "guardar".equals(accion)) {
            if (pelicula.getId_pelicula() != null) {
                try {
                    peliculasService.guardar(pelicula);
                    redirectAttrs.addFlashAttribute("mensajeExito", "Película actualizada correctamente.");
                } catch (Exception e) {
                    redirectAttrs.addFlashAttribute("mensajeError", "Ocurrió un error al actualizar la película.");
                }
            }
        }
        return "redirect:/peliculas";
    }

    @GetMapping("/peliculas/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Peliculas pelicula = peliculasService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Id de película inválido: " + id));
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("peliculas", peliculasService.listarTodas());
        return "peliculas";
    }
}