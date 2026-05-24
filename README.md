# API de Productos - Lab08

Proyecto Spring Boot para el Laboratorio 08. Expone una API REST para gestionar productos con persistencia en MySQL, validaciones con DTO y pruebas con H2.

## Caracteristicas

- Registro de productos.
- Listado de productos.
- Busqueda de producto por ID.
- Actualizacion de productos.
- Eliminacion de productos.
- Busqueda de productos por nombre.
- Validacion de datos con DTO.
- Manejo global de errores de validacion.
- Creacion automatica de tablas con JPA/Hibernate.

## Tecnologias usadas

- Java 25
- Spring Boot 4
- Spring Web
- Spring Data JPA
- Jakarta Validation
- MySQL
- Maven Wrapper
- H2 para pruebas

## Estructura principal

- `controller`: expone los endpoints REST.
- `service`: contiene la logica de negocio.
- `repository`: acceso a la base de datos.
- `model`: entidades JPA.
- `dto`: objetos para recibir y validar datos.
- `exception`: manejo global de errores.

## Requisitos

- Java 25 instalado.
- MySQL o XAMPP ejecutandose localmente.
- Base de datos configurada en `src/main/resources/application.properties`.

## Configuracion de base de datos

El proyecto usa MySQL en ejecucion normal y H2 para pruebas. Revisa este archivo antes de ejecutar la aplicacion:

```properties
src/main/resources/application.properties
```

La propiedad `spring.jpa.hibernate.ddl-auto=update` permite que Spring Boot cree o actualice las tablas automaticamente.

## Como ejecutar el proyecto

Desde la carpeta `lab08`:

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

## Como ejecutar pruebas

```powershell
.\mvnw.cmd test
```

## Endpoints principales

Base URL local:

```text
http://localhost:8080/api/productos
```

### Listar productos

```http
GET /api/productos
```

### Buscar producto por ID

```http
GET /api/productos/{id}
```

### Crear producto

```http
POST /api/productos
Content-Type: application/json
```

```json
{
  "nombre": "Laptop",
  "descripcion": "Laptop para desarrollo",
  "precio": 2500.0,
  "stock": 10
}
```

### Actualizar producto

```http
PUT /api/productos/{id}
Content-Type: application/json
```

### Eliminar producto

```http
DELETE /api/productos/{id}
```

### Buscar por nombre

```http
GET /api/productos/buscar?nombre=laptop
```

## Respuestas teoricas

### Por que usamos un DTO en lugar de exponer directamente la entidad?

Porque el DTO permite controlar los datos que entran y salen del API, aplicar validaciones y evitar exponer directamente la estructura interna de la entidad.

### Que funcion cumple `@Valid`?

`@Valid` hace que Spring valide automaticamente los datos recibidos en el DTO antes de ejecutar la logica del controlador.

### Que pasaria si eliminamos `@RestControllerAdvice`?

Se perderia el manejo global de errores personalizado. Los errores de validacion ya no tendrian el formato claro definido en el proyecto.

### Que hace `@Autowired`?

`@Autowired` le indica a Spring que inyecte automaticamente una dependencia, por ejemplo un servicio o repositorio.

### Cual es la diferencia entre `save()`, `findById()` y `findAll()`?

- `save()`: guarda o actualiza una entidad.
- `findById()`: busca un registro por su identificador.
- `findAll()`: obtiene todos los registros.

## Autor

Proyecto desarrollado como laboratorio de API REST con Spring Boot.