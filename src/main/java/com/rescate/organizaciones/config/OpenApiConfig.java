package com.rescate.organizaciones.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS1: Microservicio de Organizaciones y Sedes")
                        .version("1.0.0")
                        .description("API REST para la gestión de donantes y ONGs receptoras de alimentos excedentes.")
                        .contact(new Contact()
                                .name("Equipo Rescate Alimentario - CS2032")
                                .email("contacto@rescatealimentario.org")));
    }
}
