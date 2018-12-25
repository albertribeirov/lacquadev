package br.com.lacqua.bean;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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
import br.com.lacqua.service.ConsumoGasService;
import br.com.lacqua.service.TorreService;

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

	@Inject
	private ConsumoGasService consumoService;

	@EJB
	private ControladorConsumo controlador;

	private Apartamento apartamento;
	private Torre torre;
	private Condominio condominio;
	private Cliente cliente;
	private ConsumoGas consumoGas;
	private BigDecimal leitura;

	private List<Apartamento> apartamentos;
	private List<Torre> torres;
	private List<Condominio> condominios;
	private List<Cliente> clientes;
	private List<ConsumoGas> consumos;

	public String inserirConsumoMes() {
		return null;
	}
	
	public String inserirConsumoApartamento(Integer idApartamento) {
		controlador.inserirConsumoMensalApartamento(idApartamento, consumoGas);
		return null;
	}

	public String carregarApartamentos() {
		Integer idCondominio = consumoGas.getCondominio().getId();
		Integer idTorre = consumoGas.getTorre().getId();
		apartamentos = apartamentoService.listarApartamentosPorCondominioTorre(idCondominio, idTorre);

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
}
