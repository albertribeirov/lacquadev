package br.com.lacqua.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.ejb.ControladorConsumo;
import br.com.lacqua.model.PrecoGas;
import br.com.lacqua.service.PrecoGasService;
import br.com.lacqua.util.Constantes;

@SuppressWarnings("serial")
@Named("precoBean")
@RequestScoped
public class PrecoBean extends AbstractBean {

	@Inject
	private PrecoGasService precoGasService;

	@EJB
	private ControladorConsumo controlador;

	private PrecoGas precoGas;

	private List<PrecoGas> listaPrecosGas = null;

	public List<PrecoGas> getListaPrecosGas() {
		if (listaPrecosGas == null) {
			listaPrecosGas = precoGasService.listarPrecoGas();
		}

		return listaPrecosGas;
	}
	
	/*
	 * Cancela a alteração
	 */
	public String cancelar() {
		precoGas = null;
		listaPrecosGas = precoGasService.listarPrecoGas();
		return null;
	}

	/*
	 * Exclui um precoGas
	 */
	public String excluir(Integer id) {
		try {
			precoGasService.excluir(id);			
		} catch (Exception e) {
			handleException(e);
		}
		
		this.precoGas = null;
		return redirect(Constantes.PRECO_GAS_CADASTRAR);
	}

	/*
	 * Salva um precoGas
	 */
	public String salvar() {
		try {
			if (precoGas.getId() == null) {
				precoGasService.inserir(precoGas);
			} else {
				precoGasService.alterar(precoGas);
			}

			precoGas = null;
			return redirect(Constantes.PRECO_GAS_CADASTRAR);

		} catch (Exception e) {
			addMessageToRequest(e.getMessage());
			return null;
		}
	}

	public void setListaPrecosGas(List<PrecoGas> listaPrecosGas) {
		this.listaPrecosGas = listaPrecosGas;
	}

	public String alterar(Integer id) {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			precoGas = precoGasService.carregar(id);
			fc.addMessage("message", new FacesMessage("Sucesso!", "Preço carregado!"));
			
		} catch (Exception e) {
			handleException(e);
			fc.addMessage("message", new FacesMessage("Erro!", "Erro ao carregar preço!"));
		}
		return null;
	}
	
	/*
	 * Obter cliente
	 */
	public PrecoGas getPrecoGas() {
		if (precoGas == null) {
			precoGas = new PrecoGas();
		}
		return precoGas;
	}

	/*
	 * Setar cliente
	 */
	public void setPrecoGas(PrecoGas precoGas) {
		this.precoGas = precoGas;
	}
}