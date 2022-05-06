package ru.gb.gbshopmart.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import ru.gb.gbapi.order.dto.OrderDto;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbshopmart.dao.CategoryDao;
import ru.gb.gbshopmart.dao.ManufacturerDao;
import ru.gb.gbshopmart.dao.ProductDao;
import ru.gb.gbshopmart.entity.Order;
import ru.gb.gbshopmart.entity.Product;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Mapper(uses = {ProductMapper.class, ManufacturerMapper.class})
public interface OrderMapper {

    Order toOrder(OrderDto orderDto, @Context ManufacturerDao manufacturerDao, @Context CategoryDao categoryDao);

    OrderDto toOrderDto(Order order);

    default List<Product> productDtoListToProductList(List<ProductDto> products, @Context ProductDao productDao) {
        return products.stream().map(c -> productDao.findById(c.getId())
                .orElseThrow(
                        () -> new NoSuchElementException("There isn't product with id + " + c.getId()))
        )
                .collect(Collectors.toList());
    }

}
