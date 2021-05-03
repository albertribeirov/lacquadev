package br.com.lacqua.ejb;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Leitura;
import br.com.lacqua.model.PrecoGas;
import br.com.lacqua.model.Torre;
import net.sf.jasperreports.engine.JRException;

@Local
public interface ControladorConsumo {

	void calcularConsumo();

	void cadastrarIntervalo(Integer inicio, Integer fim, Apartamento ap);

	void inserirConsumoMensalApartamento(BigDecimal pLeitura);

	void inserirConsumoMensalApartamentos(List<Apartamento> pApartamentos, List<Leitura> pConsumos, Leitura consumoGas);

	void cargaConsumoDocumentoTexto(Leitura pLeitura);

	void gerarContaCondominio(Leitura pLeitura, Condominio pCondominio, Torre pTorre);

	void gravarConsumosPorCondominioTorreMes(Leitura pLeitura, List<Leitura> pMesAtual, List<Leitura> pProximoMes)
			throws FileNotFoundException;

	void salvarPreco(PrecoGas pPrecoGas);

	/*
	public void gerarDemonstrativosCondominio(Integer mes, Integer ano, Integer condominio, Integer torre)
	 throws JRException, FileNotFoundException;
	*/

	void gerarDemonstrativosApartamentos(Leitura pLeitura, List<Leitura> pLeituraMesProximo,
		 List<Leitura> pLeituraMesSelecionado, List<Leitura> pConsumoMesMenos1,
		 List<Leitura> pConsumoMesMenos2, List<Leitura> pConsumoMesMenos3)
			throws JRException, FileNotFoundException, InterruptedException;

	void gerarDemonstrativoTorreTXT(Leitura pLeitura, List<Leitura> pLeituraMesSelecionado,
		List<Leitura> pLeituraMesAnterior) throws Exception;

	void inserirLeituraApartamento(Leitura pLeitura);

	void gerarDemonstrativoTorrePDF(Leitura leitura, List<Leitura> leituraMesProximo,
		List<Leitura> leituraMesSelecionado, List<Leitura> leituraMesAnterior1,
		List<Leitura> leituraMesAnterior2, List<Leitura> leituraMesAnterior3);
}
