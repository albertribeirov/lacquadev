package br.com.lacqua.ejb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
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

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Consumo;
import br.com.lacqua.model.Leitura;
import br.com.lacqua.model.PrecoGas;
import br.com.lacqua.model.Torre;
import br.com.lacqua.util.BibliotecaFuncoes;
import br.com.lacqua.util.Conta;
import br.com.lacqua.util.MailSender;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

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
		LocalDate dataRealizacaoLeitura = pLeitura.getDataRealizacaoLeitura();
		Integer mesReferencia = pLeitura.getMesReferenciaLeitura();
		Condominio cond = new Condominio();
		// TODO Verificar se usa biblioteca de ano ou nao
		Integer ano = dataRealizacaoLeitura.getYear();

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
		//Torre ap = pLeitura.getTorre();
		//Condominio cond = pLeitura.getCondominio();
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

	@Override
	public void gerarDemonstrativosCondominioTorre(Leitura pLeitura, List<Leitura> pLeituraMesSelecionado, List<Leitura> pLeituraMesAnterior, List<Consumo> pConsumoMesSelecionado,
			List<Consumo> pConsumoMesMenos1, List<Consumo> pConsumoMesMenos2, List<Consumo> pConsumoMesMenos3) throws JRException, FileNotFoundException {
		JasperCompileManager.compileReportToFile("D:\\Demonstrativo.jrxml");

		TreeMap<Integer, Leitura> hashLeituraMesSelecionado = new TreeMap<>();
		TreeMap<Integer, Leitura> hashLeituraMesAnterior = new TreeMap<>();
		TreeMap<Integer, Consumo> hashConsumoMesSelecionado = new TreeMap<>();
		TreeMap<Integer, Consumo> hashConsumoMesMenos1 = new TreeMap<>();
		TreeMap<Integer, Consumo> hashConsumoMesMenos2 = new TreeMap<>();
		TreeMap<Integer, Consumo> hashConsumoMesMenos3 = new TreeMap<>();

		BigDecimal valorTotalCondominio = BigDecimal.ZERO;
		BigDecimal consumoTotalCondominio = BigDecimal.ZERO;
		/*
		 * Montagem dos hashs de leituras
		 */
		if (pLeituraMesSelecionado != null) {
			Iterator<Leitura> itLeituraMesSelecionado = pLeituraMesSelecionado.iterator();
			while (itLeituraMesSelecionado.hasNext()) {
				Leitura leituraAtual = itLeituraMesSelecionado.next();
				Integer apartamento = leituraAtual.getApartamento().getNumero();
				hashLeituraMesSelecionado.put(apartamento, leituraAtual);
			}
		}

		if (pLeituraMesAnterior != null) {
			Iterator<Leitura> itLeituraMesAnterior = pLeituraMesAnterior.iterator();
			while (itLeituraMesAnterior.hasNext()) {
				Leitura leituraAnterior = itLeituraMesAnterior.next();
				Integer apartamento = leituraAnterior.getApartamento().getNumero();
				hashLeituraMesAnterior.put(apartamento, leituraAnterior);
			}
		}

		if (pConsumoMesSelecionado != null) {
			Iterator<Consumo> itConsumoMesSelecionado = pConsumoMesSelecionado.iterator();
			while (itConsumoMesSelecionado.hasNext()) {
				Consumo consumoMesSelecionado = itConsumoMesSelecionado.next();
				// Faz somatório do consumo total do condomínio
				BigDecimal consumo = consumoMesSelecionado.getConsumo();
				consumoTotalCondominio = consumoTotalCondominio.add(consumo);
				// Faz somatório do valor a ser pago pelo condomínio
				BigDecimal valor = consumoMesSelecionado.getValorConta();
				valorTotalCondominio = valorTotalCondominio.add(valor);
				Integer apartamento = consumoMesSelecionado.getApartamento().getNumero();
				hashConsumoMesSelecionado.put(apartamento, consumoMesSelecionado);
			}
		}

		if (pConsumoMesMenos1 != null) {
			Iterator<Consumo> itConsumoMesMenos1 = pConsumoMesMenos1.iterator();
			while (itConsumoMesMenos1.hasNext()) {
				Consumo consumoMesMenos1 = itConsumoMesMenos1.next();
				Integer apartamento = consumoMesMenos1.getApartamento().getNumero();
				hashConsumoMesMenos1.put(apartamento, consumoMesMenos1);
			}
		}

		if (pConsumoMesMenos2 != null) {
			Iterator<Consumo> itConsumoMesMenos2 = pConsumoMesMenos2.iterator();
			while (itConsumoMesMenos2.hasNext()) {
				Consumo consumoMesMenos2 = itConsumoMesMenos2.next();
				Integer apartamento = consumoMesMenos2.getApartamento().getNumero();
				hashConsumoMesMenos2.put(apartamento, consumoMesMenos2);
			}
		}

		if (pConsumoMesMenos3 != null) {
			Iterator<Consumo> itConsumoMesMenos3 = pConsumoMesMenos3.iterator();
			while (itConsumoMesMenos3.hasNext()) {
				Consumo consumoMesMenos3 = itConsumoMesMenos3.next();
				Integer apartamento = consumoMesMenos3.getApartamento().getNumero();
				hashConsumoMesMenos3.put(apartamento, consumoMesMenos3);
			}
		}
		
		Session session = MailSender.getInstance().autenticar("albertribeirov@gmail.com", "senhaadm@10");

		Map<String, Object> parametros = new HashMap<String, Object>();
		PrecoGas preco = em.find(PrecoGas.class, 1);
		Condominio cond = pLeitura.getCondominio();
		BigDecimal coeficiente = preco.getValor();
		BigDecimal taxaLeitura = cond.getTaxaLeitura();
		LocalDate dataLeitura = pLeitura.getDataRealizacaoLeitura();
		String dataString = BibliotecaFuncoes.getDataComoString(dataLeitura);
		Integer ano = BibliotecaFuncoes.getAnoFromLocalDate(dataLeitura);
		Integer mes = BibliotecaFuncoes.getMesFromLocalDate(dataLeitura);

		BigDecimal valorConsumo = BigDecimal.ZERO;

		Set<Integer> conj = hashLeituraMesSelecionado.keySet();
		Iterator<Integer> it = conj.iterator();
		while (it.hasNext()) {
			Integer indice = it.next();
			Leitura l = hashLeituraMesSelecionado.get(indice);
			Leitura leitAnt = hashLeituraMesAnterior.get(indice);
			Consumo c = hashConsumoMesSelecionado.get(indice);
			Conta conta = new Conta();
			conta.setAno(ano.toString());
			conta.setMes(BibliotecaFuncoes.getMesPorExtenso(mes));
			conta.setApartamento(l.getApartamento().getNumero().toString());

			if (l.getCliente() != null) {
				conta.setCliente(l.getCliente().getNome());
			} else {
				conta.setCliente("Cliente " + conta.getApartamento());
			}

			valorConsumo = coeficiente.multiply(c.getConsumo());
			conta.setCondominio(pLeitura.getCondominio().getNome());
			conta.setTorre(pLeitura.getTorre().getNome());
			conta.setLeituraAtual(l.getLeitura());
			conta.setLeituraAnterior(leitAnt.getLeitura());
			conta.setCoeficiente(coeficiente);
			conta.setConsumo(c.getConsumo());
			conta.setDataLeituraAtual(dataString);
			conta.setDataLeituraAnterior(dataString);
			conta.setDataProximaLeitura(dataString);
			conta.setValorConsumo(valorConsumo);
			conta.setValorTotal(valorTotalCondominio);
			conta.setVolumeTotal(consumoTotalCondominio);
			conta.setTaxaLeitura(taxaLeitura);
			conta.setValorConsumoComTaxa(conta.getValorConsumo().add(taxaLeitura));
			conta.setQtdApartamentos(pLeituraMesSelecionado.size());

			conta.setAnt1(new BigDecimal("100.00"));
			conta.setAnt2(new BigDecimal("150.00"));
			conta.setAnt3(new BigDecimal("300.00"));
			conta.setAnt4(new BigDecimal("600.00"));

			BigDecimal ant1 = new BigDecimal("100.00");
			BigDecimal ant2 = new BigDecimal("150.00");
			BigDecimal ant3 = new BigDecimal("300.00");
			BigDecimal ant4 = new BigDecimal("600.00");
			parametros.put("ant1", ant1);
			parametros.put("ant2", ant2);
			parametros.put("ant3", ant3);
			parametros.put("ant4", ant4);
			List contas = new ArrayList<>();
			contas.add(conta);

			JRDataSource dataSource = new JRBeanCollectionDataSource(contas);
			JasperPrint jasperPrint = JasperFillManager.fillReport("D:\\Demonstrativo.jasper", parametros, dataSource);
			String nomeArquivo = "Demonstrativo-" + mes + "-" + conta.getAno() + "-" + conta.getApartamento() + ".pdf";

			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:\\Contas\\" + nomeArquivo));
			exporter.exportReport();

			MailSender.getInstance().enviarEmail(
					"albertribeiro@live.com", //Destinatario
					"Demonstrativo-" + mes + "-" + conta.getAno() + "-" + conta.getApartamento() + ".pdf", //Asssunto
					"Demonstrativo referente a " + BibliotecaFuncoes.getMesPorExtenso(mes) + "/" + ano + ".", // Corpo email
					"D:/Contas/" + nomeArquivo, // Caminho do arquivo
					nomeArquivo, //Nome do arquivo
					session); // Sessão já autenticada
		}
	}
}
