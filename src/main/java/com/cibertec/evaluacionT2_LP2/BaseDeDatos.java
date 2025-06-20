package com.cibertec.evaluacionT2_LP2;

import com.cibertec.evaluacionT2_LP2.entity.Clientes;
import com.cibertec.evaluacionT2_LP2.entity.Peliculas;
import com.cibertec.evaluacionT2_LP2.entity.Alquileres;
import com.cibertec.evaluacionT2_LP2.entity.Detalle_alquiler;
import com.cibertec.evaluacionT2_LP2.repository.ClientesRepository;
import com.cibertec.evaluacionT2_LP2.repository.PeliculasRepository;
import com.cibertec.evaluacionT2_LP2.repository.AlquileresRepository;
import com.cibertec.evaluacionT2_LP2.repository.Detalle_alquilerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;

@Component
public class BaseDeDatos implements CommandLineRunner {

    private final ClientesRepository clientesRepo;
    private final PeliculasRepository peliculasRepo;
    private final AlquileresRepository alquileresRepo;
    private final Detalle_alquilerRepository detalleRepo;

    public BaseDeDatos(ClientesRepository clientesRepo, PeliculasRepository peliculasRepo,
                       AlquileresRepository alquileresRepo, Detalle_alquilerRepository detalleRepo) {
        this.clientesRepo = clientesRepo;
        this.peliculasRepo = peliculasRepo;
        this.alquileresRepo = alquileresRepo;
        this.detalleRepo = detalleRepo;
    }

    private void pausar(Scanner sc) {
        System.out.print("Presiona ENTER para continuar...");
        sc.nextLine();
    }

    @Override
    public void run(String... args) {
        try (Scanner sc = new Scanner(System.in)) {
            // Solo inserta si la tabla está vacía
            if (clientesRepo.count() == 0) {
                Clientes c1 = clientesRepo.save(new Clientes(null, "Jose", "jose@email.com"));
                Clientes c2 = clientesRepo.save(new Clientes(null, "Francisco", "francisco@email.com"));
                Clientes c3 = clientesRepo.save(new Clientes(null, "Luis", "luis@email.com"));
                System.out.println("\nClientes creados:");
                System.out.println(c1);
                System.out.println(c2);
                System.out.println(c3);

                pausar(sc);

                Peliculas p1 = peliculasRepo.save(new Peliculas(null, "Matrix", "Ciencia Ficción", 5, 20.0));
                Peliculas p2 = peliculasRepo.save(new Peliculas(null, "Titanic", "Romance", 3, 9.0));
                Peliculas p3 = peliculasRepo.save(new Peliculas(null, "Avengers", "Acción", 7, 30.0));
                System.out.println("\nPelículas creadas:");
                System.out.println(p1);
                System.out.println(p2);
                System.out.println(p3);

                pausar(sc);

                System.out.println("\nBuscando película con id " + p1.getId_pelicula());
                Peliculas peliBuscada = peliculasRepo.findById(p1.getId_pelicula()).orElse(null);
                System.out.println("Encontrada: " + peliBuscada);

                if (peliBuscada != null) {
                    peliBuscada.setStock(10);
                    peliculasRepo.save(peliBuscada);
                    System.out.println("\nStock actualizado: " + peliBuscada);
                }

                pausar(sc);

                Alquileres a1 = alquileresRepo.save(new Alquileres(null, LocalDate.now(), c1, 25.0, Alquileres.EstadoAlquiler.Activo));
                Alquileres a2 = alquileresRepo.save(new Alquileres(null, LocalDate.now(), c2, 40.0, Alquileres.EstadoAlquiler.Devuelto));
                Alquileres a3 = alquileresRepo.save(new Alquileres(null, LocalDate.now(), c3, 15.0, Alquileres.EstadoAlquiler.Retrasado));
                System.out.println("\nAlquileres creados:");
                System.out.println(a1);
                System.out.println(a2);
                System.out.println(a3);

                pausar(sc);

                Detalle_alquiler d1 = detalleRepo.save(new Detalle_alquiler(a1, p1, 2));
                Detalle_alquiler d2 = detalleRepo.save(new Detalle_alquiler(a2, p2, 1));
                Detalle_alquiler d3 = detalleRepo.save(new Detalle_alquiler(a3, p3, 1));
                System.out.println("\nDetalles de alquiler creados:");
                System.out.println(d1);
                System.out.println(d2);
                System.out.println(d3);

                pausar(sc);

                System.out.println("\n>>> DEMO FINALIZADA <<<");
            } else {
                System.out.println("Los datos ya existen. No se insertaron duplicados.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}