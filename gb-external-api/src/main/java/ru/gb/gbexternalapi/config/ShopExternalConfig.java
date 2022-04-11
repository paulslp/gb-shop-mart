package ru.gb.gbexternalapi.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import ru.gb.gbexternalapi.service.CategoryGateway;
import ru.gb.gbexternalapi.service.ManufacturerGateway;
import ru.gb.gbexternalapi.service.ProductGateway;

@Configuration
@EnableFeignClients(basePackageClasses = {ManufacturerGateway.class,
        ProductGateway.class, CategoryGateway.class})
public class ShopExternalConfig {
}
