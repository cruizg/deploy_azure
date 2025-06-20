# Blockbuster Fake - Sistema de Alquiler de Películas

Proyecto de ejemplo para la gestión de alquileres y devoluciones de películas, desarrollado con **Spring Boot**, **Thymeleaf** y **MySQL**.

## Características

- Registro de alquileres de películas por cliente
- Control de stock de películas
- Registro de devoluciones
- Listado y filtrado de alquileres y clientes
- Validaciones de negocio (máximo 2 alquileres activos/retrasados por cliente, stock disponible, etc.)
- Interfaz web amigable con Thymeleaf

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
   git clone [URL_DE_TU_REPOSITORIO]
   cd [NOMBRE_DEL_PROYECTO]
   ```

2. **Configura la base de datos MySQL:**
   - Crea una base de datos llamada `nombre de tu base de datos`

   - Actualiza el archivo `src/main/resources/application.properties` con tus credenciales de MySQL:
     ```
     spring.datasource.username=tu_usuario
     spring.datasource.password=tu_contraseña
     ```

3. **Construye y ejecuta la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

4. **Accede a la aplicación:**
   - Abre tu navegador en [http://localhost:8080/inicio](http://localhost:8080/inicio)

## Estructura del proyecto

```text
evaluacionT2_LP2
│   .gitattributes
│   .gitignore
│   estructura.txt
│   HELP.md
│   mvnw
│   mvnw.cmd
│   pom.xml
│   README.md
│
├── .mvn
│   └── wrapper
│       └── maven-wrapper.properties
│
├── .vscode
│   └── launch.json
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── cibertec
│   │   │           └── evaluacionT2_LP2
│   │   │               │   BaseDeDatos.java
│   │   │               │   EvaluacionT2Lp2Application.java
│   │   │               │
│   │   │               ├── controller
│   │   │               │   AlquileresController.java
│   │   │               │   ClientesController.java
│   │   │               │   Detalle_alquilerController.java
│   │   │               │   DevolucionController.java
│   │   │               │   HomeController.java
│   │   │               │   PeliculasController.java
│   │   │               │
│   │   │               ├── entity
│   │   │               │   Alquileres.java
│   │   │               │   Clientes.java
│   │   │               │   Detalle_alquiler.java
│   │   │               │   Peliculas.java
│   │   │               │
│   │   │               └── repository
│   │   │                   AlquileresRepository.java
│   │   │                   ClientesRepository.java
│   │   │                   Detalle_alquilerRepository.java
│   │   │                   PeliculasRepository.java
│   │   │
│   │   └── resources
│   │       │   application.properties
│   │       │
│   │       ├── static
│   │       │   movietime.jpg
│   │       │
│   │       └── templates
│   │           clientes.html
│   │           devolucion.html
│   │           inicio.html
│   │           listado_alquileres.html
│   │           peliculas.html
│   │           registrar_alquiler.html
│   │           sidebar.html
│   │
│   └── test
│       └── java
│           └── com
│               └── cibertec
│                   └── evaluacionT2_LP2
│                       EvaluacionT2Lp2ApplicationTests.java
│
└── target
    └── (archivos y carpetas generados por Maven)
```

## Autor

- Gabriela Mayta Prinque

---

