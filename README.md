Sistema de Biblioteca



Descripción

Sistema de gestión de biblioteca desarrollado con arquitectura de microservicios en Spring Boot que permite tanto gestionar préstamos, multas, las notificaciones y el catálogo de libros.



Integrantes

Sebastian Leon - MicroservicioPrestamo-Multa

Samuel Enrique Rodriguez - MicroservicioLibro y NotificacionMicroservicio

Diego Ignacio Alexander Bustamante - ApiGateway y EurekaServer



Microservicios

EurekaServer (puerto 8761) - Servidor de descubrimiento

ApiGateway (puerto 8080) - Puerta de entrada centralizada

MicroservicioLibro (puerto 8081) - Gestión de libros

NotificacionMicroservicio (puerto 8082) - Gestión de notificaciones

MicroservicioPrestamo-Multa (puerto 8083) - Gestión de préstamos y multas



Rutas del Gateway

GET/api/prestamos

POST/api/prestamos

GET/api/multas

POST/api/multas

GET/api/libros

POST/api/libros

GET/api/notificaciones

POST/api/notificaciones



Swagger

MicroservicioLibro: http://localhost:8081/swagger-ui.html

NotificacionMicroservicio: http://localhost:8082/swagger-ui.html

MicroservicioPrestamo-Multa: http://localhost:8083/swagger-ui.html



Ejecución Local

Requisitos: Java 21, Maven, MySQL



1\. Clonar repositorio:

git clone https://github.com/sebasleon-007/biblioteca-microservicios.git



2\. Crear bases de datos en MySQL:

CREATE DATABASE db\_microservicio\_libro\_dev;

CREATE DATABASE db\_notificacion\_usuario\_dev;

CREATE DATABASE db\_prestamo\_multa\_dev;



3\. Ejecutar en orden:

EurekaServer

ApiGateway

MicroservicioLibro

NotificacionMicroservicio

MicroservicioPrestamo-Multa/biblioteca-main



4\. Panel Eureka: http://localhost:8761

