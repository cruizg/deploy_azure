package com.cibertec.evaluacionT2_LP2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Peliculas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pelicula;

    @NotBlank(message = "El título no puede ser nulo ni vacío")
    @Column(nullable = false, length = 100)
    private String titulo;

    @NotBlank(message = "El género no puede ser nulo ni vacío")
    @Column(nullable = false, length = 50)
    private String genero;

    @NotNull(message = "El stock no puede ser nulo")
    @Column(nullable = false)
    private Integer stock;

    @NotNull(message = "El precio no puede ser nulo")
    @Column(nullable = false)
    private Double precio;

    @Override
    public String toString() {
        return titulo;
    }
}