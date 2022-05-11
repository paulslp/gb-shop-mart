package ru.gb.gbshopmart.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.gbapi.events.OrderEvent;
import ru.gb.gbapi.order.dto.OrderDto;
import ru.gb.gbshopmart.dao.CategoryDao;
import ru.gb.gbshopmart.dao.ManufacturerDao;
import ru.gb.gbshopmart.dao.OrderDao;
import ru.gb.gbshopmart.entity.Order;
import ru.gb.gbshopmart.web.dto.mapper.OrderMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderDao orderDao;
    private final OrderMapper orderMapper;
    private final ManufacturerDao manufacturerDao;
    private final CategoryDao categoryDao;
    private final JmsTemplate jmsTemplate;

    @Value("${spring.artemis.embedded.queues}")
    public String queue;


    @Transactional
    public OrderDto save(final OrderDto orderDto) {
        Order order = orderMapper.toOrder(orderDto, manufacturerDao, categoryDao);
        if (order.getId() != null) {
            orderDao.findById(orderDto.getId()).ifPresent(
                    (p) -> order.setVersion(p.getVersion())
            );
        }
        OrderDto savedOrderDto = orderMapper.toOrderDto(orderDao.save(order));

        try {
            jmsTemplate.convertAndSend(queue, new OrderEvent(savedOrderDto));
        } catch (RuntimeException e) {
            log.error("Ошибка отправки сообщения", e);
        }

        return savedOrderDto;
    }


    @Transactional(readOnly = true)
    public OrderDto findById(Long id) {
        return orderMapper.toOrderDto(orderDao.findById(id).orElse(null));
    }


    public List<OrderDto> findAll() {
        return orderDao.findAll().stream().map(orderMapper::toOrderDto).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        try {
            orderDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
    }
}

