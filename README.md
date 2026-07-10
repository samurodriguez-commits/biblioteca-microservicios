# Biblioteca Microservicios

Sistema de gestión de biblioteca desarrollado con arquitectura de microservicios en Spring Boot que permite gestionar préstamos, multas, notificaciones y el catálogo de libros.

## Integrantes

- Sebastian Leon - MicroservicioPrestamo-Multa
- Samuel Enrique Rodriguez - MicroservicioLibro y NotificacionMicroservicio
- Diego Ignacio Alexander Bustamante - ApiGateway y EurekaServer

## Microservicios

| Microservicio | Puerto |
|---------------|--------|
| EurekaServer | 8761 |
| ApiGateway | 8080 |
| MicroservicioLibro | 8081 |
| NotificacionMicroservicio | 8082 |
| MicroservicioPrestamo-Multa | 8083 |

## Ejecución Local

### Requisitos
- Java 21
- Maven
- MySQL

### Pasos

1. Crear bases de datos en MySQL:
```sql
CREATE DATABASE db_microservicio_libro_dev;
CREATE DATABASE db_notificacion_usuario_dev;
CREATE DATABASE db_prestamo_multa_dev;
```

2. Ejecutar en orden:
   - EurekaServer
   - ApiGateway
   - MicroservicioLibro
   - NotificacionMicroservicio
   - MicroservicioPrestamo-Multa/biblioteca-main

3. Panel Eureka: http://localhost:8761

## Ejecución con Docker

### Requisitos
- Docker
- Docker Compose

### Pasos

1. Clonar el repositorio:
```bash
git clone https://github.com/samurodriguez-commits/biblioteca-microservicios.git
cd biblioteca-microservicios
```

2. (Opcional) Editar contraseña de MySQL en `.env`:
```
MYSQL_ROOT_PASSWORD=Admin123
```

3. Construir y levantar todos los servicios:
```bash
docker-compose up --build
```

4. Esperar unos minutos a que todos los servicios se registren en Eureka.

5. Panel Eureka: http://localhost:8761

6. Para detener los servicios:
```bash
docker-compose down
```

### Servicios en Docker

| Servicio | URL |
|----------|-----|
| ApiGateway | http://localhost:8080 |
| EurekaServer | http://localhost:8761 |
| MicroservicioLibro Swagger | http://localhost:8081/swagger-ui.html |
| NotificacionMicroservicio Swagger | http://localhost:8082/swagger-ui.html |
| MicroservicioPrestamo-Multa Swagger | http://localhost:8083/swagger-ui.html |

### Comandos útiles

Ver logs de un servicio específico:
```bash
docker-compose logs -f microservicio-libro
```

Reconstruir un solo servicio:
```bash
docker-compose up --build -d microservicio-libro
```

## Swagger

- MicroservicioLibro: http://localhost:8081/swagger-ui.html
- NotificacionMicroservicio: http://localhost:8082/swagger-ui.html
- MicroservicioPrestamo-Multa: http://localhost:8083/swagger-ui.html

## Rutas del Gateway

- GET/POST /api/libros
- GET/POST /api/prestamos
- GET/POST /api/multas
- GET/POST /api/notificaciones
