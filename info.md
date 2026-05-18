# 🏎️ F1 Team Manager - API REST (Sistema SaaS)

Este proyecto comprende el desarrollo del backend completo para una plataforma SaaS (Software as a Service) orientada a la gestión estratégica de escuderías de Fórmula 1. La solución implementa una arquitectura desacoplada en capas, mecanismos avanzados de seguridad perimetral por tokens y aislamiento estricto de recursos por director de equipo.

---

## 📋 1. Contexto del Proyecto y Modelo de Negocio

La API no actúa como un mero catálogo de lectura de datos, sino como un sistema transaccional donde cada usuario autenticado asume el rol de **Director de Equipo (Team Principal)** de una escudería específica.

### 🛡️ Regla de Oro: Aislamiento de Mánager
La arquitectura de seguridad garantiza el aislamiento total de la competencia. El sistema extrae el identificador único del equipo (`escuderia_id`) directamente desde el token **JWT** enviado en las cabeceras HTTP de la petición. Ningún mánager puede registrar, modificar o eliminar pilotos pertenecientes a escuderías rivales, protegiendo los activos estratégicos de la competencia.

---

## 🏛️ 2. Arquitectura de Software y Patrón DTO

La aplicación se rige bajo una estricta **Arquitectura Multicapa** (Controller -> Service -> Repository). Para proteger el contrato de la API y evitar el acoplamiento directo entre la base de datos y la presentación, se implementaron **Data Transfer Objects (DTOs)**:

* **`PilotoRequestDTO`:** Formulario de entrada de datos equipado con validaciones estrictas (`@NotBlank`, `@Size`, `@Min`). Protege al motor de la aplicación contra el ingreso de datos corruptos.
* **`PilotoResponseDTO`:** Objeto de salida limpio destinado al cliente. Transforma relaciones pesadas en tipos de datos legibles.
* **Prevención de JSON Infinito:** El uso de DTOs planos neutraliza los errores de serialización cíclica generados por relaciones bidireccionales de Hibernate, optimizando el tráfico de red.

---

## 📊 3. Diseño de Base de Datos e Integridad Histórica

El esquema de persistencia se modeló aplicando rigurosamente la **Tercera Forma Normal (3NF)** en MySQL a través de 8 entidades. Destacan dos decisiones arquitectónicas:

1. **Seguridad Separada (1:1):** La tabla `usuario` procesa credenciales y se vincula mediante una clave foránea única (`escuderia_id`) a su respectiva escudería.
2. **Entidad de Asociación (`Resultado`):** Resuelve la relación N:M entre `Piloto` y `Carrera`. Al persistir el `escuderia_id` en cada resultado, permite que un piloto cambie de escudería en el futuro (Mercado de Pases) **sin alterar el historial de puntos** de su antiguo equipo. Las carreras del pasado permanecen inmutables.

---

## ⚙️ 4. Reglas de Negocio Implementadas (Capa Service)

1. **Aislamiento de Mánager:** Comprueba que la escudería del piloto destino coincida de forma unívoca con el ID del token JWT.
2. **Reserva Automática de Dorsal:** Valida la disponibilidad del dorsal en la tabla maestra (`numero_piloto`). Si está libre, se le asigna y pasa a `esta_disponible = false`.
3. **Política Segura de Eliminación (DELETE):** Se ejecuta una baja física (*Hard Delete*) sobre un piloto únicamente si este no cuenta con historial de carreras. Si ya compitió, el sistema lanza una excepción para preservar la integridad estadística.

---

## 🛠️ 5. Stack Tecnológico Utilizado

* **Lenguaje:** Java 21 
* **Framework:** Spring Boot 3.x
* **Seguridad:** Spring Security con JSON Web Tokens (JWT)
* **Motor ORM:** Spring Data JPA / Hibernate
* **Base de Datos:** MySQL
* **Testing:** JUnit 5, Mockito y Spring MockMvc

---

## 🚀 6. Guía de Ejecución (Para el Evaluador)

Siga estos pasos para inicializar la aplicación en su entorno de desarrollo local:

### A. Requisitos Previos
* **Java 21** instalado y configurado.
* Instancia activa de **MySQL Server** (ej. phpMyAdmin o Workbench).

### B. Configuración de Base de Datos
Localice el archivo `src/main/resources/application.properties` y adapte las credenciales de conexión según su servidor local:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/f1_manager?useSSL=false&serverTimezone=UTC
spring.datasource.username=su_usuario_local
spring.datasource.password=su_contraseña_local

# Estrategia automática: Hibernate creará las 8 tablas por usted al iniciar
spring.jpa.hibernate.ddl-auto=update

C. Compilación y Arranque
Abra una terminal en la raíz del proyecto y ejecute:

Bash
mvn spring-boot:run
Verifique que la terminal indique Tomcat started on port 8080.

🎛️ 7. Endpoints Principales
API Principal (Pilotos) - Arquitectura Completa
Requiere autenticación activa (Authorization: Bearer <TOKEN_JWT>).

POST /api/auth/login: Envíe las credenciales. Recibirá el Token JWT en la respuesta.

GET /api/pilotos: Lista los pilotos de la escudería del mánager logueado. (Código 200 OK).

POST /api/pilotos: Registra un piloto validando los DTOs. (Código 201 Created).

DELETE /api/pilotos/{id}: Elimina un piloto si no viola la inmutabilidad histórica. (Código 204 No Content).

🧪 8. Pruebas Automatizadas (Testing)
La integridad del motor backend se verifica mediante un laboratorio de pruebas automatizadas:

Bash
mvn test
Pruebas de Servicio (Unitarias): Uso de Mockito para inyectar repositorios simulados y validar la lógica de eliminación y reglas de negocio.

Pruebas de Controlador (Integración): Uso de MockMvc y @WithMockUser para simular peticiones HTTP y saltear la barrera de Spring Security de forma controlada.