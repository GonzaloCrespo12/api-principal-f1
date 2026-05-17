# 🏎️ F1 Manager API REST

## 1. Contexto del Proyecto y Modelo de Negocio
Esta API REST no es un simple gestor de datos públicos, sino el "motor" de un sistema SaaS (Software as a Service) diseñado para la gestión de equipos de Fórmula 1. 

**Rol del Usuario:**
Cada usuario autenticado en el sistema asume el rol de **Director de Equipo (Team Principal)** de una escudería específica. La arquitectura garantiza que un usuario solo tenga permisos (Autorización) para gestionar los recursos, pilotos y configuraciones de su propia escudería, manteniendo el ecosistema competitivo aislado y seguro.

---

## 2. Arquitectura de Datos y Decisiones de Diseño
El esquema de la base de datos ha sido diseñado aplicando la Tercera Forma Normal (3NF) para garantizar la integridad referencial y evitar la redundancia de datos. Consta de 8 entidades estratégicamente relacionadas:

### A. Capa de Seguridad y Dominio (Relación 1:1)
* **Usuario y Escudería:** Se separó la lógica de autenticación de la lógica de negocio. La tabla `usuario` maneja credenciales y tokens JWT, vinculándose a una `escuderia` mediante una relación estricta de 1 a 1 (`escuderia_id`). Esto protege los datos del equipo de filtraciones de credenciales.

### B. Integridad Histórica (La Relación Compleja)
* **Entidad Resultado:** Para resolver la relación de Muchos a Muchos entre `Piloto` y `Carrera`, se implementó `Resultado` como una **Entidad de Asociación**. Su principal objetivo arquitectónico es doble:
    1. **Congelar el historial de mercado:** Al guardar el `escuderia_id` en el momento de la carrera, permite que los pilotos sean transferidos de equipo en el futuro (Mercado de Pases) sin alterar el historial del pasado.
    2. **Registro Histórico de Constructores:** Actúa como el libro mayor o historial permanente de la escudería. Permite consultar el rendimiento exacto, posiciones y puntos obtenidos por el equipo en cada Gran Premio a lo largo del tiempo, independientemente de qué pilotos conducían los monoplazas en esa temporada.

### C. Normalización y Tablas Maestras
Para evitar inconsistencias de entrada de datos, se extrajeron atributos clave a tablas maestras:
* **País:** Centraliza la geografía de Circuitos, Escuderías y Pilotos.
* **Motorista:** Catálogo de proveedores de unidades de potencia, evitando errores tipográficos en las escuderías.
* **Número de Piloto:** Gestiona los dorsales permitiendo saber si un número está disponible o ya pertenece a la parrilla actual.

---

## 3. Stack Tecnológico y Reglas de Negocio

### Tecnologías Utilizadas
* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.x
* **Seguridad:** Spring Security con JSON Web Tokens (JWT)
* **Persistencia:** Spring Data JPA / Hibernate
* **Base de Datos:** MySQL
* **Validaciones:** Spring Boot Starter Validation

### Reglas de Negocio Principales (Lógica en Services)
1.  **Aislamiento de Mánager:** Un usuario autenticado solo puede ejecutar métodos `PUT` o `POST` sobre pilotos que pertenezcan a la `escuderia_id` vinculada a su token.
2.  **Asignación de Dorsales:** Al registrar un *nuevo* piloto en el sistema, el motor valida en la tabla `numero_piloto` que el dorsal elegido tenga el estado `esta_disponible = true`. Durante una transferencia de equipo, el piloto simplemente conserva su dorsal personal.
3.  **Inmutabilidad del Pasado:** Los registros en la tabla `Resultado` no se eliminan al transferir a un piloto. Las carreras finalizadas son registros históricos de solo lectura.
4.  **Baja de Pilotos (DELETE):** Solo se permite eliminar físicamente (Hard Delete) a un piloto si este no posee registros asociados en la tabla `Resultado` (ej. un piloto creado por error).
  
### Requisitos API
API 1 (Principal – Completa) debe incluir:

- **Endpoints**
  - GET, POST, PUT y DELETE
  - Uso de Body, Path Variables y Query Params
- **Arquitectura y Componentes**
  - DTOs (Request y Response)
  - Validaciones en DTOs
  - Entidades JPA + Repositories
  - Queries personalizadas
  - Mapper
  - Services con lógica de negocio
- **Relaciones**
  - OneToMany
  - ManyToOne
  - ManyToMany
  - Al menos una relación compleja
- **Seguridad**
  - Autenticación con tokens
  - Endpoints protegidos
  - Manejo básico de usuarios (login/registro)
- **Testing**
  - Tests de Service
  - Tests de Controller
  - Buena cobertura general