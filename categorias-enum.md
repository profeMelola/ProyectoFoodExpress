# Refactorización de categorías con Enum en FoodExpress

## Contexto

En versiones iniciales del proyecto **FoodExpress**, el campo `category` de los platos (`Dish`) se almacenaba en base de datos como un texto libre:

- `Hamburguesas`
- `Pasta`
- `Sushi`
- `Entrante`
- `Postre`

Aunque funcional, este enfoque presenta problemas a medio y largo plazo.

---

## Problema del uso de Strings libres

Mantener las categorías como texto libre implica:

- Posibilidad de inconsistencias:
  - `Sushi`, `sushi`, `SUSHI`
  - Espacios accidentales (`"Entrante "`)
- Falta de control sobre los valores permitidos
- Errores en tiempo de ejecución difíciles de detectar
- Lógica de negocio dispersa y poco mantenible

Por este motivo, en aplicaciones Java es una buena práctica utilizar **enumerados (`enum`)** para representar valores cerrados como las categorías.

---

## Uso de enum y conflicto con la base de datos

En Java, los valores de un `enum` deben declararse siguiendo la convención:

```java
HAMBURGUESAS, PASTA, SUSHI, ENTRANTE, POSTRE
```

Sin embargo, los valores actuales en base de datos (`Hamburguesas`, `Pasta`, etc.) **no coinciden exactamente** con estos identificadores.

Mantener los valores actuales en la base de datos **y** usar enums en mayúsculas obligaría a:

- Implementar convertidores personalizados
- Añadir lógica extra de transformación
- Introducir complejidad innecesaria para el nivel del proyecto

---

## Decisión adoptada

Para simplificar el diseño y mantener el foco en los objetivos del curso, se ha decidido:

- **Modificar la base de datos**
- Almacenar las categorías en:
  - MAYÚSCULAS
  - SIN acentos
  - SIN espacios ni caracteres especiales

Ejemplo:

```sql
HAMBURGUESAS
PASTA
SUSHI
ENTRANTE
POSTRE
```

---

## Ventajas de esta decisión

- Coincidencia directa entre base de datos y enum
- Eliminación de código de conversión adicional
- Menos errores por mayúsculas/minúsculas
- Modelo más claro y fácil de mantener
- Mejor alineación con buenas prácticas Java
- En BD guardamos el valor estable del enum (SUSHI) y en la respuesta mostramos un texto o label (Sushi).

