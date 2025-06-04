package com.brasfi.siteinstitucional.sitemap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SitemapUrl {
    private String loc; // URL
    private LocalDateTime lastmod; // Data da última modificação
    private String changefreq; // Frequência de mudança: always, hourly, daily, weekly, monthly, yearly, never
    private Double priority; // Prioridade: 0.0 to 1.0
}