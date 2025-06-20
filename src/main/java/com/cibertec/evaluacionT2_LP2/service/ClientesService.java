package com.cibertec.evaluacionT2_LP2.service;

import com.cibertec.evaluacionT2_LP2.entity.Clientes;
import com.cibertec.evaluacionT2_LP2.entity.Alquileres;
import com.cibertec.evaluacionT2_LP2.repository.ClientesRepository;
import com.cibertec.evaluacionT2_LP2.repository.AlquileresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@Service
public class ClientesService {

    @Autowired
    private ClientesRepository clientesRepo;

    @Autowired
    private AlquileresRepository alquileresRepo;

    public List<Clientes> listarTodos() {
        return clientesRepo.findAll();
    }

    public List<Clientes> buscarPorNombreOEmail(String filtro) {
        return clientesRepo.findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(filtro, filtro);
    }

    public Optional<Clientes> buscarPorId(Long id) {
        return clientesRepo.findById(id);
    }

    public Optional<Clientes> buscarPorEmail(String email) {
        return clientesRepo.findByEmail(email);
    }

    public Clientes guardar(Clientes cliente) {
        return clientesRepo.save(cliente);
    }

    public void eliminarPorId(Long id) throws Exception {
        Clientes cliente = clientesRepo.findById(id)
                .orElseThrow(() -> new Exception("Cliente no encontrado"));
        long alquileresActivos = alquileresRepo.countByClienteAndEstadoIn(
                cliente,
                Arrays.asList(
                        Alquileres.EstadoAlquiler.Activo,
                        Alquileres.EstadoAlquiler.Retrasado
                )
        );
        if (alquileresActivos > 0) {
            throw new Exception("No se puede eliminar. Tiene alquileres activos o retrasados.");
        }
        clientesRepo.deleteById(id);
    }
}