# API de Productos y Categorías - Lab08

Proyecto Spring Boot para el Laboratorio 08. Expone una API REST para gestionar productos y categorías con persistencia en MySQL, validaciones con DTO, manejo global de excepciones y pruebas unitarias/integración automatizadas con H2.

## Características

- **Gestión de Productos:** Registro, listado, búsqueda por ID, actualización, eliminación y búsqueda avanzada por nombre.
- **Módulo de Categorías (CRUD Completo):** Creación, lectura, actualización y eliminación de categorías.
- **Validación de Datos:** Uso de DTOs con restricciones estrictas de entrada tanto para productos como para categorías.
- **Manejo Global de Errores:** Respuestas estandarizadas ante fallos de validación (400 Bad Request) y recursos no encontrados (404 Not Found).
- **Pruebas Automatizadas:** Cobertura de pruebas unitarias y de integración bajo un perfil aislado que utiliza H2 en memoria.
- **Creación automática de tablas** mediante JPA/Hibernate.

## Tecnologías usadas

- Java 25
- Spring Boot 4
- Spring Web
- Spring Data JPA
- Jakarta Validation
- MySQL
- Maven Wrapper
- H2 Database (exclusivo para ambiente de pruebas)

## Estructura principal

El proyecto sigue una arquitectura en capas distribuida de la siguiente manera:

- `controller`: Expone los endpoints REST para Productos y Categorías.
- `service`: Contiene la lógica de negocio y las validaciones intermedias.
- `repository`: Interfaces que extienden de `JpaRepository` para el acceso a datos.
- `model`: Entidades JPA (`Producto` y `Categoria`).
- `dto`: Objetos de transferencia de datos (`ProductoDTO` y `CategoriaDTO`) para validar inputs.
- `exception`: Clase `@RestControllerAdvice` para capturar y formatear errores globales.

## Requisitos

- Java 25 instalado.
- MySQL o XAMPP ejecutándose localmente.
- Base de datos configurada en `src/main/resources/application.properties`.

## Configuración de base de datos

El proyecto usa MySQL en ejecución normal y H2 para pruebas. Revisa este archivo antes de ejecutar la aplicación:

```properties
src/main/resources/application.properties