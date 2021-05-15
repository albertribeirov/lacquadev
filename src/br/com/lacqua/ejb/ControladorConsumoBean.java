package br.com.lacqua.ejb;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.lacqua.rn.RNCargaConsumoDocumentoTexto;
import br.com.lacqua.rn.RNGerarRelatoriosDeApartamentosPorCondominio;
import br.com.lacqua.rn.RNInserirConsumoMensalApartamentos;

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Consumo;
import br.com.lacqua.model.Leitura;
import br.com.lacqua.model.PrecoGas;
import br.com.lacqua.model.Torre;
import br.com.lacqua.util.BibliotecaFuncoes;
import net.sf.jasperreports.engine.JRException;

/**
 * Session Bean implementation class ControladorConsumoBean
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorConsumoBean implements ControladorConsumo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void cadastrarIntervalo(Integer inicio, Integer fim, Apartamento apartamento) {
        Integer primeiro = inicio;

        while (primeiro <= fim) {
            Apartamento apartamentoSave = new Apartamento();
            apartamentoSave.setCondominio(apartamento.getCondominio());
            apartamentoSave.setNumero(primeiro);
            if (Objects.nonNull(apartamento.getTorre())) {
                apartamentoSave.setTorre(apartamento.getTorre());
            }
            entityManager.persist(apartamentoSave);
            primeiro++;
        }
    }

    @Override
    public void calcularConsumo() {
        // TODO Necessário planejar o algoritmo
    }

    @Override
    public void inserirConsumoMensalApartamento(BigDecimal pLeitura) {
        entityManager.persist(pLeitura);
    }

    @Override
    public void inserirConsumoMensalApartamentos(List<Apartamento> pApartamentos, List<Leitura> pConsumos, Leitura pLeitura) {
        RNInserirConsumoMensalApartamentos.inserirConsumoMensalApartamentos(pApartamentos, pLeitura, entityManager);
    }

    @Override
    public void cargaConsumoDocumentoTexto(Leitura pLeitura) {
        RNCargaConsumoDocumentoTexto.cargaPorArquivoTXT(pLeitura, entityManager);
    }

    @Override
    public void gerarContaCondominio(Leitura pLeitura, Condominio pCondominio, Torre pTorre) {
        // TODO Falta planejar essa lógica para criar o relátório no Jasper e só então implementar
    }

    @Override
    public void gravarConsumosPorCondominioTorreMes(Leitura pLeitura, List<Leitura> leiturasMesAtual,
            List<Leitura> leiturasMesAnterior) throws FileNotFoundException {

        TreeMap<Integer, Leitura> hashApartamentoMesAtual = new TreeMap<>();
        TreeMap<Integer, Leitura> hashApartamentoMesAnterior = new TreeMap<>();
        BigDecimal valorLeituraMesAnterior;
        BigDecimal valorLeituraMesAtual;
        BigDecimal valorConsumo;
        BigDecimal coeficiente = new BigDecimal("12.35");
        BigDecimal valor;
        Integer mes = pLeitura.getMesReferenciaLeitura();
        Integer ano = pLeitura.getAno();

        PrintWriter pw = new PrintWriter("D:\\saida.txt");

        for (Leitura voConsumoAtual : leiturasMesAtual) {
            Integer apartamento = voConsumoAtual.getApartamento().getNumero();
            hashApartamentoMesAtual.put(apartamento, voConsumoAtual);
        }

        for (Leitura voConsumoMesAnterior : leiturasMesAnterior) {
            Integer apartamento = voConsumoMesAnterior.getApartamento().getNumero();
            hashApartamentoMesAnterior.put(apartamento, voConsumoMesAnterior);
        }

        Set<Integer> apartamentos = hashApartamentoMesAnterior.keySet();
        for (Integer numeroApartamento : apartamentos) {
            Leitura consumoMesAtual = hashApartamentoMesAtual.get(numeroApartamento);
            Leitura consumoMesAnterior = hashApartamentoMesAnterior.get(numeroApartamento);
            valorLeituraMesAtual = consumoMesAtual.getLeitura();
            valorLeituraMesAnterior = consumoMesAnterior.getLeitura();
            valorConsumo = valorLeituraMesAtual.subtract(valorLeituraMesAnterior);
            valorConsumo = BibliotecaFuncoes.escalarConsumo(valorConsumo);
            valor = BibliotecaFuncoes.escalarDinheiro(coeficiente.multiply(valorConsumo));

            PrecoGas precoGas = entityManager.find(PrecoGas.class, 1);
            Consumo consumo = new Consumo();
            consumo.setConsumo(valorConsumo);
            consumo.setAno(ano);
            consumo.setMes(mes);
            consumo.setValorConta(valor);
            consumo.setApartamento(consumoMesAtual.getApartamento());
            consumo.setTorre(consumoMesAtual.getTorre());
            consumo.setCondominio(consumoMesAtual.getCondominio());
            consumo.setPrecoGas(precoGas);

            entityManager.persist(consumo);

            pw.println("Apartamento " + consumoMesAtual.getApartamento().getNumero() + ", consumo " + valorConsumo + ", valor R$ " + valor);
            pw.flush();
        }
        pw.close();
    }

    @Override
    public void salvarPreco(PrecoGas pPrecoGas) {
        entityManager.persist(pPrecoGas);

    }

    @Override
    public void gerarDemonstrativosApartamentos(
            Leitura pLeitura,
            List<Leitura> pLeituraMesProximo,
            List<Leitura> pLeituraMesSelecionado,
            List<Leitura> pLeituraMesAnterior1,
            List<Leitura> pLeituraMesAnterior2,
            List<Leitura> pLeituraMesAnterior3) throws JRException, FileNotFoundException, InterruptedException {

        RNGerarRelatoriosDeApartamentosPorCondominio.gerarRelatoriosDeApartamentosPorCondominio(
                pLeitura, pLeituraMesProximo, pLeituraMesSelecionado, pLeituraMesAnterior1,
                pLeituraMesAnterior2, pLeituraMesAnterior3, entityManager);
    }

    @Override
    public void gerarDemonstrativoTorreTXT(Leitura pLeitura, List<Leitura> pLeituraMesSelecionado, List<Leitura> pLeituraMesAnterior) throws Exception {

        TreeMap<Integer, Leitura> hashLeituraMesSelecionado = new TreeMap<>();

        String torreNome;
        Integer torreNumero;
        String condominioNome;

        condominioNome = (pLeitura.getCondominio() != null) ? pLeitura.getCondominio().getNome() : "";
        torreNome = (pLeitura.getTorre() != null) ? pLeitura.getTorre().getNome() : "";
        torreNumero = (pLeitura.getTorre().getNumero() != null) ? pLeitura.getTorre().getNumero() : 1;

        FileWriter arquivo = new FileWriter("D:\\Gas " + condominioNome + " - " + torreNome + ".txt");
        PrintWriter pw = new PrintWriter(arquivo);

        pw.println("Apto	Anterior	Atual	Consumo	Valor	Data	Torre");

        /*
         * Montagem dos hashs de leituras
         */

        if (pLeituraMesSelecionado != null) {
            for (Leitura leituraAtual : pLeituraMesSelecionado) {
                Integer apartamento = leituraAtual.getApartamento().getNumero();
                hashLeituraMesSelecionado.put(apartamento, leituraAtual);
            }
        }

        if (pLeituraMesAnterior != null) {
            for (Leitura leituraAnterior : pLeituraMesAnterior) {
                Integer numeroApartamentoAnterior = leituraAnterior.getApartamento().getNumero();
                Leitura leituraMesSelecionado = hashLeituraMesSelecionado.get(numeroApartamentoAnterior);

                BigDecimal gasConsumido = BibliotecaFuncoes.escalarConsumo(leituraMesSelecionado.getLeitura().subtract(leituraAnterior.getLeitura()));
                PrecoGas preco = entityManager.find(PrecoGas.class, 1);
                BigDecimal valor = BigDecimal.ZERO;
                Date data = leituraMesSelecionado.getDataRealizacaoLeitura();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String dataTexto = sdf.format(data);
                valor = BibliotecaFuncoes.escalarDinheiro(gasConsumido.multiply(preco.getValor()));

                pw.print(numeroApartamentoAnterior +
                        "\t" + BibliotecaFuncoes.converteBigDecimal(leituraAnterior.getLeitura().toString()) +
                        "\t" + BibliotecaFuncoes.converteBigDecimal(leituraMesSelecionado.getLeitura().toString()) +
                        "\t" + BibliotecaFuncoes.converteBigDecimal(gasConsumido.toString()) +
                        "\t" + BibliotecaFuncoes.converteBigDecimal(valor.toString()) +
                        "\t" + dataTexto +
                        "\t" + torreNumero +
                        "\n");
                pw.flush();
            }
        }

        pw.close();
        arquivo.close();
    }

    @Override
    public void inserirLeituraApartamento(Leitura pLeitura) {
        entityManager.persist(pLeitura);
    }

    @Override
    public void gerarDemonstrativoTorrePDF(Leitura leitura, List<Leitura> leituraMesProximo,
            List<Leitura> leituraMesSelecionado, List<Leitura> leituraMesAnterior1,
            List<Leitura> leituraMesAnterior2, List<Leitura> leituraMesAnterior3) {
        // TODO Auto-generated method stub

    }
}
