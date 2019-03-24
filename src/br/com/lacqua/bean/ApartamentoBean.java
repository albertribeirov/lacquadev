package br.com.lacqua.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.ejb.ControladorConsumo;
import br.com.lacqua.model.Apartamento;
import br.com.lacqua.service.ApartamentoService;
import br.com.lacqua.util.Constantes;

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
	 * Obtém lista de apartamentos
	 * 
	 * @return Lista de apartamentos
	 */
	public List<Apartamento> getApartamentos() {
		try {
			apartamentos = apartamentoService.listarApartamentos();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return apartamentos;
	}

	public String salvar() {
		try {
			if (apartamento.getId() == null) {
				apartamentoService.inserir(apartamento);
			} else {
				apartamentoService.alterar(apartamento);
			}
			apartamento = null;
			return redirect(Constantes.APARTAMENTO_CADASTRAR);

		} catch (Exception e) {
			handleException(e);
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
	 * Obtém apartamento
	 * 
	 * @return Objeto apartamento
	 */
	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public String alterar(Integer id) {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			apartamento = apartamentoService.carregar(id);
			fc.addMessage("message", new FacesMessage("Sucesso!", "Apartamento carregado!"));
		} catch (Exception e) {
			fc.addMessage("message", new FacesMessage("Erro!", "Apartamento não carregado!"));
			handleException(e);
		}
		return null;
	}

	public String excluir(Integer id) {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			apartamentoService.excluir(id);
			return redirect(Constantes.APARTAMENTO_CADASTRAR);
		} catch (Exception e) {
			handleException(e);
			fc.addMessage("message", new FacesMessage("Erro!", "Apartamento não excluído!"));
		}
		return null;
	}
}