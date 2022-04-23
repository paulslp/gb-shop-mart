package ru.gb.gbapi.config;

import feign.Feign;
import feign.FeignException;
import feign.Logger;
import feign.Request;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import feign.optionals.OptionalDecoder;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static feign.FeignException.errorStatus;

@Component
@RequiredArgsConstructor
public class GatewayFactory {

    private final GbApiProperties gbApiProperties;
    private final ObjectFactory<HttpMessageConverters> messageConverters;


    public <T> T constructGateway(Class<T> gatewayClass, String url) {
        return Feign.builder()
                .encoder(new SpringEncoder(this.messageConverters))
                .decoder(new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters))))
                .errorDecoder(errorDecoder())
                .options(new Request.Options(
                        gbApiProperties.getConnection().getConnectTimeout(),
                        TimeUnit.SECONDS,
                        gbApiProperties.getConnection().getReadTimeout(),
                        TimeUnit.SECONDS,
                        true
                ))
                .logger(new Slf4jLogger(gatewayClass))
                .logLevel(Logger.Level.FULL)
                .retryer(new Retryer.Default(
                        gbApiProperties.getConnection().getPeriod(),
                        gbApiProperties.getConnection().getMaxPeriod(),
                        gbApiProperties.getConnection().getMaxAttempts()
                ))
                .contract(new SpringMvcContract())
                .target(gatewayClass, url);
    }

    private ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            FeignException exception = errorStatus(methodKey, response);
            if (exception.status() == 500 || exception.status() == 503) {
                return new RetryableException(
                        response.status(),
                        exception.getMessage(),
                        response.request().httpMethod(),
                        exception,
                        null,
                        response.request());
            }
            return exception;
        };
    }
}
