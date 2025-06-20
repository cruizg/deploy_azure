package com.cibertec.evaluacionT2_LP2.service;

import com.cibertec.evaluacionT2_LP2.entity.Detalle_alquiler;
import com.cibertec.evaluacionT2_LP2.entity.Alquileres;
import com.cibertec.evaluacionT2_LP2.entity.Peliculas;
import com.cibertec.evaluacionT2_LP2.repository.Detalle_alquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleAlquilerService {

    @Autowired
    private Detalle_alquilerRepository detalleRepo;

    public List<Detalle_alquiler> listarTodos() {
        return detalleRepo.findAll();
    }

    public List<Detalle_alquiler> buscarPorFiltro(String filtro) {
        return detalleRepo.buscarPorClientePeliculaOEstado(filtro);
    }

    public List<Detalle_alquiler> buscarPorAlquiler(Long idAlquiler) {
        return detalleRepo.findByAlquilerIdAlquiler(idAlquiler);
    }

    public Detalle_alquiler buscarPorAlquilerYPelicula(Alquileres alquiler, Peliculas pelicula) {
        return detalleRepo.findByAlquilerAndPelicula(alquiler, pelicula);
    }

    public void guardar(Detalle_alquiler detalle) {
        detalleRepo.save(detalle);
    }

    public long contarPorAlquiler(Alquileres alquiler) {
        return detalleRepo.countByAlquiler(alquiler);
    }
}