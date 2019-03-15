package br.com.lacqua.bean;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.ejb.ControladorConsumo;
import br.com.lacqua.model.PrecoGas;
import br.com.lacqua.service.PrecoGasService;

@SuppressWarnings("serial")
@Named("precoBean")
@RequestScoped
public class PrecoBean extends AbstractBean {

	@Inject
	private PrecoGasService precoGasService;

	@EJB
	private ControladorConsumo controlador;

	@Inject
	private PrecoGas precoGas;

	private List<PrecoGas> listaPrecosGas = null;

	public List<PrecoGas> getListaPrecosGas() {
		if (listaPrecosGas == null) {
			listaPrecosGas = precoGasService.listarPrecoGas();
		}

		return listaPrecosGas;
	}

	public String salvarPreco() {
		controlador.salvarPreco(precoGas);
		System.out.println(precoGas);
		return null;
	}
	public PrecoGas getPrecoGas() {
		return precoGas;
	}

	public void setPrecoGas(PrecoGas precoGas) {
		this.precoGas = precoGas;
	}

	public void setListaPrecosGas(List<PrecoGas> listaPrecosGas) {
		this.listaPrecosGas = listaPrecosGas;
	}

	public void excluir(Integer id) {

	}

	public void alterar(Integer id) {

	}
}