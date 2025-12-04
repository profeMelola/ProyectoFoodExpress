# FoodExpress 

Simula una plataforma de pedidos de comida a domicilio tipo Uber Eats /
Glovo.

![alt text](image.png)


Entorno completo con dos apps Spring:

- **FoodExpress API:** REST + JPA + JWT.
- **FoodExpress Web MVC:** aplicación Spring Boot MVC + Thymeleaf que consume la API.


# 1. FoodExpress I

https://github.com/profeMelola/DWES-03-2025-26/tree/main/EJERCICIOS/FoodExpress

---
# 2.  FoodExpres II

## BLOQUE A

### A.1. Spring I
- Explicación del examen.
- Introducción a Testing.

### A.2. Mejoras

#### Evitar F5-doble-submit (Post–Redirect–Get). FlashAttributes

![alt text](image-20.png)

![alt text](image-21.png)

**Get para mostrar formulario:**

```
@GetMapping("/create")
public String showForm(Model model, Principal principal) {
    model.addAttribute("username", principal.getName());
    model.addAttribute("restaurant", new RestaurantDTO());
    model.addAttribute("mode", "create");
    return "restaurants/restaurant-form";
}


```


**POST con PRG (SIN F5 DUPLICADO)**

```
@PostMapping("/create")
public String create(
        @ModelAttribute("restaurant") RestaurantDTO restaurantDTO,
        RedirectAttributes redirectAttributes) {

    RestaurantDTO saved = restaurantsService.create(restaurantDTO);

    // Se guarda temporalmente para el redirect
    redirectAttributes.addFlashAttribute("restaurant", saved);
    redirectAttributes.addFlashAttribute("success", true);

    return "redirect:/restaurants/create-success";
}

```

**Vista create-success**

```
<h1>Restaurante creado correctamente</h1>

<div th:if="${success}">
    <p>Nombre: <span th:text="${restaurant.name}"></span></p>
    <p>Dirección: <span th:text="${restaurant.address}"></span></p>
</div>

<a href="/restaurants/create">Crear otro</a>

```

#### Componentización. Fragmentos

- restaurant-form.html
- form-errors.html (fragment)

```
<div th:replace="~{fragments/form-errors :: error(field='phone')}"></div>

```




## BLOQUE B

### B.1 Paginación y sorting en Spring Data
- Pageable, PageRequest
- sort=name,asc
- Pasarlo a Thymeleaf con page.number, page.totalPages, etc.
- Hacer paginación en MVC + API REST
- Ejemplo práctico con el listado de restaurantes o pedidos

### B.2 JPQL + Query Methods

- Save vs SaveAll
- Introducción a JPQL 
- Ejemplos con joins
- Resolver queries complejas del examen
- Cuándo usar @Query
- Métodos por convención
- Búsquedas con filtros
- Transacciones en JPA/Spring (@Transactional): qué son, para qué sirven y cuándo se aplican

## BLOQUE C

### C.1 Recuperar DTO y ErrorDTO usando WebClient

- retrieve() vs exchangeToMono()
- Procesar error 400/404 de forma limpia
- Mapear ErrorDTO
- Integrarlo en MVC (mostrar error en pantalla)

### C.2 JJWT

- Refresh Tokens
- Expiración real en el servidor (control server-side)
- Blacklist de tokens al cerrar sesión (opcional)

**POST /auth/login**
- Recibe username/password
- Genera access token (corto)
- Genera refresh token (largo)
- Guarda refresh token en BD

**POST /auth/refresh**

- Recibe refresh token
- Valida en BD
- Genera nuevo access token
- (Opcional: regenerar también el refresh token)

**POST /auth/logout**

- Borra el refresh token de BD
- Opcional: añade el access token actual a blacklist

```
| Token         | Duración      | Uso                        |
| ------------- | ------------- | -------------------------- |
| Access Token  | 10–15 minutos | Llamadas normales a la API |
| Refresh Token | 7–30 días     | Renovar sesión             |

```

### C.3 Llamada a API pública

- Ejemplo con TheMealDB API o DogCEO
- Llamada con WebClient
- Convertir JSON a DTO
- Mostrarlos en Thymeleaf
- Endpoint /public/meals?name=burger

## BLOQUE D — Upload/Storage de ficheros, caché, loggin, email..

### D.1 Upload de imágenes / MultipartFile

- Formulario con ```<input type="file">```
- Manejo en controller (MultipartFile)
- Guardarlo en /uploads o en base64
- Mostrarlo en la web.
- Muy útil para el proyecto FoodExpress (fotos de restaurantes, platos…).

### D.2 Caching + Logging

- @Cacheable para búsquedas típicas
- Logging con SLF4J. AOP (Programación Orientada a Aspectos)
    - AOP permite ejecutar código automáticamente antes o después de ciertos métodos, sin modificar esos métodos.
- Buena práctica: logs de error, info, debug.

### D.3 Envío de email básico

- Spring Mail sender
- Plantilla simple
- Ejemplo: email de confirmación de registro

### D.4 Spring Data Rest + Swagger/OpenAPI

- Mostrar cómo Spring genera una API REST completa
- Paginated + HATEOAS
- Integrarlo con Swagger UI


---

# 4. Dockerizar todo el entorno
`
Un docker-compose.yml con 3 contenedores principales:

| Servicio               | Descripción                                                                       | Imagen base               |
| ---------------------- | --------------------------------------------------------------------------------- | ------------------------- |
| 🧩 **foodexpress-api** | La API REST (Spring Boot, puerto 8081). Expone endpoints REST + JWT + JPA.        | `openjdk:21-jdk-slim`     |
| 🌐 **foodexpress-web** | La aplicación MVC (Thymeleaf, puerto 8080). Consume la API vía HTTP.              | `openjdk:21-jdk-slim`     |
| 🗄️ **foodexpress-db** | Base de datos relacional persistente (reemplaza H2) → **PostgreSQL** o **MySQL**. | `postgres:16` / `mysql:8` |

---

# 5. Microservicios

| Tema            | API REST (monolito) | Microservicios            |
| --------------- | ------------------- | ------------------------- |
| Nº aplicaciones | 1                   | Muchas ✖                  |
| BD              | Una                 | Una por servicio          |
| Seguridad       | Simple              | Compleja (gateway + auth) |
| Comunicación    | Interna, rápida     | HTTP, eventos             |
| Escalado        | Completo            | Por servicio              |
| Deploy          | Muy fácil           | Complejo                  |
| Complejidad     | Baja                | Alta                      |


```
                    +-----------------------------+
                    |    API Gateway (Spring)     |
                    |  http://api.foodexpress.com |
                    +-------------+---------------+
                                  |
    -----------------------------------------------------------------
    |               |                   |                  |
    v               v                   v                  v
+---------+   +-----------+     +----------------+   +-----------------+
| Auth    |   | Users     |     | Restaurants    |   | Orders          |
| Service |   | Service   |     | Service        |   | Service         |
+---------+   +-----------+     +----------------+   +-----------------+
| JWT     |   | CRUD users|     | CRUD restaurants|  | Gestion pedidos |
| issuing |   | Roles     |     | Menús / dishes |   | Estado pedidos  |
+---------+   +-----------+     +----------------+   +-----------------+
     |                                                  |
     |        +------------------------+                 |
     |        | Notification Service   | <---------------+
     |        +------------------------+    Envía eventos (Kafka/Rabbit)

```

Cada microservicio tiene su propia BD:

```
auth_db      users_db      restaurants_db      orders_db      notifications_db

```

