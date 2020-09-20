package ru.diasoft.digitalq.demoapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class DemoApiGatewayApp {

    public static void main(String[] args) {
        SpringApplication.run(DemoApiGatewayApp.class, args);
    }

    @Bean
    public RouteLocator createRoutes(RouteLocatorBuilder builder, DiscoveryClient discoveryClient) {

        ServiceInstance serviceInstanceFirst = discoveryClient.getInstances("ncheranev-demo-service").get(0);

        return builder.routes()
                .route(p -> p
                        .path("/v1/sms-verification/**")
                        .uri("http://" + serviceInstanceFirst.getHost() + ":" + serviceInstanceFirst.getPort() + "/")
                        .id("ncheranev-demo")
                )
                .build();
    }
}
