package br.com.lacqua.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.ejb.ControladorConsumo;
import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Torre;
import br.com.lacqua.service.ApartamentoService;
import br.com.lacqua.util.Constantes;

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

		} catch (Exception e) {
			handleException(e);
			addMessageToRequest(e.getMessage());
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
	
	public String cancelar() {
		apartamento = null;
		return null;
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
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			apartamento = apartamentoService.carregar(id);
			facesContext.addMessage("message", new FacesMessage(SUCESSO, "Apartamento carregado!"));
		} catch (Exception e) {
			facesContext.addMessage("message", new FacesMessage(ERRO, "Apartamento n�o carregado!"));
			handleException(e);
		}
		return null;
	}

	public String excluir(Integer id) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			apartamentoService.excluir(id);
			return redirect(Constantes.APARTAMENTO_CADASTRAR);
		} catch (Exception e) {
			handleException(e);
			facesContext.addMessage("message", new FacesMessage(ERRO, "Apartamento n�o exclu�do!"));
		}
		return null;
	}
	
	public void carregarApartamentosPorTorreCondominio(ValueChangeEvent event) {
		Torre torre = (Torre) event.getNewValue();
		apartamentos = apartamentoService.listarApartamentosPorCondominioTorre(torre.getCondominio().getId(), torre.getId());
	}
}