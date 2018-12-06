package br.com.lacqua.bean;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Torre;
import br.com.lacqua.service.TorreService;

@Named("torreBean")
@RequestScoped
public class TorreBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7806488216288338187L;

	@Inject
	private TorreService torreService;

	private Torre torre;
	
	private Condominio condominio;

	private List<Torre> torres;

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
			e.printStackTrace();
			return null;
		}
	}

	public String salvar() {
		try {

			//torre.setCondominio(condominio);
			torreService.inserir(torre);
			return null;
			// return Constantes.APARTAMENTO_SUCESSO;

		} catch (Exception e) {
			e.printStackTrace();
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