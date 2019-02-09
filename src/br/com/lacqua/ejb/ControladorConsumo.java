package br.com.lacqua.ejb;

import java.math.BigDecimal;

import javax.ejb.Local;

import br.com.lacqua.model.Apartamento;

@Local
public interface ControladorConsumo {

	public void calcularConsumo();
	
	public void cadastrarIntervalo(Integer inicio, Integer fim, Apartamento ap);

	public void inserirConsumoMensalApartamento(Integer idApartamento, BigDecimal leitura);
}
