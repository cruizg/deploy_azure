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

import java.util.List;

@Service
public class DevolucionService {

    @Autowired
    private AlquileresRepository alquileresRepo;
    @Autowired
    private ClientesRepository clientesRepo;
    @Autowired
    private PeliculasRepository peliculasRepo;
    @Autowired
    private Detalle_alquilerRepository detalleRepo;

    public List<Clientes> clientesConAlquilerActivoORRetrasado() {
        return alquileresRepo.findClientesConAlquilerActivoORRetrasado();
    }

    public List<Peliculas> peliculasAlquiladasPorCliente(Long clienteId) {
        return detalleRepo.findPeliculasAlquiladasPorCliente(clienteId);
    }

    public void procesarDevolucion(Long clienteId, Long peliculaId, Integer cantidad) throws Exception {
        Clientes cliente = clientesRepo.findById(clienteId).orElse(null);
        Peliculas pelicula = peliculasRepo.findById(peliculaId).orElse(null);

        if (cliente == null || pelicula == null) {
            throw new Exception("Debe seleccionar cliente y película válidos.");
        }

        Alquileres alquiler = detalleRepo.findAlquilerActivoORRetrasadoPorClienteYPelicula(
                cliente.getId_cliente(), pelicula.getId_pelicula());
        if (alquiler == null) {
            throw new Exception("No se encontró un alquiler activo/retrasado para esa película y cliente.");
        }

        Detalle_alquiler detalle = detalleRepo.findByAlquilerAndPelicula(alquiler, pelicula);
        if (detalle == null) {
            throw new Exception("No se encontró el detalle de alquiler.");
        }

        if (cantidad == null || cantidad < 1 || cantidad > detalle.getCantidad()) {
            throw new Exception("Cantidad a devolver inválida.");
        }

        pelicula.setStock(pelicula.getStock() + cantidad);
        peliculasRepo.save(pelicula);

        if (cantidad.equals(detalle.getCantidad())) {
            detalle.setCantidad(0);
            detalleRepo.save(detalle);
        } else {
            detalle.setCantidad(detalle.getCantidad() - cantidad);
            detalleRepo.save(detalle);
        }

        List<Detalle_alquiler> detalles = detalleRepo.findByAlquiler(alquiler);
        boolean todosDevueltos = detalles.stream().allMatch(d -> d.getCantidad() == 0);

        if (todosDevueltos) {
            alquiler.setEstado(Alquileres.EstadoAlquiler.Devuelto);
            alquileresRepo.save(alquiler);
        }
    }
}