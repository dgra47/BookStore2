package com.termos.controller;

import com.termos.dto.ApiResponse;
import com.termos.dto.OrderDTO;
import com.termos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.stream.Stream;

import javax.websocket.server.PathParam;

@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    ResponseEntity<ApiResponse<Stream<OrderDTO>>> orders() {
        var response = new ApiResponse<Stream<OrderDTO>>();
        response.isSuccess = true;
        response.message = "Orders list returned successfully.";
        response.payload = orderService.findAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{id}")
    ResponseEntity<ApiResponse<OrderDTO>> get(@PathVariable String id) {
        var response = new ApiResponse<OrderDTO>();
        if(orderService.getById(id) == null){
            response.isSuccess = false;
            response.message = "Order doesn't exist.";
            return ResponseEntity.badRequest().body(response);
        }
        response.isSuccess = true;
        response.message = "Order of id: "+id+" returned successfully.";
        response.payload = orderService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/orders")
    ResponseEntity<ApiResponse<OrderDTO>> create(@RequestBody OrderDTO order){
        var response = new ApiResponse<OrderDTO>();

        if(orderService.getById(order.getOrderId()) != null){
            response.isSuccess = false;
            response.message = "Order already exists.";
            return ResponseEntity.badRequest().body(response);
        }

        response.isSuccess = true;
        response.message = "Order created successfully.";
        response.payload = orderService.persistNew(order);
        return ResponseEntity.created(URI.create("/orders/"+response.payload.getOrderId())).body(response);
    }

    @PutMapping("/orders/{id}")
    ResponseEntity<ApiResponse<OrderDTO>> update(@PathVariable String id, @RequestBody OrderDTO order) {
        var response = new ApiResponse<OrderDTO>();

        if(orderService.getById(id) == null){
            response.isSuccess = false;
            response.message = "Order doesn't exist.";
            return ResponseEntity.badRequest().body(response);
        }

        response.isSuccess = true;
        response.message = "Updated order.";
        response.payload = orderService.persistNew(order);
        return ResponseEntity.ok(response);
    }

    //delete
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable String id) {
        var response = new ApiResponse<String>();
        if(orderService.getById(id) == null){
            response.isSuccess = false;
            response.message = "Order doesn't exist.";
            return ResponseEntity.badRequest().body(response);
        }
        response.message = "Deleted order successfully";
        response.isSuccess = true;
        response.payload = orderService.remove(id);
        return ResponseEntity.ok(response);
    }


}