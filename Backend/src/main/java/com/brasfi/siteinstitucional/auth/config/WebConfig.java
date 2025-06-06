package com.brasfi.siteinstitucional.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // CORS agora é configurado no SecurityConfig para evitar conflitos
    // com a autenticação JWT

    // Se precisar de configurações adicionais de MVC, adicionar aqui
}