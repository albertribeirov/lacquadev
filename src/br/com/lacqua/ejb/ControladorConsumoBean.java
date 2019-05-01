package br.com.lacqua.ejb;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.mail.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Consumo;
import br.com.lacqua.model.Leitura;
import br.com.lacqua.model.PrecoGas;
import br.com.lacqua.model.Torre;
import br.com.lacqua.util.BibliotecaFuncoes;
import br.com.lacqua.util.Constantes;
import br.com.lacqua.util.Conta;
import br.com.lacqua.util.MailSender;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

/**
 * Session Bean implementation class ControladorConsumoBean
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorConsumoBean implements ControladorConsumo {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void cadastrarIntervalo(Integer inicio, Integer fim, Apartamento ap) {
		Integer primeiro = inicio;

		while (primeiro <= fim) {
			Apartamento apartamento = new Apartamento();
			apartamento.setCondominio(ap.getCondominio());
			apartamento.setNumero(primeiro);
			if (ap.getTorre() != null) {
				apartamento.setTorre(ap.getTorre());
			}
			em.persist(apartamento);
			primeiro++;
		}
	}

	@Override
	public void calcularConsumo() {

	}

	@Override
	public void inserirConsumoMensalApartamento(Integer idApartamento, BigDecimal leitura) {
		Apartamento ap = em.find(Apartamento.class, idApartamento);
		Leitura consumo = new Leitura();

		consumo.setApartamento(ap);
		consumo.setLeitura(leitura);
		consumo.setCliente(ap.getCliente());
		consumo.setTorre(ap.getTorre());
		consumo.setCondominio(ap.getCondominio());

		em.persist(consumo);
	}

	@Override
	public void inserirConsumoMensalApartamentos(List<Apartamento> pApartamentos, List<Leitura> pConsumos, Leitura pLeitura) {

		Iterator<Apartamento> it = pApartamentos.iterator();
		while (it.hasNext()) {
			Apartamento ap = it.next();
			Leitura consumo = new Leitura();
			BigDecimal leitura = null;
			boolean isGravar = false;

			if (ap.getLeitura() != null) {
				isGravar = true;
				leitura = ap.getLeitura();
				leitura = leitura.setScale(0, RoundingMode.HALF_EVEN);
				consumo.setLeitura(leitura);
			}

			if (ap.getCliente() != null) {
				consumo.setCliente(ap.getCliente());
			}

			if (ap.getTorre() != null) {
				consumo.setTorre(ap.getTorre());
			}

			if (pLeitura != null) {
				consumo.setMesReferenciaLeitura(pLeitura.getMesReferenciaLeitura());
				consumo.setDataRealizacaoLeitura(pLeitura.getDataRealizacaoLeitura());
			}

			consumo.setApartamento(ap);
			consumo.setCondominio(ap.getCondominio());
			consumo.setTorre(ap.getTorre());

			if (isGravar == true) {
				em.persist(consumo);
			}
		}
	}

	@Override
	public void cargaConsumoDocumentoTexto(Leitura pLeitura) {
		Date dataRealizacaoLeitura = pLeitura.getDataRealizacaoLeitura();
		Integer mesReferencia = pLeitura.getMesReferenciaLeitura();
		Condominio cond = new Condominio();
		// TODO Verificar se usa biblioteca de ano ou nao
		Integer ano = BibliotecaFuncoes.getAnoFromDate(dataRealizacaoLeitura);

		List<String> texto = BibliotecaFuncoes.lerArquivo("D:/arquivo.txt");
		Iterator<String> it = texto.iterator();

		while (it.hasNext()) {
			Leitura consumoGas = new Leitura();
			BigDecimal leitura = BigDecimal.ZERO;
			String linhaConcatenada = "";
			String numeroApartamento = "";
			List<String> line = new ArrayList<String>();
			List<Apartamento> aps = new ArrayList<Apartamento>();

			linhaConcatenada = it.next();
			line = BibliotecaFuncoes.split(linhaConcatenada, ";");
			numeroApartamento = line.get(0);
			leitura = new BigDecimal(line.get(1));
			leitura = BibliotecaFuncoes.escalarConsumo(leitura);
			cond = em.find(Condominio.class, pLeitura.getCondominio().getId());

			aps = cond.getApartamentos();

			Iterator<Apartamento> iter = aps.iterator();
			while (iter.hasNext()) {
				Apartamento apart = iter.next();

				if (numeroApartamento.equals(apart.getNumero().toString())) {
					consumoGas.setApartamento(apart);
					consumoGas.setCondominio(apart.getCondominio());

					if (apart.getTorre() != null) {
						consumoGas.setTorre(apart.getTorre());
					}

					if (apart.getCliente() != null) {
						consumoGas.setCliente(apart.getCliente());
					}

					consumoGas.setLeitura(leitura);
					consumoGas.setDataRealizacaoLeitura(dataRealizacaoLeitura);
					consumoGas.setMesReferenciaLeitura(mesReferencia);
					consumoGas.setAno(ano);
					em.persist(consumoGas);
				}
			}
		}
	}

	@Override
	public void gerarContaCondominio(Leitura pLeitura, Condominio pCondominio, Torre pTorre) {
		// ConsumosGas consumo =
		// Condominio cond = em.find(Condominio.class, pCondominio.getId());
	}

	@Override
	public void gravarConsumosPorCondominioTorreMes(Leitura pLeitura, List<Leitura> pMesAtual, List<Leitura> pMesAnterior) throws FileNotFoundException {
		TreeMap<Integer, Leitura> hashApartamentoMesAtual = new TreeMap<Integer, Leitura>();
		TreeMap<Integer, Leitura> hashApartamentoMesAnterior = new TreeMap<Integer, Leitura>();
		BigDecimal leituraMesAnterior = BigDecimal.ZERO;
		BigDecimal leituraMesAtual = BigDecimal.ZERO;
		BigDecimal valorConsumo = BigDecimal.ZERO;
		BigDecimal coeficiente = new BigDecimal("12.35");
		BigDecimal valor = BigDecimal.ZERO;
		Integer mes = pLeitura.getMesReferenciaLeitura();
		Integer ano = pLeitura.getAno();
		// Torre ap = pLeitura.getTorre();
		// Condominio cond = pLeitura.getCondominio();
		PrintWriter pw = new PrintWriter(new File("D:\\saida.txt"));

		Iterator<Leitura> itMesAtual = pMesAtual.iterator();
		while (itMesAtual.hasNext()) {
			Leitura voConsumoAtual = itMesAtual.next();
			Integer apartamento = voConsumoAtual.getApartamento().getNumero();
			hashApartamentoMesAtual.put(apartamento, voConsumoAtual);
		}

		Iterator<Leitura> itMesAnterior = pMesAnterior.iterator();
		while (itMesAnterior.hasNext()) {
			Leitura voConsumoMesAnterior = itMesAnterior.next();
			Integer apartamento = voConsumoMesAnterior.getApartamento().getNumero();
			hashApartamentoMesAnterior.put(apartamento, voConsumoMesAnterior);
		}

		Set<Integer> apartamentos = hashApartamentoMesAnterior.keySet();
		Iterator<Integer> it = apartamentos.iterator();
		while (it.hasNext()) {
			Integer numeroAp = it.next();

			Leitura consumoMesAtual = hashApartamentoMesAtual.get(numeroAp);
			Leitura consumoMesAnterior = hashApartamentoMesAnterior.get(numeroAp);
			leituraMesAtual = consumoMesAtual.getLeitura();
			leituraMesAnterior = consumoMesAnterior.getLeitura();
			valorConsumo = leituraMesAtual.subtract(leituraMesAnterior);
			valorConsumo = BibliotecaFuncoes.escalarConsumo(valorConsumo);
			valor = BibliotecaFuncoes.escalarDinheiro(coeficiente.multiply(valorConsumo));

			PrecoGas precoGas = em.find(PrecoGas.class, 1);
			Consumo consumo = new Consumo();
			consumo.setConsumo(valorConsumo);
			consumo.setAno(ano);
			consumo.setMes(mes);
			consumo.setValorConta(valor);
			consumo.setApartamento(consumoMesAtual.getApartamento());
			consumo.setTorre(consumoMesAtual.getTorre());
			consumo.setCondominio(consumoMesAtual.getCondominio());
			consumo.setPrecoGas(precoGas);

			em.persist(consumo);

			pw.println("Apartamento " + consumoMesAtual.getApartamento().getNumero() + ", consumo " + valorConsumo + ", valor R$ " + valor);
			pw.flush();
		}
		pw.close();
	}

	@Override
	public void salvarPreco(PrecoGas pPrecoGas) {
		em.persist(pPrecoGas);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void gerarDemonstrativosCondominioTorre(Leitura pLeitura, List<Leitura> pLeituraMesProximo, List<Leitura> pLeituraMesSelecionado, List<Leitura> pLeituraMesAnterior1,
			List<Leitura> pLeituraMesAnterior2, List<Leitura> pLeituraMesAnterior3) throws JRException, FileNotFoundException, Exception {

		JasperCompileManager.compileReportToFile("D:\\Demonstrativo.jrxml");

		TreeMap<Integer, Leitura> hashLeituraMesProximo = new TreeMap<>();
		TreeMap<Integer, Leitura> hashLeituraMesSelecionado = new TreeMap<>();
		TreeMap<Integer, Leitura> hashLeituraMesAnterior1 = new TreeMap<>();
		TreeMap<Integer, Leitura> hashLeituraMesAnterior2 = new TreeMap<>();
		TreeMap<Integer, Leitura> hashLeituraMesAnterior3 = new TreeMap<>();

		BigDecimal valorTotalCondominio = BigDecimal.ZERO;
		BigDecimal consumoTotalCondominio = BigDecimal.ZERO;

		/*
		 * Montagem dos hashs de leituras
		 */
		if (pLeituraMesProximo != null) {
			Iterator<Leitura> itLeituraMesProximo = pLeituraMesProximo.iterator();
			while (itLeituraMesProximo.hasNext()) {
				Leitura leituraMesProximo = itLeituraMesProximo.next();
				Integer apartamento = leituraMesProximo.getApartamento().getNumero();
				hashLeituraMesProximo.put(apartamento, leituraMesProximo);
			}
		}

		if (pLeituraMesSelecionado != null) {
			Iterator<Leitura> itLeituraMesSelecionado = pLeituraMesSelecionado.iterator();
			while (itLeituraMesSelecionado.hasNext()) {
				Leitura leituraAtual = itLeituraMesSelecionado.next();
				Integer apartamento = leituraAtual.getApartamento().getNumero();
				hashLeituraMesSelecionado.put(apartamento, leituraAtual);
			}
		}

		if (pLeituraMesAnterior1 != null) {
			Iterator<Leitura> itLeituraMesAnterior1 = pLeituraMesAnterior1.iterator();
			while (itLeituraMesAnterior1.hasNext()) {
				Leitura leituraAnterior1 = itLeituraMesAnterior1.next();
				Integer apartamento = leituraAnterior1.getApartamento().getNumero();
				hashLeituraMesAnterior1.put(apartamento, leituraAnterior1);
			}
		}

		if (pLeituraMesAnterior2 != null) {
			Iterator<Leitura> itLeituraMesAnterior2 = pLeituraMesAnterior2.iterator();
			while (itLeituraMesAnterior2.hasNext()) {
				Leitura leituraMesAnterior2 = itLeituraMesAnterior2.next();
				Integer apartamento = leituraMesAnterior2.getApartamento().getNumero();
				hashLeituraMesAnterior2.put(apartamento, leituraMesAnterior2);
			}
		}

		if (pLeituraMesAnterior3 != null) {
			Iterator<Leitura> itLeituraMesAnterior3 = pLeituraMesAnterior3.iterator();
			while (itLeituraMesAnterior3.hasNext()) {
				Leitura leituraMesAnterior3 = itLeituraMesAnterior3.next();
				Integer apartamento = leituraMesAnterior3.getApartamento().getNumero();
				hashLeituraMesAnterior3.put(apartamento, leituraMesAnterior3);
			}
		}

		Session session = MailSender.getInstance().autenticar("albertribeirov@gmail.com", "senhaadm@10");

		Map<String, Object> parametros = new HashMap<String, Object>();
		PrecoGas preco = em.find(PrecoGas.class, 1);
		Condominio cond = pLeitura.getCondominio();
		BigDecimal coeficiente = preco.getValor();
		BigDecimal taxaLeitura = cond.getTaxaLeitura();
		Date dataLeitura = pLeitura.getDataRealizacaoLeitura();
		String dataString = BibliotecaFuncoes.getDateComoString(dataLeitura);
		Integer ano = BibliotecaFuncoes.getAnoFromDate(dataLeitura);
		Integer mes = BibliotecaFuncoes.getMesFromDate(dataLeitura);

		BigDecimal valorConsumo = BigDecimal.ZERO;

		Set<Integer> conj = hashLeituraMesSelecionado.keySet();
		Iterator<Integer> it = conj.iterator();
		while (it.hasNext()) {
			Integer indice = it.next();
			Leitura leituraMesProximo = hashLeituraMesProximo.get(indice);
			Leitura leituraMesSelecionado = hashLeituraMesSelecionado.get(indice);
			Leitura leituraMesAnterior1 = hashLeituraMesAnterior1.get(indice);
			Leitura leituraMesAnterior2 = hashLeituraMesAnterior1.get(indice);
			Leitura leituraMesAnterior3 = hashLeituraMesAnterior1.get(indice);
			Conta conta = new Conta();
			conta.setAno(ano.toString());
			conta.setMes(BibliotecaFuncoes.getMesPorExtenso(mes));
			conta.setApartamento(leituraMesSelecionado.getApartamento().getNumero().toString());

			if (leituraMesSelecionado.getCliente() != null) {
				conta.setCliente(leituraMesSelecionado.getCliente().getNome());
			} else {
				conta.setCliente("Cliente " + conta.getApartamento());
			}

			valorConsumo = coeficiente.multiply(leituraMesProximo.getLeitura().subtract(leituraMesSelecionado.getLeitura()));
			conta.setCondominio(pLeitura.getCondominio().getNome());
			
			if (pLeitura.getTorre() != null) {
				conta.setTorre(pLeitura.getTorre().getNome());				
			} else {
				conta.setTorre("");
			}
			conta.setLeituraAtual(leituraMesProximo.getLeitura());
			conta.setLeituraAnterior(leituraMesSelecionado.getLeitura());
			conta.setCoeficiente(coeficiente);
			conta.setConsumo(leituraMesProximo.getLeitura().subtract(leituraMesSelecionado.getLeitura()));
			conta.setDataLeituraAtual(dataString);
			conta.setDataLeituraAnterior(dataString);
			conta.setDataProximaLeitura(dataString);
			conta.setValorConsumo(valorConsumo);
			conta.setValorTotal(valorTotalCondominio);
			conta.setVolumeTotal(consumoTotalCondominio);
			conta.setTaxaLeitura(taxaLeitura);
			conta.setValorConsumoComTaxa(conta.getValorConsumo().add(taxaLeitura));
			conta.setQtdApartamentos(pLeituraMesSelecionado.size());

			BigDecimal consumoMesSelecionado = leituraMesProximo.getLeitura().subtract(leituraMesSelecionado.getLeitura());
			BigDecimal consumoMesAnterior1 = leituraMesSelecionado.getLeitura().subtract(leituraMesAnterior1.getLeitura());
			BigDecimal consumoMesAnterior2 = leituraMesAnterior1.getLeitura().subtract(leituraMesAnterior2.getLeitura());
			BigDecimal consumoMesAnterior3 = leituraMesAnterior2.getLeitura().subtract(leituraMesAnterior3.getLeitura());

			String mesSelecionado = BibliotecaFuncoes.getMesPorExtenso(leituraMesSelecionado.getMesReferenciaLeitura());
			String mesAnterior1 = BibliotecaFuncoes.getMesPorExtenso(leituraMesAnterior1.getMesReferenciaLeitura());
			String mesAnterior2 = BibliotecaFuncoes.getMesPorExtenso(leituraMesAnterior2.getMesReferenciaLeitura());
			String mesAnterior3 = BibliotecaFuncoes.getMesPorExtenso(leituraMesAnterior3.getMesReferenciaLeitura());

			/*
			 * Gráfico starts
			 */

			// Cria o gráfico de barras
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			dataset.setValue(consumoMesSelecionado, Constantes.MES, mesSelecionado.substring(0, 2) + "/" + leituraMesSelecionado.getAno().toString());
			dataset.setValue(consumoMesAnterior1, Constantes.MES, mesAnterior1.substring(0, 2) + "/" + leituraMesAnterior1.getAno().toString());
			dataset.setValue(consumoMesAnterior2, Constantes.MES, mesAnterior2.substring(0, 2) + "/" + leituraMesAnterior1.getAno().toString());
			dataset.setValue(consumoMesAnterior3, Constantes.MES, mesAnterior3.substring(0, 2) + "/" + leituraMesAnterior1.getAno().toString());

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
			String nomeArquivo = "Demonstrativo-" + mes + "-" + conta.getAno() + "-" + conta.getApartamento() + ".pdf";

			Exporter ex = new JRPdfExporter();
			SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
			config .setMetadataAuthor("Lacqua");
			ex.setConfiguration(config );
			ex.setExporterInput(new SimpleExporterInput(jasperPrint));
			ex.setExporterOutput(new SimpleOutputStreamExporterOutput(new FileOutputStream("D:\\Contas\\" + nomeArquivo)));
			ex.exportReport();
			
			Thread.sleep(1000);
			
			
			/*
			 * JRExporter exporter = new JRPdfExporter();
			 * exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			 * exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:\\Contas\\" +
			 * nomeArquivo));
			 * exporter.exportReport();
			 */
		}
	}
}
