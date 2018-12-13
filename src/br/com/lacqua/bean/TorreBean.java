package br.com.lacqua.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Torre;
import br.com.lacqua.service.TorreService;

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
		try {
			if (torre.getId() == null) {
				torreService.salvar(torre);
			} else {
				torreService.atualizar(torre);
			}
			torre = null; 
			return redirect("cadastrarTorre");
			
		} catch (Exception e) {
			addMessageToRequest(e.getMessage());
			return null;
		}
	}
	
	public String alterar(Integer id) {
		this.torre = torreService.carregar(id);
		return null;
	}
	
	public String excluir() {
		try {
			torreService.excluir(torre.getId());
		} catch (Exception e) {
			handleException(e);
		}
		this.torre = null;
		return null;
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
			e.printStackTrace();
			return null;
		}
	}

}