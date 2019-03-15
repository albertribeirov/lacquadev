package br.com.lacqua.ejb;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.ConsumoGas;
import br.com.lacqua.model.PrecoGas;
import br.com.lacqua.model.Torre;

@Local
public interface ControladorConsumo {

	public void calcularConsumo();

	public void cadastrarIntervalo(Integer inicio, Integer fim, Apartamento ap);

	public void inserirConsumoMensalApartamento(Integer idApartamento, BigDecimal leitura);

	public void inserirConsumoMensalApartamentos(List<Apartamento> pApartamentos, List<ConsumoGas> pConsumos, ConsumoGas consumoGas);

	public void cargaConsumoDocumentoTexto(ConsumoGas pConsumoGas);
	
	public void gerarContaCondominio(ConsumoGas pConsumoGas, Condominio pCondominio, Torre pTorre);
	
	public void listarConsumosPorCondominioTorreMes(ConsumoGas pConsumoGas,  List<ConsumoGas> pMesAtual, List<ConsumoGas> pProximoMes) throws FileNotFoundException;

	public void salvarPreco(PrecoGas pPrecoGas);
}
