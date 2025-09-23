package com.triage.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI triageOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Triage System API")
                        .description("API สำหรับระบบ Triage - จัดการข้อมูล Triage และ Call Logs")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Triage System")
                                .email("support@triage.com")));
    }
}
