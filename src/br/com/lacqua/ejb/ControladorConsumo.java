package br.com.lacqua.ejb;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Local;

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.ConsumoGas;

@Local
public interface ControladorConsumo {

	public void calcularConsumo();

	public void cadastrarIntervalo(Integer inicio, Integer fim, Apartamento ap);

	public void inserirConsumoMensalApartamento(Integer idApartamento, BigDecimal leitura);

	public void inserirConsumoMensalApartamentos(List<Apartamento> pApartamentos, List<ConsumoGas> pConsumos,
			ConsumoGas consumoGas);

	void cargaConsumoDocumentoTexto(ConsumoGas pConsumoGas);
}
