package br.com.lacqua.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

public class TesteJPA {

	public static void main(String[] args) throws JRException, FileNotFoundException, SQLException {
		
		JasperCompileManager.compileReportToFile("D:\\Demonstrativo.jrxml");
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		List<Integer> lis = new ArrayList();
		lis.add(1);
		lis.add(1);
		lis.add(1);
		lis.add(1);
		lis.add(1);
		BigDecimal coeficiente =  new BigDecimal("12.35");
		BigDecimal valorTotalCondominio = new BigDecimal("98765.00");
		BigDecimal consumoTotalCondominio = new BigDecimal("654.00");
		BigDecimal lei = new BigDecimal("54.16");
		BigDecimal taxaLeitura = new BigDecimal("4.00");
		String dataLeitura = "17/03/2019";
		BigDecimal valorConsumo = BigDecimal.ZERO;
		BigDecimal leituraAnterior = new BigDecimal("58.559");
		BigDecimal leituraAtual = new BigDecimal("68.018");
		BigDecimal consumo =  leituraAtual.subtract(leituraAnterior);
		BigDecimal individual = BibliotecaFuncoes.escalarDinheiro(consumo.multiply(coeficiente));
	
		Conta conta = new Conta();
		conta.setId(1);
		conta.setAno("2018");
		conta.setConsumo(consumo);
		conta.setValorTotal(valorTotalCondominio);
		conta.setVolumeTotal(consumoTotalCondominio);
		conta.setApartamento("1004");
		conta.setCondominio("ACQUA HOME CLUB");
		conta.setTorre("ATLANTIC");
		conta.setCliente("GEORGE BRENO DE AGUIAR E SILVA");
		conta.setCoeficiente(coeficiente);
		conta.setMes("Novembro");
		conta.setValorConsumoComTaxa(individual.add(taxaLeitura));
		conta.setDataLeituraAnterior(dataLeitura);
		conta.setDataLeituraAtual(dataLeitura);
		conta.setDataProximaLeitura(dataLeitura);
		conta.setLeituraAnterior(leituraAnterior);
		conta.setLeituraAtual(leituraAtual);
		conta.setTaxaLeitura(taxaLeitura);
		conta.setValorConsumo(BibliotecaFuncoes.escalarDinheiro(consumo.multiply(coeficiente)));
		conta.setQtdApartamentos(lis.size());
		conta.setAnt1(new BigDecimal("100.00"));
		conta.setAnt2(new BigDecimal("150.00"));
		conta.setAnt3(new BigDecimal("300.00"));
		conta.setAnt4(new BigDecimal("600.00"));
		List contas = new ArrayList<>();
		contas.add(conta);
		
		
		JRDataSource dataSource = new JRBeanCollectionDataSource(contas);
		JasperPrint jasperPrint = JasperFillManager.fillReport("D:\\Demonstrativo.jasper", parametros, dataSource);
		
		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:\\Demonstrativo.pdf"));
		exporter.exportReport();
		
		/*Exporter pdfExporter = new JRPdfExporter();
		pdfExporter.setExporterInput(SimpleExporterInput.getInstance(contas));
		pdfExporter.setExporterOutput(new SimpleWriterExporterOutput("D:\\Demonstrativo.pdf"));
		SimpleExporterConfiguration cfg = new SimpleExporterConfiguration();
		pdfExporter.setConfiguration(cfg);
		pdfExporter.exportReport();*/
	}
}