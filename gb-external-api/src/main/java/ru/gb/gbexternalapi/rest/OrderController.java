package ru.gb.gbexternalapi.rest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.gbapi.order.api.OrderGateway;
import ru.gb.gbapi.order.dto.OrderDto;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderGateway orderGateway;

    public OrderController(
            @Qualifier("orderIntGateway") OrderGateway orderGateway
    ) {
        this.orderGateway = orderGateway;
    }

    @GetMapping
    List<OrderDto> getOrderList() {
        return orderGateway.getOrderList();
    }

    @GetMapping({"/{orderId}"})
    ResponseEntity<OrderDto> getOrder(@PathVariable("orderId") Long id) {
        return orderGateway.getOrder(id);
    }

    @PostMapping
    ResponseEntity<OrderDto> handlePost(@Validated @RequestBody OrderDto orderDto) {
        return orderGateway.handlePost(orderDto);
    }

    @PutMapping({"/{orderId}"})
    ResponseEntity<OrderDto> handleUpdate(@PathVariable("orderId") Long id,
                                          @Validated @RequestBody OrderDto orderDto) {
        return orderGateway.handleUpdate(id, orderDto);
    }

    @DeleteMapping({"/{orderId}"})
    void deleteById(@PathVariable("orderId") Long id) {
        orderGateway.deleteById(id);
    }
}
