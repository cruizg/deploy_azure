package com.cibertec.evaluacionT2_LP2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Entity
@IdClass(Detalle_alquiler.DetalleAlquilerId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Detalle_alquiler {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_alquiler", nullable = false)
    private Alquileres alquiler;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Peliculas pelicula;

    @NotNull
    @Column(nullable = false)
    private Integer cantidad;

    @Override
    public String toString() {
        return "Detalle_alquiler [alquiler=" + (alquiler != null ? alquiler.getId_alquiler() : null) +
               ", pelicula=" + (pelicula != null ? pelicula.getId_pelicula() : null) +
               ", cantidad=" + cantidad + "]";
    }

    // Clase estática para la clave compuesta
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetalleAlquilerId implements Serializable {
        private Long alquiler;
        private Long pelicula;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DetalleAlquilerId that = (DetalleAlquilerId) o;
            return java.util.Objects.equals(alquiler, that.alquiler) &&
                   java.util.Objects.equals(pelicula, that.pelicula);
        }

        @Override
        public int hashCode() {
            return java.util.Objects.hash(alquiler, pelicula);
        }
    }
}