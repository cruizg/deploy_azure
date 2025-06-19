package com.cibertec.evaluacionT2_LP2.controller;

import com.cibertec.evaluacionT2_LP2.entity.Clientes;
import com.cibertec.evaluacionT2_LP2.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientesController {

    @Autowired
    private ClientesRepository clientesRepo;

    // --- READ: Mostrar lista de clientes con filtro de búsqueda ---
    @GetMapping("/clientes")
    public String listarClientes(
            @RequestParam(value = "filtro", required = false) String filtro,
            Model model) {
        if (filtro != null && !filtro.trim().isEmpty()) {
            model.addAttribute("clientes", clientesRepo.findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(filtro, filtro));
        } else {
            model.addAttribute("clientes", clientesRepo.findAll());
        }
        model.addAttribute("cliente", new Clientes());
        return "clientes";
    }

    // --- CREATE/UPDATE/DELETE según acción del botón ---
    @PostMapping("/clientes/guardar")
    public String procesarCliente(
            @ModelAttribute("cliente") Clientes cliente,
            @RequestParam("accion") String accion,
            RedirectAttributes redirectAttrs) {

        if ("agregar".equals(accion)) {
            if (cliente.getId_cliente() == null) {
                // Validar email único
                if (clientesRepo.findByEmail(cliente.getEmail()).isPresent()) {
                    redirectAttrs.addFlashAttribute("mensajeError", "El email ya está registrado.");
                    return "redirect:/clientes";
                }
                try {
                    clientesRepo.save(cliente);
                    redirectAttrs.addFlashAttribute("mensajeExito", "Cliente agregado");
                } catch (Exception e) {
                    redirectAttrs.addFlashAttribute("mensajeError", "No se pudo  agregar al cliente.");
                }
            }
        } else if ("eliminar".equals(accion)) {
            if (cliente.getId_cliente() != null) {
                try {
                    clientesRepo.deleteById(cliente.getId_cliente());
                    redirectAttrs.addFlashAttribute("mensajeExito", "Cliente eliminado");
                } catch (Exception e) {
                    redirectAttrs.addFlashAttribute("mensajeError", "No se puede eliminar. Tiene alquileres registrados.");
                }
            }
        } else if ("editar".equals(accion) || "guardar".equals(accion)) {
            if (cliente.getId_cliente() != null) {
                try {
                    clientesRepo.save(cliente);
                    redirectAttrs.addFlashAttribute("mensajeExito", "Datos actualizados");
                } catch (Exception e) {
                    redirectAttrs.addFlashAttribute("mensajeError", "Ocurrió un error al actualizar el cliente.");
                }
            }
        }
        return "redirect:/clientes";
    }

    // --- UPDATE: Mostrar formulario para editar cliente ---
    @GetMapping("/clientes/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Clientes cliente = clientesRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Id de cliente inválido: " + id));
        model.addAttribute("cliente", cliente);
        model.addAttribute("clientes", clientesRepo.findAll());
        return "clientes";
    }
}