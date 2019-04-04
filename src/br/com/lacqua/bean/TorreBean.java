package br.com.lacqua.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Torre;
import br.com.lacqua.service.TorreService;
import br.com.lacqua.util.Constantes;

@SuppressWarnings("serial")
@Named("torreBean")
@RequestScoped
public class TorreBean extends AbstractBean {

	@Inject
	private TorreService torreService;

	private Torre torre;

	private Condominio condominio;

	private List<Torre> torres;

	/**
	 * Salva ou altera uma torre.
	 */
	public String salvar() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (torre.getId() == null) {
				torreService.salvar(torre);
			} else {
				torreService.atualizar(torre);
			}
			torre = null;
			return redirect(Constantes.TORRE_CADASTRAR);

		} catch (Exception e) {
			fc.addMessage(MESSAGE, new FacesMessage(ERRO, e.getMessage()));
			return null;
		}
	}

	public String cancelar() {
		torre = null;
		return null;
	}

	/*
	 * Carrega uma torre na tela para que ela seja alterada.
	 */
	public String alterar(Integer id) {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			this.torre = torreService.carregar(id);
			return null;
		} catch (Exception e) {
			fc.addMessage(MESSAGE, new FacesMessage(ERRO, e.getMessage()));
			handleException(e);
			return null;
		}
	}

	/*
	 * Exclui uma torre.
	 */
	public String excluir(Integer id) {
		torreService.excluir(id);
		torres = null;
		return redirect(Constantes.TORRE_CADASTRAR);
	}

	public void carregarTorresPorCondominio(ValueChangeEvent event) {
		Condominio cond = (Condominio) event.getNewValue();
		torres = torreService.listarTorresPorCondominio(cond.getId());
	}

	/*
	 * 
	 * Obtém lista de torres
	 * 
	 * @return Lista de torres
	 */
	public List<Torre> getTorres() {
		try {
			if (torres == null) {
				torres = torreService.listarTorres();

			}
			return torres;

		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}

	public Torre getTorre() {
		if (torre == null) {
			torre = new Torre();
		}
		return torre;
	}

	/*
	 * Obtém torre
	 * 
	 * @return Objeto torre
	 */
	public void setTorre(Torre torre) {
		this.torre = torre;
	}

	/*
	 * Obtém torre
	 * 
	 * @return Objeto torre
	 */
	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	/*
	 * 
	 * Obtém lista de torres
	 * 
	 * @return Lista de torres
	 */
	public Condominio getCondominio() {
		try {
			if (condominio == null) {
				condominio = new Condominio();
			}
			return condominio;

		} catch (Exception e) {
			handleException(e);
			return null;
		}
	}
}