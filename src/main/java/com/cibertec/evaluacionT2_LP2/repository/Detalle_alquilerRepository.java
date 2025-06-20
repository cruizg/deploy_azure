package com.cibertec.evaluacionT2_LP2.repository;

import com.cibertec.evaluacionT2_LP2.entity.Detalle_alquiler;
import com.cibertec.evaluacionT2_LP2.entity.Detalle_alquiler.DetalleAlquilerId;
import com.cibertec.evaluacionT2_LP2.entity.Alquileres;
import com.cibertec.evaluacionT2_LP2.entity.Peliculas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Detalle_alquilerRepository extends JpaRepository<Detalle_alquiler, DetalleAlquilerId> {
    @Query("SELECT d FROM Detalle_alquiler d WHERE " +
           "LOWER(d.alquiler.cliente.nombre) LIKE %:filtro% OR " +
           "LOWER(d.pelicula.titulo) LIKE %:filtro% OR " +
           "LOWER(d.alquiler.estado) LIKE %:filtro%")
    List<Detalle_alquiler> buscarPorClientePeliculaOEstado(@Param("filtro") String filtro);

    @Query("SELECT d FROM Detalle_alquiler d WHERE d.alquiler.id_alquiler = :idAlquiler")
    List<Detalle_alquiler> findByAlquilerIdAlquiler(@Param("idAlquiler") Long idAlquiler);

    // Películas alquiladas por un cliente en alquileres activos o retrasados
    @Query("SELECT DISTINCT d.pelicula FROM Detalle_alquiler d WHERE d.alquiler.cliente.id_cliente = :clienteId AND (d.alquiler.estado = com.cibertec.evaluacionT2_LP2.entity.Alquileres$EstadoAlquiler.Activo OR d.alquiler.estado = com.cibertec.evaluacionT2_LP2.entity.Alquileres$EstadoAlquiler.Retrasado)")
    List<Peliculas> findPeliculasAlquiladasPorCliente(@Param("clienteId") Long clienteId);

    // Buscar el alquiler activo/retrasado por cliente y película
    @Query("SELECT d.alquiler FROM Detalle_alquiler d WHERE d.alquiler.cliente.id_cliente = :clienteId AND d.pelicula.id_pelicula = :peliculaId AND (d.alquiler.estado = com.cibertec.evaluacionT2_LP2.entity.Alquileres$EstadoAlquiler.Activo OR d.alquiler.estado = com.cibertec.evaluacionT2_LP2.entity.Alquileres$EstadoAlquiler.Retrasado)")
    Alquileres findAlquilerActivoORRetrasadoPorClienteYPelicula(@Param("clienteId") Long clienteId, @Param("peliculaId") Long peliculaId);

    // Buscar el detalle de alquiler por alquiler y película
    Detalle_alquiler findByAlquilerAndPelicula(Alquileres alquiler, Peliculas pelicula);

    // Contar detalles por alquiler
    long countByAlquiler(Alquileres alquiler);

    // Buscar todos los detalles por alquiler
    List<Detalle_alquiler> findByAlquiler(Alquileres alquiler);
}