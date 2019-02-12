package br.com.lacqua.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.ejb.ControladorConsumo;
import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Cliente;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.ConsumoGas;
import br.com.lacqua.model.Torre;
import br.com.lacqua.service.ApartamentoService;
import br.com.lacqua.service.ClienteService;
import br.com.lacqua.service.CondominioService;
import br.com.lacqua.service.TorreService;
import br.com.lacqua.util.Mes;

@SuppressWarnings("serial")
@Named("consumoGasBean")
@RequestScoped
public class ConsumoGasBean extends AbstractBean {

	@Inject
	private ApartamentoService apartamentoService;

	@Inject
	private TorreService torreService;

	@Inject
	private CondominioService condominioService;

	@Inject
	private ClienteService clienteService;

	// @Inject
	// private ConsumoGasService consumoGasService;

	@EJB
	private ControladorConsumo controlador;

	private Apartamento apartamento;
	private Torre torre;
	private Condominio condominio;
	private Cliente cliente;
	private ConsumoGas consumoGas;
	private BigDecimal leitura;
	private List<String> listaLeituras;

	private List<Apartamento> apartamentos;
	private List<Torre> torres;
	private List<Condominio> condominios;
	private List<Cliente> clientes;
	private List<ConsumoGas> consumos = new ArrayList<ConsumoGas>();

	public String inserirConsumoMes() {
		return null;
	}

	public String inserirConsumoApartamento(Integer idApartamento) {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (leitura != null) {
				controlador.inserirConsumoMensalApartamento(idApartamento, leitura);
				fc.addMessage("message", new FacesMessage("Sucesso",
						"A leitura do apartamento " + apartamento.getNumero() + " foi gravada!"));
			}

			leitura = null;
		} catch (Exception e) {
			fc.addMessage("message", new FacesMessage("Erro", "A leitura não pôde ser gravada"));
		}
		return null;
	}

	public String salvarConsumosEmTeste() {
		FacesContext fc = FacesContext.getCurrentInstance();
		/*Iterator<Apartamento> it = apartamentos.iterator();
		while (it.hasNext()) {
			Apartamento ap = (Apartamento) it.next();
			ConsumoGas consumo = new ConsumoGas();
			consumo.setTorre(ap.getTorre());
			consumo.setLeitura(leitura);
			consumo.setCondominio(ap.getCondominio());
			consumo.setApartamento(ap);
			consumos.add(consumo);
		}*/

		try {
			if (leitura != null) {
				//controlador.inserirConsumoMensalApartamentos(apartamentos, consumos);
				fc.addMessage("message", new FacesMessage("Sucesso", "As leituras dos apartamentos foram gravadas!"));
			}

			leitura = null;
		} catch (Exception e) {
			fc.addMessage("message", new FacesMessage("Erro", "As leituras não puderam ser gravadas!"));
		}
		return null;
	}
	
	public String salvarConsumos() {
		FacesContext fc = FacesContext.getCurrentInstance();
		
		try {
			if (apartamentos != null && consumos != null) {
				controlador.inserirConsumoMensalApartamentos(apartamentos, consumos, consumoGas);
				fc.addMessage("message", new FacesMessage("Sucesso", "As leituras dos apartamentos foram gravadas!"));
			}

			leitura = null;
		} catch (Exception e) {
			fc.addMessage("message", new FacesMessage("Erro", "As leituras não puderam ser gravadas!"));
		}
		return redirect("consumo");
	}

	public String carregarApartamentos() {
		Integer idCondominio = null;
		Integer idTorre = null;

		if (consumoGas.getCondominio() != null && consumoGas.getTorre() != null) {
			idCondominio = consumoGas.getCondominio().getId();
			idTorre = consumoGas.getTorre().getId();
			apartamentos = apartamentoService.listarApartamentosPorCondominioTorre(idCondominio, idTorre);

		} else if (consumoGas.getCondominio() != null) {
			idCondominio = consumoGas.getCondominio().getId();
			apartamentos = apartamentoService.listarApartamentosPorCondominio(idCondominio);
		}

		return null;
	}

	public List<Apartamento> getApartamentos() {
		if (apartamentos == null) {
			apartamentos = apartamentoService.listarApartamentos();
		}

		return apartamentos;
	}

	public List<Torre> getTorres() {
		if (torres == null) {
			torres = torreService.listarTorres();
		}

		return torres;
	}

	public List<Condominio> getCondominios() {
		if (condominios == null) {
			condominios = condominioService.listarCondominios();
		}

		return condominios;
	}

	public List<Cliente> getClientes() {
		if (clientes == null) {
			clientes = clienteService.listarClientes();
		}

		return clientes;
	}

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public Torre getTorre() {
		return torre;
	}

	public void setTorre(Torre torre) {
		this.torre = torre;
	}

	public Condominio getCondominio() {
		return condominio;
	}

	public void setCondominio(Condominio condominio) {
		this.condominio = condominio;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public ConsumoGas getConsumo() {
		if (consumoGas == null) {
			consumoGas = new ConsumoGas();
		}
		return consumoGas;
	}

	public void setConsumo(ConsumoGas consumo) {
		this.consumoGas = consumo;
	}

	public List<ConsumoGas> getConsumos() {
		return consumos;
	}

	public void setConsumos(List<ConsumoGas> consumos) {
		this.consumos = consumos;
	}

	public BigDecimal getLeitura() {
		return leitura;
	}

	public void setLeitura(BigDecimal leitura) {
		this.leitura = leitura;
	}

	public Mes[] getMeses() {
		return Mes.MESES;
	}

	public List<String> getListaLeituras() {
		return listaLeituras;
	}

	public void setListaLeituras(List<String> listaLeituras) {
		this.listaLeituras = listaLeituras;
	}
}
