package com.brasfi.siteinstitucional.admin.aspect;

import com.brasfi.siteinstitucional.admin.annotation.Auditavel;
import com.brasfi.siteinstitucional.admin.service.AuditoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditoriaAspect {

    private final AuditoriaService auditoriaService;
    private final ObjectMapper objectMapper;

    @AfterReturning(
            pointcut = "@annotation(com.brasfi.siteinstitucional.admin.annotation.Auditavel)",
            returning = "resultado"
    )
    public void registrarAcao(JoinPoint joinPoint, Object resultado) {
        try {
            // Obter a anotação
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Auditavel auditavel = method.getAnnotation(Auditavel.class);

            // Obter os valores da anotação
            String acao = auditavel.acao();
            String entidade = auditavel.entidade();

            // Obter o ID da entidade, se existir no resultado
            Long entidadeId = null;
            if (resultado != null && auditavel.capturarIdRetorno()) {
                try {
                    entidadeId = (Long) resultado.getClass().getMethod("getId").invoke(resultado);
                } catch (Exception e) {
                    log.warn("Não foi possível obter o ID da entidade retornada: {}", e.getMessage());
                }
            }

            // Obter detalhes
            String detalhes = "";
            if (auditavel.incluirParametros()) {
                Object[] args = joinPoint.getArgs();
                detalhes = objectMapper.writeValueAsString(args);
            }

            // Registrar a ação
            auditoriaService.registrarAcao(acao, entidade, entidadeId, detalhes);
        } catch (Exception e) {
            log.error("Erro ao registrar auditoria: {}", e.getMessage(), e);
        }
    }
}