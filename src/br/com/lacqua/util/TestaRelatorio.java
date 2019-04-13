package br.com.lacqua.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@SuppressWarnings("deprecation")
public class TestaRelatorio {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) throws JRException, FileNotFoundException {
		
		try {
			
		JasperCompileManager.compileReportToFile("D:\\Demonstrativo.jrxml");
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		Conta conta = new Conta();
		conta.setAno("2019");
		conta.setMes("Janeiro");
		conta.setApartamento("1001");
		conta.setCliente("Fulano");
		conta.setCondominio("Condominio Tal");
		conta.setTorre("Torre X");
		conta.setLeituraAtual(new BigDecimal("50.432"));
		conta.setLeituraAnterior(new BigDecimal("50.200"));
		conta.setCoeficiente(new BigDecimal("12.35"));
		conta.setConsumo(new BigDecimal("232"));
		conta.setDataLeituraAtual("04/04/2019");
		conta.setDataLeituraAnterior("04/04/2019");
		conta.setDataProximaLeitura("04/04/2019");
		conta.setValorConsumo(new BigDecimal("232.00"));
		conta.setValorTotal(new BigDecimal("232"));
		conta.setVolumeTotal(new BigDecimal("232"));
		conta.setTaxaLeitura(new BigDecimal("232"));
		conta.setValorConsumoComTaxa(conta.getValorConsumo().add(new BigDecimal("3.00")));
		conta.setQtdApartamentos(80);

		conta.setAnt1(new BigDecimal("100.00"));
		conta.setAnt2(new BigDecimal("150.00"));
		conta.setAnt3(new BigDecimal("300.00"));
		conta.setAnt4(new BigDecimal("600.00"));
		
		BigDecimal janeiro = new BigDecimal("50.00");
		BigDecimal fevereiro = new BigDecimal("60.00");
		BigDecimal marco = new BigDecimal("70.00");
		BigDecimal abril = new BigDecimal("200.00");
		
		// Cria o gráfico de barras
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(abril, "mes", "Abr/2019");
		dataset.setValue(marco, "mes", "Mar/2019");
		dataset.setValue(fevereiro, "mes", "Fev/2019");
		dataset.setValue(janeiro, "mes", "Jan/2019");

		JFreeChart chart = ChartFactory.createBarChart("", "", "", dataset, PlotOrientation.HORIZONTAL, false, false, false);
		CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer bar = (BarRenderer) plot.getRenderer();
		CategoryItemRenderer item = ((CategoryPlot) chart.getPlot()).getRenderer();
		ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER_LEFT);
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 30);
		Font font2 = new Font(Font.SANS_SERIF, Font.BOLD, 30);

		NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setVisible(false);

		CategoryAxis axis = plot.getDomainAxis();
		axis.setLowerMargin(0.0);
		axis.setUpperMargin(0.0);
		axis.setTickLabelFont(font);

		ValueAxis range = plot.getRangeAxis();
		range.setLowerMargin(-0.1);
		range.setUpperMargin(0.08);
		
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.WHITE);
		plot.setRangeGridlinePaint(Color.WHITE);
		plot.setRangeMinorGridlinesVisible(false);
		plot.setRangeMinorGridlinePaint(Color.WHITE);
		plot.setOutlineVisible(false);

		// Cria cor e aplica nas barras
		Color cor = new Color(16, 100, 157);
		bar.setBarPainter(new StandardBarPainter());
		bar.setItemMargin(0.0);
		bar.setSeriesPaint(0, cor);
		bar.setSeriesPaint(1, cor);
		bar.setSeriesPaint(2, cor);
		bar.setSeriesPaint(3, cor);

		// Seta propriedades do item
		item.setBaseItemLabelFont(font2);
		item.setBaseSeriesVisible(true);
		item.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		item.setBaseItemLabelsVisible(true);
		item.setBasePositiveItemLabelPosition(position);
		
		BufferedImage grafico = chart.createBufferedImage(1176, 320);
		
		parametros.put("grafico", grafico);
		List contas = new ArrayList<>();
		contas.add(conta);
		
		JRDataSource dataSource = new JRBeanCollectionDataSource(contas);
		JasperPrint jasperPrint = JasperFillManager.fillReport("D:\\Demonstrativo.jasper", parametros, dataSource);
		String nomeArquivo = "Demonstrativo-" + "Janeiro" + "-" + conta.getAno() + "-" + conta.getApartamento() + ".pdf";

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:\\" + nomeArquivo));
		exporter.exportReport();
		
		} catch (Exception e) {
		 System.out.println(e.getMessage());
		}
	}
}
