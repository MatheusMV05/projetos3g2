package com.brasfi.siteinstitucional.metricas.service;

import com.brasfi.siteinstitucional.admin.annotation.Auditavel;
import com.brasfi.siteinstitucional.metricas.dto.DepoimentoDTO;
import com.brasfi.siteinstitucional.metricas.dto.MetricaImpactoDTO;
import com.brasfi.siteinstitucional.metricas.dto.RelatorioImpactoDTO;
import com.brasfi.siteinstitucional.metricas.entity.Depoimento;
import com.brasfi.siteinstitucional.metricas.entity.MetricaImpacto;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RelatorioPDFService {

    private final MetricaImpactoService metricaService;
    private final DepoimentoService depoimentoService;
    private final TemplateEngine templateEngine;

    @Auditavel(acao = "GERAR_RELATORIO", entidade = "RELATORIO_IMPACTO")
    public byte[] gerarRelatorioImpacto(Integer ano, String categoria, boolean incluirDepoimentos) {
        try {
            // Buscar dados
            List<MetricaImpacto> metricas = ano != null ?
                    metricaService.listarPorAno(ano) :
                    metricaService.listarAtivas();

            if (categoria != null) {
                metricas = metricas.stream()
                        .filter(m -> m.getCategoria().equals(categoria))
                        .collect(Collectors.toList());
            }

            List<Depoimento> depoimentos = incluirDepoimentos ?
                    (ano != null ? depoimentoService.listarPorAno(ano) : depoimentoService.listarDestaques()) :
                    List.of();

            // Preparar dados do relatório
            RelatorioImpactoDTO relatorio = RelatorioImpactoDTO.builder()
                    .titulo("Relatório de Impacto BRASFI")
                    .subtitulo(ano != null ? "Ano: " + ano : "Todas as Métricas")
                    .ano(ano)
                    .dataGeracao(LocalDateTime.now())
                    .metricas(converterMetricas(metricas))
                    .depoimentos(converterDepoimentos(depoimentos))
                    .metricasPorCategoria(agruparMetricasPorCategoria(metricas))
                    .metricasPorAno(agruparMetricasPorAno(metricas))
                    .resumoExecutivo(gerarResumoExecutivo(metricas))
                    .conclusao(gerarConclusao(metricas, depoimentos))
                    .build();

            // Gerar PDF usando template HTML
            return gerarPDFComTemplate(relatorio);

        } catch (Exception e) {
            log.error("Erro ao gerar relatório PDF: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao gerar relatório PDF", e);
        }
    }

    private byte[] gerarPDFComTemplate(RelatorioImpactoDTO relatorio) throws IOException {
        // Preparar contexto Thymeleaf
        Context context = new Context();
        context.setVariable("relatorio", relatorio);
        context.setVariable("dataFormatada", relatorio.getDataGeracao()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        // Processar template HTML
        String html = templateEngine.process("relatorio-impacto", context);

        // Converter HTML para PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(html, outputStream);

        return outputStream.toByteArray();
    }

    private byte[] gerarPDFProgramaticamente(RelatorioImpactoDTO relatorio) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);

        // Cabeçalho
        Paragraph titulo = new Paragraph(relatorio.getTitulo())
                .setFontSize(24)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold();
        document.add(titulo);

        Paragraph subtitulo = new Paragraph(relatorio.getSubtitulo())
                .setFontSize(16)
                .setTextAlignment(TextAlignment.CENTER);
        document.add(subtitulo);

        Paragraph data = new Paragraph("Gerado em: " +
                relatorio.getDataGeracao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT);
        document.add(data);

        // Resumo Executivo
        if (relatorio.getResumoExecutivo() != null) {
            document.add(new Paragraph("Resumo Executivo")
                    .setFontSize(18)
                    .setBold()
                    .setMarginTop(20));
            document.add(new Paragraph(relatorio.getResumoExecutivo())
                    .setFontSize(12));
        }

        // Tabela de Métricas
        document.add(new Paragraph("Métricas de Impacto")
                .setFontSize(18)
                .setBold()
                .setMarginTop(20));

        Table tabelaMetricas = new Table(UnitValue.createPercentArray(new float[]{3, 2, 2, 2}))
                .setWidth(UnitValue.createPercentValue(100));

        // Cabeçalho da tabela
        tabelaMetricas.addHeaderCell("Título");
        tabelaMetricas.addHeaderCell("Valor");
        tabelaMetricas.addHeaderCell("Categoria");
        tabelaMetricas.addHeaderCell("Ano");

        // Dados das métricas
        for (MetricaImpactoDTO metrica : relatorio.getMetricas()) {
            tabelaMetricas.addCell(metrica.getTitulo());
            tabelaMetricas.addCell(formatarValor(metrica.getValor(), metrica.getUnidade()));
            tabelaMetricas.addCell(metrica.getCategoria());
            tabelaMetricas.addCell(String.valueOf(metrica.getAno()));
        }

        document.add(tabelaMetricas);

        // Depoimentos
        if (!relatorio.getDepoimentos().isEmpty()) {
            document.add(new Paragraph("Depoimentos")
                    .setFontSize(18)
                    .setBold()
                    .setMarginTop(20));

            for (DepoimentoDTO depoimento : relatorio.getDepoimentos()) {
                document.add(new Paragraph("\"" + depoimento.getTexto() + "\"")
                        .setFontSize(11)
                        .setItalic()
                        .setMarginLeft(20)
                        .setMarginRight(20));

                document.add(new Paragraph("— " + depoimento.getNome() +
                        ", " + depoimento.getCargo() + " - " + depoimento.getOrganizacao())
                        .setFontSize(10)
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setMarginBottom(10));
            }
        }

        // Conclusão
        if (relatorio.getConclusao() != null) {
            document.add(new Paragraph("Conclusão")
                    .setFontSize(18)
                    .setBold()
                    .setMarginTop(20));
            document.add(new Paragraph(relatorio.getConclusao())
                    .setFontSize(12));
        }

        document.close();
        return outputStream.toByteArray();
    }

    private String formatarValor(BigDecimal valor, String unidade) {
        if ("R$".equals(unidade)) {
            return String.format("R$ %,.2f", valor);
        } else if ("%".equals(unidade)) {
            return String.format("%.1f%%", valor);
        } else {
            return valor.toString() + " " + unidade;
        }
    }

    private List<MetricaImpactoDTO> converterMetricas(List<MetricaImpacto> metricas) {
        return metricas.stream()
                .map(m -> MetricaImpactoDTO.builder()
                        .id(m.getId())
                        .titulo(m.getTitulo())
                        .valor(m.getValor())
                        .unidade(m.getUnidade())
                        .descricao(m.getDescricao())
                        .categoria(m.getCategoria())
                        .ano(m.getAno())
                        .tipoIniciativa(m.getTipoIniciativa())
                        .icone(m.getIcone())
                        .ordem(m.getOrdem())
                        .ativo(m.isAtivo())
                        .build())
                .collect(Collectors.toList());
    }

    private List<DepoimentoDTO> converterDepoimentos(List<Depoimento> depoimentos) {
        return depoimentos.stream()
                .map(d -> DepoimentoDTO.builder()
                        .id(d.getId())
                        .nome(d.getNome())
                        .cargo(d.getCargo())
                        .organizacao(d.getOrganizacao())
                        .texto(d.getTexto())
                        .fotoUrl(d.getFotoUrl())
                        .iniciativaRelacionada(d.getIniciativaRelacionada())
                        .ano(d.getAno())
                        .ordem(d.getOrdem())
                        .destaque(d.isDestaque())
                        .ativo(d.isAtivo())
                        .build())
                .collect(Collectors.toList());
    }

    private Map<String, List<MetricaImpactoDTO>> agruparMetricasPorCategoria(List<MetricaImpacto> metricas) {
        return converterMetricas(metricas).stream()
                .collect(Collectors.groupingBy(MetricaImpactoDTO::getCategoria));
    }

    private Map<Integer, List<MetricaImpactoDTO>> agruparMetricasPorAno(List<MetricaImpacto> metricas) {
        return converterMetricas(metricas).stream()
                .collect(Collectors.groupingBy(MetricaImpactoDTO::getAno));
    }

    private String gerarResumoExecutivo(List<MetricaImpacto> metricas) {
        Map<String, Long> metricasPorCategoria = metricas.stream()
                .collect(Collectors.groupingBy(MetricaImpacto::getCategoria, Collectors.counting()));

        StringBuilder resumo = new StringBuilder();
        resumo.append("Este relatório apresenta ")
                .append(metricas.size())
                .append(" métricas de impacto da BRASFI, distribuídas em ")
                .append(metricasPorCategoria.size())
                .append(" categorias: ");

        metricasPorCategoria.forEach((categoria, count) ->
                resumo.append(categoria).append(" (").append(count).append("), "));

        resumo.setLength(resumo.length() - 2); // Remove última vírgula
        resumo.append(".");

        return resumo.toString();
    }

    private String gerarConclusao(List<MetricaImpacto> metricas, List<Depoimento> depoimentos) {
        return String.format(
                "A BRASFI demonstra através de suas %d métricas de impacto e %d depoimentos " +
                        "o compromisso contínuo com a promoção de finanças sustentáveis no Brasil. " +
                        "Os resultados apresentados evidenciam o alcance e a efetividade das iniciativas " +
                        "desenvolvidas pela organização.",
                metricas.size(),
                depoimentos.size()
        );
    }
}