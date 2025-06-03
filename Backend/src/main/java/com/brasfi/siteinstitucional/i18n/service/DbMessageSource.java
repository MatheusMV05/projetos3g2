package com.brasfi.siteinstitucional.i18n.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

// Não precisa ser um @Component se instanciado diretamente no @Bean da config
// Mas se quiser injetar outras coisas aqui, pode ser.
@RequiredArgsConstructor
public class DbMessageSource extends AbstractMessageSource {

    private final TraducaoService traducaoService;

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String idioma = locale.toLanguageTag(); // ex: "pt-BR"
        String message = traducaoService.getTraducao(idioma, code);
        if (message == null) {
            // Tentar idioma base (ex: "pt" de "pt-BR") se a tradução específica não for encontrada
            String baseIdioma = locale.getLanguage();
            if (!baseIdioma.equals(idioma)) {
                message = traducaoService.getTraducao(baseIdioma, code);
            }
        }
        return (message != null) ? new MessageFormat(message, locale) : null;
    }

    // Opcional: para carregar todas as mensagens de um locale de uma vez,
    // pode ser útil para o frontend, mas para MessageSource padrão, resolveCode é suficiente.
    public Map<String, String> getAllMessages(Locale locale) {
        return traducaoService.getTraducoesPorIdioma(locale.toLanguageTag());
    }
}