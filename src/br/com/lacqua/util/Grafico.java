package br.com.lacqua.util;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
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

public class Grafico {
	public static void main(String[] args) {

		BigDecimal janeiro = new BigDecimal("50.00");
		BigDecimal fevereiro = new BigDecimal("60.00");
		BigDecimal marco = new BigDecimal("70.00");
		BigDecimal abril = new BigDecimal("80.00");
		// Create a simple Bar chart
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(abril, "mes", "Abril");
		dataset.setValue(marco, "mes", "Março");
		dataset.setValue(fevereiro, "mes", "Fevereiro");
		dataset.setValue(janeiro, "mes", "Janeiro");

		JFreeChart chart = ChartFactory.createBarChart("", "", "", dataset, PlotOrientation.HORIZONTAL, false, false, false);
		CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer bar = (BarRenderer) plot.getRenderer();
		CategoryItemRenderer item = ((CategoryPlot) chart.getPlot()).getRenderer();
		ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE3, TextAnchor.CENTER_LEFT);
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
		Font font2 = new Font(Font.SANS_SERIF, Font.PLAIN, 10);

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
		try {
			// ChartUtilities.
			ChartUtilities.saveChartAsJPEG(new File("D:\\Contas\\chart.jpg"), chart, 350, 100);
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
	}
}
