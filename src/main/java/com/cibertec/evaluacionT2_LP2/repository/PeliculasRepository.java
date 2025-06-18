package com.cibertec.evaluacionT2_LP2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cibertec.evaluacionT2_LP2.entity.Peliculas;
import java.util.Optional;
import java.util.List;

public interface PeliculasRepository extends JpaRepository<Peliculas, Long> {
    Optional<Peliculas> findByTituloIgnoreCase(String titulo);
    List<Peliculas> findByTituloContainingIgnoreCaseOrGeneroContainingIgnoreCase(String titulo, String genero);
}