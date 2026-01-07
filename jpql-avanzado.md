# Endpoints con JPQL avanzado

Controller + Service + DTOs + Repository queries para un módulo de reports basado en en el modelo (Order, User, Restaurant, OrderDetail, Dish).

Endpoints (ReportsController):

## ¿Cuánto ha gastado cada cliente?

Gasto total por cliente (suma de subtotales).

**GET /api/reports/customers/spend**

## ¿Qué restaurantes tienen más ventas?

Restaurantes con más pedidos (COUNT de órdenes)

**GET /api/reports/restaurants/top-by-orders**

## ¿Cuáles son los platos más vendidos? 

Platos más vendidos por unidades (SUM de quantity)

**GET /api/reports/dishes/top-by-units**


---

En JPQL estricto, toda expresión que aparezca en el SELECT y no esté dentro de una función de agregación (SUM, COUNT, AVG, etc.) debe aparecer también en el GROUP BY.

Esto es exactamente la misma regla que en SQL estándar