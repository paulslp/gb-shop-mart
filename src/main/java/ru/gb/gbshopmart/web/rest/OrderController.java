package ru.gb.gbshopmart.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.gbapi.order.dto.OrderDto;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbshopmart.service.OrderService;
import ru.gb.gbshopmart.service.ProductService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    private final ProductService productService;

    @GetMapping
    public List<OrderDto> getOrderList() {
        return orderService.findAll();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable("orderId") Long id) {
        OrderDto order;
        if (id != null) {
            order = orderService.findById(id);
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody OrderDto orderDto) {
        List<ProductDto> products = orderDto.getProducts().stream().map(productDto ->
                productService.findById(productDto.getId()))
                .collect(Collectors.toList());
        orderDto.setProducts(products);
        OrderDto savedOrder = orderService.save(orderDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/order/" + savedOrder.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("orderId") Long id, @Validated @RequestBody OrderDto orderDto) {
        orderDto.setId(id);
        orderService.save(orderDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("orderId") Long id) {
        orderService.deleteById(id);
    }
}
