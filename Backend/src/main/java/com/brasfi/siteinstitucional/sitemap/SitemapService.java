package com.brasfi.siteinstitucional.sitemap;

import com.brasfi.siteinstitucional.entity.Pagina;
import com.brasfi.siteinstitucional.repository.PaginaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SitemapService {

    private final PaginaRepository paginaRepository;

    @Value("${application.base-url:http://localhost:8080}")
    private String baseUrl;

    public String generateSitemapXml() {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");

        // Página Inicial
        addUrl(xml, baseUrl + "/", null, "daily", 1.0);

        // Adicionar outras páginas estáticas principais
        addUrl(xml, baseUrl + "/sobre", null, "monthly", 0.8); // Changed from /quem-somos
        addUrl(xml, baseUrl + "/servicos", null, "weekly", 0.8); // Mapped /nosso-impacto to /servicos
        addUrl(xml, baseUrl + "/trabalhe-conosco", null, "monthly", 0.7); // Mapped /parcerias to /trabalhe-conosco

        // Páginas Dinâmicas
        List<Pagina> paginasDinamicas = paginaRepository.findAll();
        for (Pagina pagina : paginasDinamicas) {
            addUrl(xml, baseUrl + "/pagina/" + pagina.getSlug(),
                    pagina.getDataAtualizacao() != null ? pagina.getDataAtualizacao() : pagina.getDataCriacao(),
                    "weekly", 0.6);
        }

        xml.append("</urlset>");
        return xml.toString();
    }

    private void addUrl(StringBuilder xml, String loc, LocalDateTime lastmod, String changefreq, double priority) {
        xml.append("  <url>\n");
        xml.append("    <loc>").append(loc).append("</loc>\n");
        if (lastmod != null) {
            xml.append("    <lastmod>").append(lastmod.atZone(ZoneId.systemDefault()).toOffsetDateTime().toString()).append("</lastmod>\n");
        }
        if (changefreq != null) {
            xml.append("    <changefreq>").append(changefreq).append("</changefreq>\n");
        }
        xml.append("    <priority>").append(String.format("%.1f", priority)).append("</priority>\n");
        xml.append("  </url>\n");
    }
}