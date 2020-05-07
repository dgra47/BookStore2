package com.termos.service;

import com.termos.dto.OrderDTO;
import com.termos.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class OrderService implements AbstractService<OrderDTO>{
    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderDTO persistNew(OrderDTO order) {
        return orderRepository.modelToDto(orderRepository.persist(orderRepository.dtoToModel(order)));
    }

    @Override
    public String remove(String id) {
        return orderRepository.remove(id);
    }

    @Override
    public OrderDTO getById(String id) {
        return orderRepository.modelToDto(orderRepository.getById(id));
    }

    @Override
    public Stream<OrderDTO> findAll() {
        return orderRepository.findAll().stream().map(book -> orderRepository.modelToDto(book));
    }

    @Override
    public int getRecordCount() {
        return orderRepository.count();
    }
}