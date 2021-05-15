package br.com.lacqua.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Consumo;
import br.com.lacqua.service.ConsumoService;

@SuppressWarnings("serial")
@Named("consumoBean")
@RequestScoped
public class ConsumoBean extends AbstractBean {

	@Inject
	private ConsumoService consumoService;

	private Consumo consumo;

	private List<Consumo> consumos;

	/*
	 * Listar consumos
	 */
	public List<Consumo> getConsumos() {
		if (consumos == null) {
			consumos = consumoService.listarConsumosPorCondominioTorreMes(consumo);
		}
		return consumos;
	}
	
	/*
	 * Altera um consumo
	 */
	public String alterar(Integer id) {
		consumo = consumoService.carregar(id);
		return null;

	}
	
	/*
	 * Cancela a alteração
	 */
	public String cancelar() {
		consumo = null;
		consumos = consumoService.listarConsumos();
		return null;
	}

	/*
	 * Exclui um consumo
	 */
	public String excluir(Integer id) {
		try {
			consumoService.excluir(id);			
		} catch (Exception e) {
			handleException(e);
		}
		
		this.consumo = null;
		return redirect("consumo");
	}

	/*
	 * Salva um consumo
	 */
	public String salvar() {
		try {
			if (consumo.getId() == null) {
				consumoService.inserir(consumo);
			} else {
				consumoService.alterar(consumo);
			}

			consumo = null;
			return redirect("consumo");

		} catch (Exception e) {
			addMessageToRequest(e.getMessage());
			return null;
		}
	}

	/*
	 * Obter consumo
	 */
	public Consumo getConsumo() {
		if (consumo == null) {
			consumo = new Consumo();
		}
		return consumo;
	}

	/*
	 * Setar consumo
	 */
	public void setConsumo(Consumo consumo) {
		this.consumo = consumo;
	}
}