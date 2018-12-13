package br.com.lacqua.ejb;

import javax.ejb.Local;

import br.com.lacqua.model.Apartamento;

@Local
public interface ControladorConsumo {

	public void calcularConsumo();
	
	public void cadastrarIntervalo(Integer inicio, Integer fim, Apartamento ap);
}
