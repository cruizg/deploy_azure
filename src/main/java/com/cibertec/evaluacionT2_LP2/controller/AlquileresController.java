package com.cibertec.evaluacionT2_LP2.controller;

import com.cibertec.evaluacionT2_LP2.entity.Alquileres;
import com.cibertec.evaluacionT2_LP2.entity.Clientes;
import com.cibertec.evaluacionT2_LP2.entity.Peliculas;
import com.cibertec.evaluacionT2_LP2.entity.Detalle_alquiler;
import com.cibertec.evaluacionT2_LP2.repository.AlquileresRepository;
import com.cibertec.evaluacionT2_LP2.repository.ClientesRepository;
import com.cibertec.evaluacionT2_LP2.repository.PeliculasRepository;
import com.cibertec.evaluacionT2_LP2.repository.Detalle_alquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class AlquileresController {

    @Autowired
    private AlquileresRepository alquileresRepo;
    @Autowired
    private ClientesRepository clientesRepo;
    @Autowired
    private PeliculasRepository peliculasRepo;
    @Autowired
    private Detalle_alquilerRepository detalleRepo;

    // Mostrar formulario de registro de alquiler
    @GetMapping("/alquileres/registrar")
    public String mostrarFormularioRegistrar(Model model) {
        model.addAttribute("alquilerForm", new AlquilerForm());
        model.addAttribute("clientes", clientesRepo.findAll());
        model.addAttribute("peliculas", peliculasRepo.findAll());
        return "registrar_alquiler";
    }

    // Procesar registro de alquiler
    @PostMapping("/alquileres/registrar")
    public String procesarRegistroAlquiler(
            @ModelAttribute("alquilerForm") AlquilerForm form,
            RedirectAttributes redirectAttrs) {
        try {
            Clientes cliente = clientesRepo.findById(form.getClienteId()).orElse(null);
            Peliculas pelicula = peliculasRepo.findById(form.getPeliculaId()).orElse(null);

            if (cliente == null || pelicula == null) {
                redirectAttrs.addFlashAttribute("mensajeError", "Debe seleccionar cliente y película válidos.");
                return "redirect:/alquileres/registrar";
            }
            if (form.getCantidad() == null || form.getCantidad() < 1) {
                redirectAttrs.addFlashAttribute("mensajeError", "La cantidad debe ser mayor a cero.");
                return "redirect:/alquileres/registrar";
            }
            if (pelicula.getStock() < form.getCantidad()) {
                redirectAttrs.addFlashAttribute("mensajeError", "No hay suficiente stock de la película.");
                return "redirect:/alquileres/registrar";
            }
            // Validación: máximo 2 alquileres activos o retrasados por cliente
            long alquileresActivos = alquileresRepo.countByClienteAndEstadoIn(
                cliente,
                Arrays.asList(Alquileres.EstadoAlquiler.Activo, Alquileres.EstadoAlquiler.Retrasado)
            );
            if (alquileresActivos >= 2) {
                redirectAttrs.addFlashAttribute("mensajeError", "El cliente ya tiene dos alquileres activos o retrasados.");
                return "redirect:/alquileres/registrar";
            }

            // Registrar alquiler
            Alquileres alquiler = new Alquileres();
            alquiler.setCliente(cliente);
            alquiler.setFecha(LocalDate.now());
            alquiler.setTotal(pelicula.getPrecio() * form.getCantidad());
            alquiler.setEstado(Alquileres.EstadoAlquiler.Activo);
            alquileresRepo.save(alquiler);

            // Registrar detalle de alquiler
            Detalle_alquiler detalle = new Detalle_alquiler(alquiler, pelicula, form.getCantidad());
            detalleRepo.save(detalle);

            // Actualizar stock de la película
            pelicula.setStock(pelicula.getStock() - form.getCantidad());
            peliculasRepo.save(pelicula);

            redirectAttrs.addFlashAttribute("mensajeExito", "Alquiler registrado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Ocurrió un error al registrar el alquiler.");
        }
        return "redirect:/alquileres/registrar";
    }

    // Listado de alquileres con filtro
    @GetMapping("/alquileres/listado")
    public String listarAlquileres(
            @RequestParam(value = "filtro", required = false) String filtro,
            Model model) {
        List<Detalle_alquiler> detalles;
        if (filtro != null && !filtro.trim().isEmpty()) {
            detalles = detalleRepo.buscarPorClientePeliculaOEstado(filtro.toLowerCase());
        } else {
            detalles = detalleRepo.findAll();
        }
        model.addAttribute("detalles", detalles);
        return "listado_alquileres";
    }

    // Eliminar alquiler y sus detalles
    @PostMapping("/alquileres/eliminar")
    public String eliminarAlquiler(
            @RequestParam("idAlquiler") Long idAlquiler,
            RedirectAttributes redirectAttrs) {
        try {
            List<Detalle_alquiler> detalles = detalleRepo.findByAlquilerIdAlquiler(idAlquiler);
            detalleRepo.deleteAll(detalles);
            alquileresRepo.deleteById(idAlquiler);
            redirectAttrs.addFlashAttribute("mensajeExito", "Alquiler eliminado correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "No se pudo eliminar el alquiler.");
        }
        return "redirect:/alquileres/listado";
    }

    // Clase interna para el formulario
    public static class AlquilerForm {
        private Long clienteId;
        private Long peliculaId;
        private Integer cantidad;

        // Getters y setters
        public Long getClienteId() { return clienteId; }
        public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
        public Long getPeliculaId() { return peliculaId; }
        public void setPeliculaId(Long peliculaId) { this.peliculaId = peliculaId; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}