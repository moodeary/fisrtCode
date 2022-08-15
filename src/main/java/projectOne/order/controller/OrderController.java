package projectOne.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import projectOne.order.dto.OrderPatchDto;
import projectOne.order.dto.OrderPostDto;
import projectOne.order.entity.Order;
import projectOne.order.mapper.OrderMapper;
import projectOne.order.service.OrderService;
import projectOne.dto.MultiResponseDto;
import projectOne.dto.SingleResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/orders")
@Validated
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper mapper;




    @PostMapping
    public ResponseEntity postOrder(@Valid @RequestBody OrderPostDto orderPostDto) {

        System.out.println("orderPostDto = " + orderPostDto);

        Order createdOrder = orderService.createOrder(mapper.orderPostDtoToOrder(orderPostDto));

        System.out.println("createdOrder = " + createdOrder);

        // List<Coffee> coffees = coffeeService.findOrderedCoffees(createdOrder);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.orderToOrderResponseDto(createdOrder)),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{order-id}")
    public ResponseEntity patchOrder(@PathVariable("order-id") @Positive long orderId,
                                     @Valid @RequestBody OrderPatchDto orderPatchDto) {

        orderPatchDto.setOrderId(orderId);

        Order order = orderService.updateOrder(mapper.orderPatchDtoToOrder(orderPatchDto));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.orderToOrderResponseDto(order))
                , HttpStatus.OK);

    }
    @GetMapping("/{order-id}")
    public ResponseEntity getOrder(@PathVariable("order-id") @Positive long orderId) {
        Order order = orderService.findOrder(orderId);

        // List<Coffee> coffees = coffeeService.findOrderedCoffees(order);
        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.orderToOrderResponseDto(order)),
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getOrders(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {

        log.info("위치 0");
        Page<Order> pageOrders = orderService.findOrders(page - 1, size);

        log.info("위치 1");
        List<Order> orders = pageOrders.getContent();

        log.info("위치 1");
        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.ordersToOrderResponseDtos(orders), pageOrders),
                HttpStatus.OK);
    }

    @DeleteMapping("/{order-id}")
    public ResponseEntity cancelOrder(@PathVariable("order-id") @Positive long orderId) {
        orderService.cancelOrder(orderId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}