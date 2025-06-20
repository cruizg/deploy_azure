package com.cibertec.evaluacionT2_LP2.service;

import com.cibertec.evaluacionT2_LP2.entity.Alquileres;
import com.cibertec.evaluacionT2_LP2.entity.Clientes;
import com.cibertec.evaluacionT2_LP2.entity.Peliculas;
import com.cibertec.evaluacionT2_LP2.entity.Detalle_alquiler;
import com.cibertec.evaluacionT2_LP2.repository.AlquileresRepository;
import com.cibertec.evaluacionT2_LP2.repository.ClientesRepository;
import com.cibertec.evaluacionT2_LP2.repository.PeliculasRepository;
import com.cibertec.evaluacionT2_LP2.repository.Detalle_alquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class AlquileresService {

    @Autowired
    private AlquileresRepository alquileresRepo;
    @Autowired
    private ClientesRepository clientesRepo;
    @Autowired
    private PeliculasRepository peliculasRepo;
    @Autowired
    private Detalle_alquilerRepository detalleRepo;

    public List<Clientes> listarClientes() {
        return clientesRepo.findAll();
    }

    public List<Peliculas> listarPeliculas() {
        return peliculasRepo.findAll();
    }

    public boolean puedeAlquilar(Clientes cliente) {
        long alquileresActivos = alquileresRepo.countByClienteAndEstadoIn(
                cliente,
                Arrays.asList(Alquileres.EstadoAlquiler.Activo, Alquileres.EstadoAlquiler.Retrasado)
        );
        return alquileresActivos < 2;
    }

    public boolean registrarAlquiler(Long clienteId, Long peliculaId, Integer cantidad) throws Exception {
        Clientes cliente = clientesRepo.findById(clienteId).orElse(null);
        Peliculas pelicula = peliculasRepo.findById(peliculaId).orElse(null);

        if (cliente == null || pelicula == null) throw new Exception("Cliente o película inválidos.");
        if (cantidad == null || cantidad < 1) throw new Exception("Cantidad inválida.");
        if (pelicula.getStock() < cantidad) throw new Exception("Stock insuficiente.");
        if (!puedeAlquilar(cliente)) throw new Exception("El cliente ya tiene dos alquileres activos o retrasados.");

        Alquileres alquiler = new Alquileres();
        alquiler.setCliente(cliente);
        alquiler.setFecha(LocalDate.now());
        alquiler.setTotal(pelicula.getPrecio() * cantidad);
        alquiler.setEstado(Alquileres.EstadoAlquiler.Activo);
        alquileresRepo.save(alquiler);

        Detalle_alquiler detalle = new Detalle_alquiler(alquiler, pelicula, cantidad);
        detalleRepo.save(detalle);

        pelicula.setStock(pelicula.getStock() - cantidad);
        peliculasRepo.save(pelicula);

        return true;
    }

    // Nuevo método para listar todos los alquileres (incluyendo devueltos y sin detalles)
    public List<Alquileres> listarTodosAlquileres() {
        return alquileresRepo.findAll();
    }

    public List<Detalle_alquiler> listarDetalles(String filtro) {
        if (filtro != null && !filtro.trim().isEmpty()) {
            return detalleRepo.buscarPorClientePeliculaOEstado(filtro.toLowerCase());
        } else {
            return detalleRepo.findAll();
        }
    }

    public void eliminarAlquiler(Long idAlquiler) {
        List<Detalle_alquiler> detalles = detalleRepo.findByAlquilerIdAlquiler(idAlquiler);
        detalleRepo.deleteAll(detalles);
        alquileresRepo.deleteById(idAlquiler);
    }
}