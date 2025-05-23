package com.brasfi.siteinstitucional.admin.analytics.service;

import com.brasfi.siteinstitucional.admin.analytics.dto.DadosAgrupadosDTO;
import com.brasfi.siteinstitucional.admin.analytics.dto.EstatisticasDTO;
import com.brasfi.siteinstitucional.admin.analytics.dto.EventoAnalyticsDTO;
import com.brasfi.siteinstitucional.admin.analytics.entity.EventoAnalytics;
import com.brasfi.siteinstitucional.admin.analytics.repository.EventoAnalyticsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final EventoAnalyticsRepository eventoRepository;

    public void registrarEvento(EventoAnalyticsDTO dto, HttpServletRequest request) {
        // Obter informações do usuário logado
        Long usuarioId = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            try {
                // Lógica para extrair o ID do usuário da autenticação
                // Esta lógica pode variar dependendo da implementação
            } catch (Exception e) {
                // Log e continue
            }
        }

        // Construir entidade de evento
        EventoAnalytics evento = EventoAnalytics.builder()
                .tipo(dto.getTipo())
                .categoria(dto.getCategoria())
                .acao(dto.getAcao())
                .rotulo(dto.getRotulo())
                .valor(dto.getValor())
                .ipUsuario(obterIpCliente(request))
                .userAgent(request.getHeader("User-Agent"))
                .paginaReferencia(dto.getPaginaReferencia())
                .paginaAtual(dto.getPaginaAtual())
                .usuarioId(usuarioId)
                .build();

        // Salvar evento
        eventoRepository.save(evento);
    }

    public EstatisticasDTO obterEstatisticas(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio == null) {
            inicio = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
        }

        if (fim == null) {
            fim = LocalDateTime.now();
        }

        // Obter top 10 páginas mais visitadas
        List<DadosAgrupadosDTO> paginasMaisVisitadas = eventoRepository.getPaginasMaisVisitadas(
                inicio, fim, PageRequest.of(0, 10));

        // Obter top 10 referências
        List<DadosAgrupadosDTO> referenciasPopulares = eventoRepository.getReferenciasPopulares(
                inicio, fim, PageRequest.of(0, 10));

        // Obter contagem de usuários únicos
        Long usuariosUnicos = eventoRepository.getUsuariosUnicos(inicio, fim);

        // Número total de eventos
        long totalEventos = eventoRepository.count();

        // Construir DTO de estatísticas
        return EstatisticasDTO.builder()
                .paginasMaisVisitadas(paginasMaisVisitadas)
                .referenciasPopulares(referenciasPopulares)
                .usuariosUnicos(usuariosUnicos)
                .totalEventos(totalEventos)
                .periodoInicio(inicio)
                .periodoFim(fim)
                .build();
    }

    public EstatisticasDTO obterEstatisticasDiarias() {
        LocalDateTime inicio = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime fim = LocalDateTime.now().with(LocalTime.MAX);
        return obterEstatisticas(inicio, fim);
    }

    public EstatisticasDTO obterEstatisticasSemanais() {
        LocalDateTime inicio = LocalDateTime.now().minus(7, ChronoUnit.DAYS);
        LocalDateTime fim = LocalDateTime.now();
        return obterEstatisticas(inicio, fim);
    }

    public EstatisticasDTO obterEstatisticasMensais() {
        LocalDateTime inicio = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
        LocalDateTime fim = LocalDateTime.now();
        return obterEstatisticas(inicio, fim);
    }

    private String obterIpCliente(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}