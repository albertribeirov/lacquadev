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
@Named("apartamentoBean")
@RequestScoped
public class ApartamentoBean extends AbstractBean {

	@EJB
	private ControladorConsumo controlador;
	@Inject
	private ApartamentoService apartamentoService;

	private List<Apartamento> apartamentos;

	private Apartamento apartamento;

	/*
	 * 
	 * Obt�m lista de apartamentos
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

	public String salvar() {
		try {
			if (apartamento.getId() == null) {
				apartamentoService.inserir(apartamento);
			} else {
				apartamentoService.alterar(apartamento);
			}
			apartamento = null;
			return redirect("cadastrarApartamento");

		} catch (Exception e) {
			addMessageToRequest(e.getMessage());
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
	 * Obt�m apartamento
	 * 
	 * @return Objeto apartamento
	 */
	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public String alterar(Integer id) {
		apartamento = apartamentoService.carregar(id);
		return null;
	}

	public String excluir() {
		return null;
	}

}