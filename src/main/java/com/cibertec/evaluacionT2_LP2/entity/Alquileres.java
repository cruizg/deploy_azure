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

    @OneToMany(mappedBy = "alquiler", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private java.util.Set<Detalle_alquiler> detalles = new java.util.HashSet<>();

    public Alquileres(Long id_alquiler, LocalDate fecha, Clientes cliente, Double total, EstadoAlquiler estado) {
        this.id_alquiler = id_alquiler;
        this.fecha = fecha;
        this.cliente = cliente;
        this.total = total;
        this.estado = estado;
    }

    public enum EstadoAlquiler {
        Activo, Devuelto, Retrasado
    }
}