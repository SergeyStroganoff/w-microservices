package com.stroganov.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration

public class OpenApiConfig {

    @Value("${openapi.dev-url}")
    private String devUrl;

    @Value("${openapi.prod-url}")
    private String prodUrl;

    @Value("${openapi.terms-url}")
    private String termsURL;

    @Value("${openapi.license-url}")
    private String licenseURL;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");
        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in Production environment");
        Contact contact = new Contact();
        contact.setEmail("sergey.stroganow@gmail.com");
        contact.setName("https://github.com/SergeyStroganoff");
        contact.setUrl("https://www.bezkoder.com");
        License mitLicense = new License().name("MIT License").url(licenseURL);
        Info info = new Info()
                .title("Warehouse management API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to manage Warehouse.").termsOfService(termsURL)
                .license(mitLicense);
        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}
