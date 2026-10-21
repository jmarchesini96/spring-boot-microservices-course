# Curso: Microservicios y APIs REST con Spring Boot, OAuth2 y Docker

Este repositorio contiene todos los ejercicios, proyectos y configuraciones desarrollados a lo largo del curso.

## 🛠️ Tecnologías utilizadas
* Java 17+ / Spring Boot
* Spring Security & OAuth2
* Docker & Docker Compose
* Spring Cloud (Eureka, API Gateway, etc.)

## 📁 Estructura del Proyecto
(Aquí irás listando tus microservicios conforme los crees)

## 🚀 Documentación de las APIs (Swagger)

Cuando los microservicios estén corriendo localmente, puedes acceder a la interfaz interactiva de Swagger y revisar los endpoints desde el navegador utilizando los siguientes puertos:

### 👤 Microservicio: Customer (Puerto 8080)
* 🌍 **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* 📄 **OpenAPI JSON Spec:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### 💳 Microservicio: Billing (Puerto 8081)
* 🌍 **Swagger UI:** [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
* 📄 **OpenAPI JSON Spec:** [http://localhost:8081/v3/api-docs](http://localhost:8081/v3/api-docs)

### 💳 Microservicio: Product (Puerto 8082)
* 🌍 **Swagger UI:** [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html)
* 📄 **OpenAPI JSON Spec:** [http://localhost:8082/v3/api-docs](http://localhost:8082/v3/api-docs)

### 💳 Microservicio: Transaction (Puerto 8083)
* 🌍 **Swagger UI:** [http://localhost:8083/swagger-ui/index.html](http://localhost:8083/swagger-ui/index.html)
* 📄 **OpenAPI JSON Spec:** [http://localhost:8083/v3/api-docs](http://localhost:8083/v3/api-docs)

## 🗄️ Consola H2 Database

Cuando los microservicios estén corriendo localmente, puedes acceder a la consola web de H2 para inspeccionar la base de datos en memoria:

### 👤 Microservicio: Customer (Puerto 8080)
* 🗄️ **H2 Console:** [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
* 🔗 **JDBC URL:** `jdbc:h2:mem:customer`
* 👤 **Usuario:** `sa`
* 🔑 **Contraseña:** *(vacía)*

### 💳 Microservicio: Billing (Puerto 8081)
* 🗄️ **H2 Console:** [http://localhost:8081/h2-console](http://localhost:8081/h2-console)
* 🔗 **JDBC URL:** `jdbc:h2:mem:billing`
* 👤 **Usuario:** `sa`
* 🔑 **Contraseña:** *(vacía)*

### 📦 Microservicio: Product (Puerto 8082)
* 🗄️ **H2 Console:** [http://localhost:8082/h2-console](http://localhost:8082/h2-console)
* 🔗 **JDBC URL:** `jdbc:h2:mem:product`
* 👤 **Usuario:** `sa`
* 🔑 **Contraseña:** *(vacía)*

### 🔄 Microservicio: Transaction (Puerto 8083)
* 🗄️ **H2 Console:** [http://localhost:8083/h2-console](http://localhost:8083/h2-console)
* 🔗 **JDBC URL:** `jdbc:h2:mem:transaction`
* 👤 **Usuario:** `sa`
* 🔑 **Contraseña:** *(vacía)*

> **Nota:** La consola H2 debe estar habilitada en cada microservicio (`spring.h2.console.enabled=true` en `application.properties`). Al acceder, usa la "JDBC URL" correspondiente y deja el usuario/contraseña como se indica arriba.