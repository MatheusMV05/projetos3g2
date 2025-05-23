package com.brasfi.siteinstitucional.comunicacao.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecaptchaResponse {
    private boolean success;
    private String challenge_ts;
    private String hostname;
    private double score;
    private String action;
    private String[] errorCodes;
}