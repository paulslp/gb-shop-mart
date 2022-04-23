package ru.gb.gbapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.gb.gbapi.category.api.CategoryGateway;
import ru.gb.gbapi.manufacturer.api.ManufacturerGateway;
import ru.gb.gbapi.product.api.ProductGateway;

@Configuration
@EnableFeignClients
@EnableConfigurationProperties(GbApiProperties.class)
@RequiredArgsConstructor
@Import(value = {GatewayFactory.class})
public class FeignConfig {

    private final GatewayFactory gatewayFactory;

    private final GbApiProperties gbApiProperties;

    @Bean
    public ManufacturerGateway manufacturerGateway() {
        return gatewayFactory.constructGateway(
                ManufacturerGateway.class, gbApiProperties.getEndpoint().getManufacturerUrl()
        );
    }

    @Bean
    public ProductGateway productGateway() {
        return gatewayFactory.constructGateway(
                ProductGateway.class, gbApiProperties.getEndpoint().getProductUrl()
        );
    }

    @Bean
    public CategoryGateway categoryGateway() {
        return gatewayFactory.constructGateway(
                CategoryGateway.class, gbApiProperties.getEndpoint().getCategoryUrl()
        );
    }
}
