package com.cibertec.evaluacionT2_LP2.controller;

import com.cibertec.evaluacionT2_LP2.entity.Alquileres;
import com.cibertec.evaluacionT2_LP2.entity.Clientes;
import com.cibertec.evaluacionT2_LP2.entity.Peliculas;
import com.cibertec.evaluacionT2_LP2.entity.Detalle_alquiler;
import com.cibertec.evaluacionT2_LP2.repository.AlquileresRepository;
import com.cibertec.evaluacionT2_LP2.repository.ClientesRepository;
import com.cibertec.evaluacionT2_LP2.repository.PeliculasRepository;
import com.cibertec.evaluacionT2_LP2.repository.Detalle_alquilerRepository;
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
    private AlquileresRepository alquileresRepo;
    @Autowired
    private ClientesRepository clientesRepo;
    @Autowired
    private PeliculasRepository peliculasRepo;
    @Autowired
    private Detalle_alquilerRepository detalleRepo;

    @GetMapping("/devoluciones/registrar")
    public String mostrarFormularioDevolucion(
            @RequestParam(value = "clienteId", required = false) Long clienteId,
            Model model) {

        List<Clientes> clientesActivos = alquileresRepo.findClientesConAlquilerActivoORRetrasado();
        List<Peliculas> peliculasAlquiladas = (clienteId != null)
                ? detalleRepo.findPeliculasAlquiladasPorCliente(clienteId)
                : List.of();

        DevolucionForm form = new DevolucionForm();
        // No asignar clienteId si es null, así el combo aparece vacío
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
            Clientes cliente = clientesRepo.findById(form.getClienteId()).orElse(null);
            Peliculas pelicula = peliculasRepo.findById(form.getPeliculaId()).orElse(null);

            if (cliente == null || pelicula == null) {
                redirectAttrs.addFlashAttribute("mensajeError", "Debe seleccionar cliente y película válidos.");
                return "redirect:/devoluciones/registrar";
            }
            if (form.getCantidad() == null || form.getCantidad() < 1) {
                redirectAttrs.addFlashAttribute("mensajeError", "La cantidad debe ser mayor a cero.");
                return "redirect:/devoluciones/registrar?clienteId=" + cliente.getId_cliente();
            }

            Alquileres alquiler = detalleRepo.findAlquilerActivoORRetrasadoPorClienteYPelicula(
                    cliente.getId_cliente(), pelicula.getId_pelicula());
            if (alquiler == null) {
                redirectAttrs.addFlashAttribute("mensajeError", "No se encontró un alquiler activo/retrasado para esa película y cliente.");
                return "redirect:/devoluciones/registrar?clienteId=" + cliente.getId_cliente();
            }

            Detalle_alquiler detalle = detalleRepo.findByAlquilerAndPelicula(alquiler, pelicula);
            if (detalle == null) {
                redirectAttrs.addFlashAttribute("mensajeError", "No se encontró el detalle de alquiler.");
                return "redirect:/devoluciones/registrar?clienteId=" + cliente.getId_cliente();
            }
            if (form.getCantidad() > detalle.getCantidad()) {
                redirectAttrs.addFlashAttribute("mensajeError", "No puede devolver más de lo alquilado.");
                return "redirect:/devoluciones/registrar?clienteId=" + cliente.getId_cliente();
            }

            pelicula.setStock(pelicula.getStock() + form.getCantidad());
            peliculasRepo.save(pelicula);

            if (form.getCantidad().equals(detalle.getCantidad())) {
                detalleRepo.delete(detalle);
            } else {
                detalle.setCantidad(detalle.getCantidad() - form.getCantidad());
                detalleRepo.save(detalle);
            }

            long restantes = detalleRepo.countByAlquiler(alquiler);
            if (restantes == 0) {
                alquiler.setEstado(Alquileres.EstadoAlquiler.Devuelto);
                alquileresRepo.save(alquiler);
            }

            redirectAttrs.addFlashAttribute("mensajeExito", "Devolución registrada correctamente.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensajeError", "Ocurrió un error al registrar la devolución.");
        }
        return "redirect:/devoluciones/registrar";
    }

    @Data
    public static class DevolucionForm {
        private Long clienteId;
        private Long peliculaId;
        private Integer cantidad;
    }
}