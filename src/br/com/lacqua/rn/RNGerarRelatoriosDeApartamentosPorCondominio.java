package br.com.lacqua.rn;

import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Leitura;
import br.com.lacqua.model.PrecoGas;
import br.com.lacqua.util.BibliotecaFuncoes;
import br.com.lacqua.util.Constantes;
import br.com.lacqua.util.Conta;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
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

import javax.persistence.EntityManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

public class RNGerarRelatoriosDeApartamentosPorCondominio {

    public static void gerarRelatoriosDeApartamentosPorCondominio(
            Leitura pLeitura, List<Leitura> pLeituraMesProximo, List<Leitura> pLeituraMesSelecionado,
            List<Leitura> pLeituraMesAnterior1, List<Leitura> pLeituraMesAnterior2,
            List<Leitura> pLeituraMesAnterior3, EntityManager entityManager)
            throws JRException, FileNotFoundException, InterruptedException {

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
        montarMapaLeiturasComApartamentos(pLeituraMesProximo, hashLeituraMesProximo);
        montarMapaLeiturasComApartamentos(pLeituraMesSelecionado, hashLeituraMesSelecionado);
        montarMapaLeiturasComApartamentos(pLeituraMesAnterior1, hashLeituraMesAnterior1);
        montarMapaLeiturasComApartamentos(pLeituraMesAnterior2, hashLeituraMesAnterior2);
        montarMapaLeiturasComApartamentos(pLeituraMesAnterior3, hashLeituraMesAnterior3);

        // Descomentar caso se torne necessário enviar os demonstrativos para os clientes por e-mail
        //Session session = MailSender.getInstance().autenticar("albertribeirov@gmail.com", "[senha-aqui]");

        Map<String, Object> parametros = new HashMap<>();
        //TODO Criar busca do preço do gás dinamicamente
        PrecoGas preco = entityManager.find(PrecoGas.class, 1);
        Condominio cond = pLeitura.getCondominio();
        BigDecimal coeficiente = preco.getValor();
        BigDecimal taxaLeitura = cond.getTaxaLeitura();
        Date dataLeitura = pLeitura.getDataRealizacaoLeitura();
        String dataString = BibliotecaFuncoes.getDateComoString(dataLeitura);
        Integer ano = BibliotecaFuncoes.getAnoFromDate(dataLeitura);
        Integer mes = BibliotecaFuncoes.getMesFromDate(dataLeitura);

        BigDecimal valorConsumo;

        Set<Integer> conj = hashLeituraMesSelecionado.keySet();
        for (Integer indice : conj) {
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

            BigDecimal consumoMesSelecionado = subtrairLeituras(leituraMesProximo.getLeitura(), leituraMesSelecionado.getLeitura());
            BigDecimal consumoMesAnterior1 = subtrairLeituras(leituraMesSelecionado.getLeitura(), leituraMesAnterior1.getLeitura());
            BigDecimal consumoMesAnterior2 = subtrairLeituras(leituraMesAnterior1.getLeitura(), leituraMesAnterior2.getLeitura());
            BigDecimal consumoMesAnterior3 = subtrairLeituras(leituraMesAnterior2.getLeitura(), leituraMesAnterior3.getLeitura());

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
            config.setMetadataAuthor("Lacqua");
            ex.setConfiguration(config);
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

    private static void montarMapaLeiturasComApartamentos(List<Leitura> pLeituraMesProximo, TreeMap<Integer, Leitura> hashLeituraMesProximo) {
        if (Objects.nonNull(pLeituraMesProximo)) {
            for (Leitura leituraMesProximo : pLeituraMesProximo) {
                Integer apartamento = leituraMesProximo.getApartamento().getNumero();
                hashLeituraMesProximo.put(apartamento, leituraMesProximo);
            }
        }
    }

    private static BigDecimal subtrairLeituras(BigDecimal valorLeitura, BigDecimal valorLeituraMesAnterior) {
        return valorLeitura.subtract(valorLeituraMesAnterior);
    }
}
