package com.brasfi.siteinstitucional.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;


import java.util.Locale;

@Configuration
public class MessageSourceConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
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