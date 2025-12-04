# Testing
En la práctica profesional usarías tres capas de test:

## TEST UNITARIOS

Para servicios y lógica.

Usan Mockito → rápidos y no necesitan BD.

Ej:

- Testear UserService.register()
- Testear filtro de pedidos en OrderService
- Testear conversión de DTOs

## TEST DE INTEGRACIÓN API (WebMvcTest o @SpringBootTest)

Validan endpoints REST incluyendo controlador, validaciones y serialización.

Ej:

- POST /api/restaurants funciona
- GET /api/orders?status=ENTREGADO devuelve lo esperado
- excepciones ofrecen el status correcto

## TEST END-TO-END (con @SpringBootTest + H2)

Arranca la aplicación entera y usa la BD real.

Ej:

- CRUD completo de restaurante funciona
- borrar restaurante no provoca tabla intermedia
- relaciones JPA están bien mapeadas

---

# EJEMPLOS

# Test para el endpoint GET /api/dishes

Para un endpoint REST sencillo como este, lo típico es un test de controlador usando:

- @WebMvcTest(DishController.class)
- MockMvc para simular las peticiones HTTP.
- @MockBean para sustituir el DishService.

Verificar:

- HTTP 200 OK
- JSON correcto
- Se llamó a dishService.findAll()

No hace falta levantar la base de datos ni el contexto completo ⇒ rápido.

**En IntelliJ:**

No hace falta añadir la dependencia cuando hemos usado Spring Web:

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

```

Incluye: JUnit 5, MockMvc, Mockito, JSONAssert, Hamcrest…

