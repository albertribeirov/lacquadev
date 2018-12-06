package br.com.lacqua.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.TarifaCompesa;
import br.com.lacqua.service.TarifaCompesaService;

@SuppressWarnings("serial")
@Named("tarifaCompesaBean")
@RequestScoped
public class TarifaCompesaBean extends AbstractBean {

	@Inject
	private TarifaCompesaService tarifaCompesaService;

	private TarifaCompesa tarifaCompesa;

	private List<TarifaCompesa> tarifasCompesa;

	/*
	 * Listar tarifas da compesa
	 */
	public List<TarifaCompesa> getTarifasCompesa() throws Exception {
		if (tarifasCompesa == null) {
			tarifasCompesa = tarifaCompesaService.listarTarifasCompesa();
		}
		return tarifasCompesa;
	}

	/*
	 * Salva uma tarifa da compesa
	 */
	public String salvar() {
		try {
			if (tarifaCompesa.getId() == null) {
				tarifaCompesaService.inserir(tarifaCompesa);

			} else {
				tarifaCompesaService.alterar(tarifaCompesa);
			}

			tarifaCompesa = null;
			return null;
			// return Constantes.CONDOMINIO_SUCESSO;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * Altera uma tarifa da compesa
	 */
	public String alterar(Integer id) {
		tarifaCompesa = tarifaCompesaService.carregar(id);
		return null;

	}

	/*
	 * Exclui uma tarifa da compesa
	 */
	public String excluir(Integer id) {
		tarifaCompesaService.excluir(id);
		tarifasCompesa = null;
		return "cadastrarTarifaCompesa";

	}

	/*
	 * Cadastrar uma nova tarifa da compesa
	 */
	public String novo() {
		return "cadastrarTarifaCompesa";
	}

	/*
	 * Obter tarifa da compesa
	 */
	public TarifaCompesa getTarifaCompesa() {
		if (tarifaCompesa == null) {
			tarifaCompesa = new TarifaCompesa();
		}
		return tarifaCompesa;
	}

	/*
	 * Setar tarifa da compesa
	 */
	public void setTarifaCompesa(TarifaCompesa tarifaCompesa) {
		this.tarifaCompesa = tarifaCompesa;
	}
}
