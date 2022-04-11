package ru.gb.gbshopmart.web.dto.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.gbapi.product.dto.ProductDto;
import ru.gb.gbshopmart.dao.ManufacturerDao;
import ru.gb.gbshopmart.entity.Manufacturer;
import ru.gb.gbshopmart.entity.Product;

import java.util.NoSuchElementException;

@Mapper(uses = {ManufacturerMapper.class, CategoryMapper.class})
public interface ProductMapper {


    Product toProduct(ProductDto productDto,
                      @Context ManufacturerDao manufacturerDao);

    @Mapping(target = "category", source = "categories")
    ProductDto toProductDto(Product product);

    default Manufacturer getManufacturer(String manufacturer, @Context ManufacturerDao manufacturerDao) {
        return manufacturerDao.findByName(manufacturer).orElseThrow(NoSuchElementException::new);
    }

    default String getManufacturer(Manufacturer manufacturer) {
        return manufacturer.getName();
    }
}
