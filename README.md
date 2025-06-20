# Blockbuster Fake - Sistema de Alquiler de Películas

Proyecto de ejemplo para la gestión de alquileres y devoluciones de películas, desarrollado con **Spring Boot**, **Thymeleaf** y **MySQL**.



## Pantallla de Inicio

![Pantalla de Inicio](https://github.com/user-attachments/assets/5e17add5-96da-4db4-ae10-7c33c3f07e44)

## Descripción

Este proyecto permite registrar clientes, películas, alquileres y devoluciones, gestionando el stock de películas y el historial de transacciones. Incluye validaciones, filtros y una interfaz moderna basada en **Thymeleaf**.

## Características

- Registro, edición y eliminación de clientes.
- Registro y gestión de películas.
- Registro de alquileres con control de stock.
- Registro de devoluciones parciales y totales.
- Listado y filtrado de alquileres y devoluciones.
- Validaciones de negocio (máximo de alquileres activos/retrasados por cliente, stock disponible, etc).
- Mensajes de éxito y error en la interfaz.
- Interfaz web amigable y moderna.

## Tecnologías utilizadas

- Java 17
- Spring Boot 3.5.x
- Spring Data JPA
- Thymeleaf
- MySQL
- Maven
- Lombok

## Configuración del proyecto

1. **Clona el repositorio:**
   ```bash
   git clone [https://github.com/gabrielamapri/evaluacionT2_LP2]
   cd [evaluacionT2_LP2]
   ```

2. **Configura la base de datos MySQL:**
   - Crea una base de datos llamada `nombre de tu base de datos`

   - Actualiza el archivo `src/main/resources/application.properties` con tus credenciales de MySQL:
     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/nombre_de_tu_base_de_datos?useSSL=false&serverTimezone=UTC
     spring.datasource.username=tu_usuario
     spring.datasource.password=tu_contraseña
     ```

3. **Construye y ejecuta la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

4. **Accede a la aplicación:**
   - Abre tu navegador en [http://localhost:8080/inicio](http://localhost:8080/inicio)

## Estructura general del proyecto

```text
evaluacionT2_LP2
│   [pom.xml]
│   [README.md]
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── cibertec
│   │   │           └── evaluacionT2_LP2
│   │   │               ├── controller
│   │   │               ├── entity
│   │   │               ├── repository
│   │   │               └── service
│   │   └── resources
│   │       │   application.properties
│   │       ├── static
│   │       │   slide1.jpg
│   │       │   slide2.jpg
│   │       │   slide3.jpg
│   │       │   slide4.jpg
│   │       └── templates
│   │           clientes.html
│   │           devolucion.html
│   │           inicio.html
│   │           listado_alquileres.html
│   │           peliculas.html
│   │           registrar_alquiler.html
│   │           sidebar.html
│   └── test
│       └── java
│           └── com
│               └── cibertec
│                   └── evaluacionT2_LP2
│                       EvaluacionT2Lp2ApplicationTests.java
```
## Capturas de Pantalla

   Registro de Clientes
   ![image](https://github.com/user-attachments/assets/613e088a-8e32-4e23-9768-c3082c9723db)

   Registro de Películas
   ![image](https://github.com/user-attachments/assets/7f53c891-7694-49a6-8ee5-2808346ce6c3)

   Registro de Alquiler
   ![image](https://github.com/user-attachments/assets/9b07e8b7-7994-421b-9d14-c662768b3845)

   Registro de Devolución
   ![image](https://github.com/user-attachments/assets/978931b7-ba19-4b10-babc-bacc4cac2f49)

   Listado de Alquileres
   ![image](https://github.com/user-attachments/assets/3ad964b2-2bf0-4d58-ae10-afc3f30b3d10)


## Autor

- Gabriela Mayta Prinque

---

**¡Gracias por probar Blockbuster Fake!**
