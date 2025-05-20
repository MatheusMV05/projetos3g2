package com.brasfi.siteinstitucional.service;

import com.brasfi.siteinstitucional.dto.MetricaFiltroDTO;
import com.brasfi.siteinstitucional.dto.RelatorioMetricaDTO;
import com.brasfi.siteinstitucional.entity.Metrica;
import com.brasfi.siteinstitucional.repository.MetricaRepository;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Font;
import com.lowagie.text.BaseColor;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.ColumnText;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RelatorioPdfService {

    private final MetricaRepository metricaRepository;

    @Transactional(readOnly = true)
    public ByteArrayInputStream gerarRelatorioPdf(MetricaFiltroDTO filtro) {
        List<Metrica> metricas = metricaRepository.buscarMetricasComFiltros(
                filtro.getNome(),
                filtro.getDescricao(),
                filtro.getValorMinimo(),
                filtro.getValorMaximo(),
                filtro.getUnidade(),
                filtro.getDataInicio(),
                filtro.getDataFim()
        );

        RelatorioMetricaDTO relatorioDTO = prepararDadosRelatorio(metricas, filtro);

        return gerarPdf(relatorioDTO);
    }

    private RelatorioMetricaDTO prepararDadosRelatorio(List<Metrica> metricas, MetricaFiltroDTO filtro) {
        List<Map<String, Object>> metricasData = metricas.stream()
                .map(m -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("nome", m.getNome());
                    data.put("valor", m.getValor() + " " + m.getUnidade());
                    data.put("descricao", m.getDescricao());
                    data.put("dataReferencia", m.getDataReferencia().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    return data;
                })
                .collect(Collectors.toList());

        RelatorioMetricaDTO relatorio = new RelatorioMetricaDTO();
        relatorio.setTitulo("Relatório de Métricas de Impacto");
        relatorio.setDataGeracao(LocalDateTime.now());
        relatorio.setFiltrosAplicados(construirDescricaoFiltros(filtro));
        relatorio.setTotalMetricas(metricas.size());
        relatorio.setMetricasData(metricasData);

        return relatorio;
    }

    private String construirDescricaoFiltros(MetricaFiltroDTO filtro) {
        StringBuilder sb = new StringBuilder();

        if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
            sb.append("Nome: ").append(filtro.getNome()).append(", ");
        }

        if (filtro.getDescricao() != null && !filtro.getDescricao().isEmpty()) {
            sb.append("Descrição: ").append(filtro.getDescricao()).append(", ");
        }

        if (filtro.getValorMinimo() != null) {
            sb.append("Valor mínimo: ").append(filtro.getValorMinimo()).append(", ");
        }

        if (filtro.getValorMaximo() != null) {
            sb.append("Valor máximo: ").append(filtro.getValorMaximo()).append(", ");
        }

        if (filtro.getUnidade() != null && !filtro.getUnidade().isEmpty()) {
            sb.append("Unidade: ").append(filtro.getUnidade()).append(", ");
        }

        if (filtro.getDataInicio() != null) {
            sb.append("Data início: ").append(filtro.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append(", ");
        }

        if (filtro.getDataFim() != null) {
            sb.append("Data fim: ").append(filtro.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append(", ");
        }

        return sb.length() > 2 ? sb.substring(0, sb.length() - 2) : "Sem filtros aplicados";
    }

    private ByteArrayInputStream gerarPdf(RelatorioMetricaDTO relatorio) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            writer.setPageEvent(new PDFHeaderFooter(relatorio.getTitulo()));

            document.open();

            Font tituloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK); // Revertido para BaseColor.BLACK
            Font subtituloFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK); // Revertido para BaseColor.BLACK
            Font regularFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);   // Revertido para BaseColor.BLACK

            Paragraph titulo = new Paragraph(relatorio.getTitulo(), tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            Paragraph dataGeracao = new Paragraph("Data de geração: " +
                    relatorio.getDataGeracao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), regularFont);
            dataGeracao.setAlignment(Element.ALIGN_RIGHT);
            document.add(dataGeracao);

            Paragraph totalMetricas = new Paragraph("Total de métricas: " + relatorio.getTotalMetricas(), regularFont);
            totalMetricas.setAlignment(Element.ALIGN_RIGHT);
            totalMetricas.setSpacingAfter(15);
            document.add(totalMetricas);

            Paragraph filtrosTitulo = new Paragraph("Filtros Aplicados:", subtituloFont);
            filtrosTitulo.setSpacingBefore(10);
            document.add(filtrosTitulo);

            Paragraph filtros = new Paragraph(relatorio.getFiltrosAplicados(), regularFont);
            filtros.setSpacingAfter(20);
            document.add(filtros);

            Paragraph metricasTitulo = new Paragraph("Detalhamento das Métricas:", subtituloFont);
            metricasTitulo.setSpacingBefore(10);
            document.add(metricasTitulo);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            float[] columnWidths = {2.5f, 1.5f, 3.5f, 1.5f};
            table.setWidths(columnWidths);

            addTableHeader(table);

            addTableData(table, relatorio.getMetricasData());

            document.add(table);

            document.close();

            return new ByteArrayInputStream(out.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF: " + e.getMessage());
        }
    }

    private void addTableHeader(PdfPTable table) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE); // Revertido para BaseColor.WHITE

        Stream.of("Nome", "Valor", "Descrição", "Data Referência")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(new BaseColor(30, 65, 160)); // Revertido para new BaseColor
                    header.setBorderWidth(1);
                    header.setPadding(5);
                    header.setPhrase(new Phrase(columnTitle, headerFont));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private void addTableData(PdfPTable table, List<Map<String, Object>> data) {
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 9, BaseColor.BLACK); // Revertido para BaseColor.BLACK

        for (Map<String, Object> row : data) {
            table.addCell(createCell(row.get("nome").toString(), cellFont));
            table.addCell(createCell(row.get("valor").toString(), cellFont));
            table.addCell(createCell(row.get("descricao").toString(), cellFont));
            table.addCell(createCell(row.get("dataReferencia").toString(), cellFont));
        }
    }

    private PdfPCell createCell(String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setPadding(5);
        cell.setBorderWidth(0.5f);
        cell.setBorderColor(BaseColor.LIGHT_GRAY); // Revertido para BaseColor.LIGHT_GRAY
        return cell;
    }

    class PDFHeaderFooter extends PdfPageEventHelper {
        private String titulo;

        public PDFHeaderFooter(String titulo) {
            this.titulo = titulo;
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte cb = writer.getDirectContent();
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.DARK_GRAY); // Revertido para BaseColor.DARK_GRAY

            String textoRodape = "Página " + writer.getPageNumber() + " | BrasFi - " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            Phrase rodape = new Phrase(textoRodape, footerFont);

            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, rodape,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 20, 0);
        }
    }
}