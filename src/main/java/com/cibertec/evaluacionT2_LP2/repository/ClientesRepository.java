package com.cibertec.evaluacionT2_LP2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cibertec.evaluacionT2_LP2.entity.Clientes;
import java.util.Optional;
import java.util.List;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {
    Optional<Clientes> findByEmail(String email);
    List<Clientes> findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCase(String nombre, String email);
}