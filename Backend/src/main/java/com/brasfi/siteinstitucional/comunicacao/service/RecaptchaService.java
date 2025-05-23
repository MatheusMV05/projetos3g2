package com.brasfi.siteinstitucional.comunicacao.service;

import com.brasfi.siteinstitucional.comunicacao.dto.RecaptchaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecaptchaService {

    private final RestTemplate restTemplate;

    @Value("${application.recaptcha.secret}")
    private String recaptchaSecret;

    @Value("${application.recaptcha.threshold:0.5}")
    private double threshold;

    public boolean validarToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        try {
            Map<String, String> body = new HashMap<>();
            body.put("secret", recaptchaSecret);
            body.put("response", token);

            RecaptchaResponse response = restTemplate.postForObject(
                    "https://www.google.com/recaptcha/api/siteverify",
                    body,
                    RecaptchaResponse.class);

            if (response == null) {
                return false;
            }

            return response.isSuccess() && response.getScore() >= threshold;

        } catch (Exception e) {
            log.error("Erro ao validar token reCAPTCHA: {}", e.getMessage(), e);
            return false;
        }
    }
}