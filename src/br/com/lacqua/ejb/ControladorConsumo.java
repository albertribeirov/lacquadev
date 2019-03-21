package br.com.lacqua.ejb;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Consumo;
import br.com.lacqua.model.Leitura;
import br.com.lacqua.model.PrecoGas;
import br.com.lacqua.model.Torre;
import net.sf.jasperreports.engine.JRException;

@Local
public interface ControladorConsumo {

	public void calcularConsumo();

	public void cadastrarIntervalo(Integer inicio, Integer fim, Apartamento ap);

	public void inserirConsumoMensalApartamento(Integer idApartamento, BigDecimal leitura);

	public void inserirConsumoMensalApartamentos(List<Apartamento> pApartamentos, List<Leitura> pConsumos, Leitura consumoGas);

	public void cargaConsumoDocumentoTexto(Leitura pLeitura);

	public void gerarContaCondominio(Leitura pLeitura, Condominio pCondominio, Torre pTorre);

	public void gravarConsumosPorCondominioTorreMes(Leitura pLeitura, List<Leitura> pMesAtual, List<Leitura> pProximoMes) throws FileNotFoundException;

	public void salvarPreco(PrecoGas pPrecoGas);

	//public void gerarDemonstrativosCondominio(Integer mes, Integer ano, Integer condominio, Integer torre) throws JRException, FileNotFoundException;

	public void gerarDemonstrativosCondominioTorre(Leitura pLeitura, List<Leitura> pLeituraMesSelecionado, List<Leitura> pLeituraMesAnterior, List<Consumo> pConsumoMesSelecionado,
			List<Consumo> pConsumoMesMenos1, List<Consumo> pConsumoMesMenos2, List<Consumo> pConsumoMesMenos3) throws JRException, FileNotFoundException;
}
