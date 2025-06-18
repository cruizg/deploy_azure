package com.cibertec.evaluacionT2_LP2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alquileres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_alquiler;

    @NotNull
    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes cliente;

    @NotNull
    @Column(nullable = false)
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private EstadoAlquiler estado;

    public enum EstadoAlquiler {
        Activo, Devuelto, Retrasado
    }
}