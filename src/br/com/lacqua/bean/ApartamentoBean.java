package br.com.lacqua.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.service.ApartamentoService;
import br.com.lacqua.util.Constantes;

@Named("apartamentoBean")
@RequestScoped
public class ApartamentoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7806488216288338187L;

	@Inject
	private ApartamentoService apartamentoService;
	
	private List<Apartamento> apartamentos;
	
	private Apartamento apartamento;
	
	/*
	 * 
	 * Obtém lista de apartamentos
	 * @return Lista de apartamentos
	 */
	public List<Apartamento> getApartamentos(){
		try {
			if (apartamentos == null) {
				apartamentos = apartamentoService.listarApartamentos();
				
			}
			return apartamentos;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String salvar() {
		try {
			apartamentoService.inserir(apartamento);
			return Constantes.SUCESSO;
			//return Constantes.APARTAMENTO_SUCESSO;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Apartamento getApartamento() {
		if (apartamento == null) {
			apartamento = new Apartamento();
		}
		return apartamento;
	}
	
	/*
	 * Obtém apartamento
	 * @return Objeto apartamento
	 */
	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}
	
	public String alterar() {
		return null;
	}
	
	public String excluir() {
		return null;
	}

}