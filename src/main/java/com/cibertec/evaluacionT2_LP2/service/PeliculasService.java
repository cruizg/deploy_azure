package com.cibertec.evaluacionT2_LP2.service;

import com.cibertec.evaluacionT2_LP2.entity.Peliculas;
import com.cibertec.evaluacionT2_LP2.repository.PeliculasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PeliculasService {

    @Autowired
    private PeliculasRepository peliculasRepo;

    public List<Peliculas> listarTodas() {
        return peliculasRepo.findAll();
    }

    public List<Peliculas> buscarPorTituloOGenero(String filtro) {
        return peliculasRepo.findByTituloContainingIgnoreCaseOrGeneroContainingIgnoreCase(filtro, filtro);
    }

    public Optional<Peliculas> buscarPorId(Long id) {
        return peliculasRepo.findById(id);
    }

    public Optional<Peliculas> buscarPorTitulo(String titulo) {
        return peliculasRepo.findByTituloIgnoreCase(titulo);
    }

    public Peliculas guardar(Peliculas pelicula) {
        return peliculasRepo.save(pelicula);
    }

    public void eliminarPorId(Long id) {
        peliculasRepo.deleteById(id);
    }
}