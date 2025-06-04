package com.brasfi.siteinstitucional.sitemap;

import com.brasfi.siteinstitucional.entity.Pagina; // Supondo que você tem uma entidade Pagina
import com.brasfi.siteinstitucional.repository.PaginaRepository; // E seu repositório
// Importar outros repositórios/serviços para outras entidades (Parceiros, Notícias, etc.)
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SitemapService {

    private final PaginaRepository paginaRepository;
    // Injete outros repositórios para outras seções do site (ex: ParceiroRepository, NoticiaRepository)

    @Value("${application.base-url:http://localhost:8080}") // Defina no application.properties
    private String baseUrl;

    public String generateSitemapXml() {
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xml.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");

        // Página Inicial
        addUrl(xml, baseUrl + "/", null, "daily", 1.0);

        // Adicionar outras páginas estáticas principais
        addUrl(xml, baseUrl + "/quem-somos", null, "monthly", 0.8);
        addUrl(xml, baseUrl + "/nosso-impacto", null, "weekly", 0.8);
        addUrl(xml, baseUrl + "/parcerias", null, "monthly", 0.7);
        // ... outras páginas fixas

        // Páginas Dinâmicas (ex: de 'PaginaRepository')
        List<Pagina> paginasDinamicas = paginaRepository.findAll(); // Idealmente, apenas as ativas/públicas
        for (Pagina pagina : paginasDinamicas) {
            // Assumindo que 'pagina.getSlug()' existe e é usado na URL
            addUrl(xml, baseUrl + "/pagina/" + pagina.getSlug(),
                    pagina.getDataAtualizacao() != null ? pagina.getDataAtualizacao() : pagina.getDataCriacao(),
                    "weekly", 0.6);
        }

        // Adicionar URLs de Parceiros (se houver páginas individuais para eles)
        // List<Parceiro> parceiros = parceiroRepository.findByVisivelTrueAndAtivoTrueOrderByNomeOrganizacaoAsc();
        // for (Parceiro parceiro : parceiros) {
        //     addUrl(xml, baseUrl + "/parceiro/" + parceiro.getId(), // ou slug se tiver
        //            parceiro.getDataAtualizacao(), "monthly", 0.5);
        // }

        // Adicionar URLs de Notícias, Eventos, etc. de forma similar

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