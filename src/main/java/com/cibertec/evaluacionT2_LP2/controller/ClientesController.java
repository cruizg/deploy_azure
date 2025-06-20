package com.cibertec.evaluacionT2_LP2.controller;

import com.cibertec.evaluacionT2_LP2.entity.Clientes;
import com.cibertec.evaluacionT2_LP2.service.ClientesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientesController {

    @Autowired
    private ClientesService clientesService;

    @GetMapping("/clientes")
    public String listarClientes(
            @RequestParam(value = "filtro", required = false) String filtro,
            Model model) {
        if (filtro != null && !filtro.trim().isEmpty()) {
            model.addAttribute("clientes", clientesService.buscarPorNombreOEmail(filtro));
        } else {
            model.addAttribute("clientes", clientesService.listarTodos());
        }
        model.addAttribute("cliente", new Clientes());
        return "clientes";
    }

    @PostMapping("/clientes/guardar")
    public String procesarCliente(
            @ModelAttribute("cliente") Clientes cliente,
            @RequestParam("accion") String accion,
            RedirectAttributes redirectAttrs) {

        if ("agregar".equals(accion)) {
            if (cliente.getId_cliente() == null) {
                if (clientesService.buscarPorEmail(cliente.getEmail()).isPresent()) {
                    redirectAttrs.addFlashAttribute("mensajeError", "El email ya está registrado.");
                    return "redirect:/clientes";
                }
                try {
                    clientesService.guardar(cliente);
                    redirectAttrs.addFlashAttribute("mensajeExito", "Cliente agregado");
                } catch (Exception e) {
                    redirectAttrs.addFlashAttribute("mensajeError", "No se pudo  agregar al cliente.");
                }
            }
        } else if ("eliminar".equals(accion)) {
            if (cliente.getId_cliente() != null) {
                try {
                    clientesService.eliminarPorId(cliente.getId_cliente());
                    redirectAttrs.addFlashAttribute("mensajeExito", "Cliente eliminado");
                } catch (Exception e) {
                    redirectAttrs.addFlashAttribute("mensajeError", "No se puede eliminar. Tiene alquileres registrados.");
                }
            }
        } else if ("editar".equals(accion) || "guardar".equals(accion)) {
            if (cliente.getId_cliente() != null) {
                try {
                    clientesService.guardar(cliente);
                    redirectAttrs.addFlashAttribute("mensajeExito", "Datos actualizados");
                } catch (Exception e) {
                    redirectAttrs.addFlashAttribute("mensajeError", "Ocurrió un error al actualizar el cliente.");
                }
            }
        }
        return "redirect:/clientes";
    }

    @GetMapping("/clientes/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model) {
        Clientes cliente = clientesService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Id de cliente inválido: " + id));
        model.addAttribute("cliente", cliente);
        model.addAttribute("clientes", clientesService.listarTodos());
        return "clientes";
    }
}