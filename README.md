# Sistema de Gestión de Tickets - Backend

## Descripción General

Este módulo corresponde al *backend* del sistema de gestión de tickets de soporte técnico.
Está desarrollado con *Spring Boot* y proporciona una API REST que permite la creación, consulta, actualización y gestión de tickets, usuarios, categorías, comentarios y archivos adjuntos.

El objetivo es ofrecer una solución robusta y escalable que administre toda la lógica de negocio y persistencia de datos del sistema.

---

## Objetivos

* Implementar una API RESTful segura y bien estructurada.
* Permitir la creación y seguimiento de tickets de soporte técnico.
* Gestionar usuarios con diferentes roles (administrador, técnico, usuario).
* Controlar categorías, comentarios y adjuntos relacionados con cada ticket.

---


## Entidades Principales

* *User:* representa a los usuarios del sistema (administrador, técnico o usuario final).
* *Ticket:* entidad principal que almacena información sobre incidencias.
* *Category:* clasifica los tickets según el tipo de problema.
* *Comment:* registra comentarios o seguimientos de cada ticket.
* *Attachment:* almacena archivos asociados a un ticket.

---

## Endpoints Principales

| Método | Endpoint            | Descripción                    |
| ------ | ------------------- | ------------------------------ |
| POST   | /api/auth/login   | Iniciar sesión                 |
| GET    | /api/tickets      | Listar tickets                 |
| POST   | /api/tickets      | Crear nuevo ticket             |
| PUT    | /api/tickets/{id} | Actualizar estado o asignación |
| GET    | /api/categories   | Listar categorías              |
| POST   | /api/comments     | Agregar comentario a un ticket |

---

## Seguridad

El proyecto implementa autenticación basada en *JWT (JSON Web Tokens)* y autorización por roles para proteger los recursos de la API.
También incluye configuración de *CORS* para permitir el acceso desde el frontend Angular.

---

## Configuración y Ejecución

### Requisitos previos

* Java 21
* Maven 3.8+
* MySQL

### Pasos

1. Clonar el repositorio

   bash
   git clone https://github.com/usuario/ticket-system-backend.git
   
2. Configurar las variables de conexión en application.properties
3. Ejecutar el proyecto

   bash
   mvn spring-boot:run
   
4. La API estará disponible en:

   
   http://localhost:8080
   

---
