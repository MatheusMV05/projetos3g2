package com.brasfi.siteinstitucional.config;

import com.brasfi.siteinstitucional.i18n.service.DbMessageSource;
import com.brasfi.siteinstitucional.i18n.service.TraducaoService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver; // Ou CookieLocaleResolver

import java.util.Locale;

@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {

    @Bean
    public MessageSource messageSource(TraducaoService traducaoService) {
        DbMessageSource messageSource = new DbMessageSource(traducaoService);
        // messageSource.setDefaultEncoding("UTF-8"); // Já configurado no DbMessageSource se necessário
        // messageSource.setUseCodeAsDefaultMessage(true); // Retorna a chave se a tradução não for encontrada
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        //SessionLocaleResolver slr = new SessionLocaleResolver();
        //slr.setDefaultLocale(new Locale("pt", "BR")); // Define o locale padrão
        //return slr;
        // Alternativamente, usar CookieLocaleResolver para persistir entre sessões sem depender de sessão HTTP
        org.springframework.web.servlet.i18n.CookieLocaleResolver clr = new org.springframework.web.servlet.i18n.CookieLocaleResolver();
        clr.setDefaultLocale(new Locale("pt", "BR"));
        clr.setCookieName("BRASFI_LANG");
        clr.setCookieMaxAge(3600 * 24 * 30); // 30 dias
        return clr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); // Parâmetro na URL para mudar o idioma, ex: ?lang=en
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}