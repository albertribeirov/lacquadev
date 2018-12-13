package br.com.lacqua.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.ejb.ControladorConsumo;
import br.com.lacqua.model.Apartamento;
import br.com.lacqua.service.ApartamentoService;

@SuppressWarnings("serial")
@Named("intervaloBean")
@RequestScoped
public class IntervaloBean extends AbstractBean {

	@EJB
	private ControladorConsumo controlador;

	@Inject
	private ApartamentoService apartamentoService;

	private List<Apartamento> apartamentos;
	
	private Apartamento apartamento;

	private Integer inicio;
	private Integer fim;

	
	public String cadastrarIntervalo() {
		try {
			controlador.cadastrarIntervalo(inicio, fim, apartamento);			
			return redirect("cadastrarApartamento");
		} catch (Exception e) {
			handleException(e);
		}
		return null;
	}

	/*
	 * 
	 * Obtém lista de apartamentos
	 * 
	 * @return Lista de apartamentos
	 */
	public List<Apartamento> getApartamentos() {
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

	public Integer getFim() {
		return fim;
	}

	public void setFim(Integer fim) {
		this.fim = fim;
	}

	public Integer getInicio() {
		return inicio;
	}

	public void setInicio(Integer inicio) {
		this.inicio = inicio;
	}

	public Apartamento getApartamento() {
		if (apartamento == null) {
			apartamento = new Apartamento();
		}
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}
}
