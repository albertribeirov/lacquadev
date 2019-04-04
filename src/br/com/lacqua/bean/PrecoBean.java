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

	/*
	 * Cancela a alteração
	 */
	public String cancelar() {
		precoGas = null;
		listaPrecosGas = precoGasService.listarPrecoGas();
		return null;
	}

	/*
	 * Exclui um um objeto PrecoGas.
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
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (precoGas.getId() == null) {
				precoGasService.inserir(precoGas);
			} else {
				precoGasService.alterar(precoGas);
			}

			precoGas = null;
			return redirect(Constantes.PRECO_GAS_CADASTRAR);

		} catch (Exception e) {
			handleException(e);
			fc.addMessage(MESSAGE, new FacesMessage(ERRO, e.getMessage()));
			return null;
		}
	}

	public String alterar(Integer id) {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			precoGas = precoGasService.carregar(id);
			fc.addMessage(MESSAGE, new FacesMessage(SUCESSO, "Preço carregado!"));

		} catch (Exception e) {
			handleException(e);
			fc.addMessage(MESSAGE, new FacesMessage(ERRO, e.getMessage()));
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

	public List<PrecoGas> getListaPrecosGas() {
		if (listaPrecosGas == null) {
			listaPrecosGas = precoGasService.listarPrecoGas();
		}
		return listaPrecosGas;
	}

	public void setListaPrecosGas(List<PrecoGas> listaPrecosGas) {
		this.listaPrecosGas = listaPrecosGas;
	}

	/*
	 * Setar cliente
	 */
	public void setPrecoGas(PrecoGas precoGas) {
		this.precoGas = precoGas;
	}
}