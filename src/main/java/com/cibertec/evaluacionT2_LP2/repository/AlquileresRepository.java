package com.cibertec.evaluacionT2_LP2.repository;

import com.cibertec.evaluacionT2_LP2.entity.Alquileres;
import com.cibertec.evaluacionT2_LP2.entity.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlquileresRepository extends JpaRepository<Alquileres, Long> {

    long countByClienteAndEstadoIn(Clientes cliente, List<Alquileres.EstadoAlquiler> estados);

    @Query("SELECT DISTINCT a.cliente FROM Alquileres a WHERE a.estado = com.cibertec.evaluacionT2_LP2.entity.Alquileres$EstadoAlquiler.Activo OR a.estado = com.cibertec.evaluacionT2_LP2.entity.Alquileres$EstadoAlquiler.Retrasado")
    List<Clientes> findClientesConAlquilerActivoORRetrasado();
}