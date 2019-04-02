package br.com.lacqua.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Condominio;
import br.com.lacqua.service.CondominioService;
import br.com.lacqua.util.Constantes;

@SuppressWarnings("serial")
@Named("condominioBean")
@RequestScoped
public class CondominioBean extends AbstractBean {

	@Inject
	private CondominioService condominioService;

	private Condominio condominio;

	private List<Condominio> condominios;

	/*
	 * Listar condomínios
	 */
	public List<Condominio> getCondominios() throws Exception {
		if (condominios == null) {
			condominios = condominioService.listarCondominios();
		}
		return condominios;
	}

	/**
	 * Salva um condomínio
	 */
	public String salvar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (condominio.getId() == null) {
				condominioService.inserir(condominio);

			} else {
				condominioService.alterar(condominio);
			}

			condominio = null;
			return redirect(Constantes.CONDOMINIO_CADASTRAR);

		} catch (Exception e) {
			fc.addMessage("message", new FacesMessage("Condomínio não salvo!", e.getMessage()));
			return null;
		}
	}

	/**
	 * Altera um condomínio
	 */
	public String alterar(Integer id) {
		this.condominio = condominioService.carregar(id);
		return null;

	}

	/*
	 * Exclui um condomínio
	 */
	public String excluir(Integer id) {
		condominioService.excluir(id);
		condominios = null;
		return redirect(Constantes.CONDOMINIO_CADASTRAR);

	}

	/*
	 * Cadastrar um novo condomínio
	 */
	public String cancelar() {
		condominio = null;
		return null;
	}

	/*
	 * Obter condomínio
	 */
	public Condominio getCondominio() {
		if (condominio == null) {
			condominio = new Condominio();
		}
		return condominio;
	}

	/*
	 * Setar condomínio
	 */
	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}
}