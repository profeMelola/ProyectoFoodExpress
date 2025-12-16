# Ejercicio con transacciones JPA 

Operaciones que deben ir juntas:
- Crear el Order
- Crear varios OrderDetail
- Calcular subtotales
- Guardar todo

Si falla cualquier paso → no debe guardarse nada.

## Crear un pedido completo

Implementa el método createOrder() de forma que:

- Cree un pedido (Order)
- Cree todos sus detalles (OrderDetail)
- Calcule correctamente los subtotales
- Todas las operaciones deben ejecutarse dentro de una única transacción.
- Si ocurre cualquier error durante el proceso, no debe guardarse ningún dato en la base de datos.

### DTOs de entrada

```
public record CreateOrderDTO(
        Long userId,
        Long restaurantId,
        List<OrderItemDTO> items
) {}

```

```
public record OrderItemDTO(
        Long dishId,
        Integer quantity
) {}

```

### Servicio con transacción. Crear un pedido completo

```
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository,
            UserRepository userRepository,
            RestaurantRepository restaurantRepository,
            DishRepository dishRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.dishRepository = dishRepository;
    }

    @Transactional
    public Order createOrder(CreateOrderDTO dto) {

        Order order = new Order();
        order.setUser(userRepository.findById(dto.userId()).orElseThrow());
        order.setRestaurant(restaurantRepository.findById(dto.restaurantId()).orElseThrow());
        order.setStatus("CREATED");
        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);

        for (OrderItemDTO item : dto.items()) {

            Dish dish = dishRepository.findById(item.dishId())
                    .orElseThrow(() -> new IllegalArgumentException("Dish not found"));

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setDish(dish);
            detail.setQuantity(item.quantity());
            detail.setSubtotal(
                    dish.getPrice().multiply(BigDecimal.valueOf(item.quantity()))
            );

            orderDetailRepository.save(detail);
        }

        return order;
    }
}

```
- Spring abre una transacción al entrar al método.
- Si falla cualquier save() → rollback automático.
- No queda ningún pedido “a medias”.
- Spring solo hace rollback automático con RuntimeException y Error, no con Exception (excepciones checked).
    - Si la excepción es checked entonces: @Transactional(rollbackFor = Exception.class)


## @Transactional en consultas (solo lectura)

Para consultas complejas JPQL:

```
@Transactional(readOnly = true)
public List<OrderSummaryDTO> getOrderSummaries() {
    return orderRepository.findAllOrderSummaries();
}

```

- Menor consumo
- Hibernate optimiza la sesión
- Buen hábito profesional