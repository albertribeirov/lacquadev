package br.com.lacqua.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import br.com.lacqua.model.Leitura;
import br.com.lacqua.model.Torre;
import br.com.lacqua.service.ApartamentoService;
import br.com.lacqua.service.ClienteService;
import br.com.lacqua.service.CondominioService;
import br.com.lacqua.service.LeituraService;
import br.com.lacqua.service.TorreService;
import br.com.lacqua.util.Mes;

@SuppressWarnings("serial")
@Named("leituraBean")
@RequestScoped
public class LeituraBean extends AbstractBean {

	@Inject
	private ApartamentoService apartamentoService;

	@Inject
	private TorreService torreService;

	@Inject
	private CondominioService condominioService;

	@Inject
	private ClienteService clienteService;

	@Inject
	private LeituraService leituraService;

	@EJB
	private ControladorConsumo controlador;

	private Apartamento apartamento;
	private Torre torre;
	private Condominio condominio;
	private Cliente cliente;
	private Leitura leitura;
	private BigDecimal leituraValor;
	private List<String> listaLeituras;

	private List<Apartamento> apartamentos;
	private List<Torre> torres;
	private List<Condominio> condominios;
	private List<Cliente> clientes;
	private List<Leitura> consumos = new ArrayList<Leitura>();

	public String inserirConsumoMes() {
		return null;
	}

	public String listarConsumosPorCondominioTorreMes() {
		FacesContext fc = FacesContext.getCurrentInstance();
		List<Leitura> consumoProximoMes = new ArrayList<>();
		List<Leitura> consumoMesSelecionado = new ArrayList<>();
		Date dataConsumo = leitura.getDataRealizacaoLeitura();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dataConsumo);
		Integer ano = cal.get(Calendar.YEAR);
		Integer mes = cal.get(Calendar.MONTH) + 1;
		leitura.setMesReferenciaLeitura(mes);
		leitura.setAno(ano);

		try {

			consumoMesSelecionado = leituraService.listarConsumosPorCondominioTorreMes(leitura);

			if (mes == 12) {
				mes = 1;
				ano = ano + 1;
			} else {
				mes = mes + 1;
			}

			leitura.setAno(ano);
			leitura.setMesReferenciaLeitura(mes);

			consumoProximoMes = leituraService.listarConsumosPorCondominioTorreMes(leitura);

			controlador.listarConsumosPorCondominioTorreMes(leitura, consumoMesSelecionado, consumoProximoMes);
			fc.addMessage("message", new FacesMessage("Sucesso", "Consumo calculado!"));
		} catch (Exception e) {
			fc.addMessage("message", new FacesMessage("Erro", "O consumo mensal não pôde ser gerado."));
			e.printStackTrace();
		}
		return null;
	}

	public String gerarConsumosCondominio() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (condominio != null) {
				controlador.gerarContaCondominio(leitura, condominio, torre);

				// controlador.inserirConsumoMensalApartamento(idApartamento, leituraValor);
				// fc.addMessage("message", new FacesMessage("Sucesso", "A leituraValor do apartamento " +
				// apartamento.getNumero() + " foi gravada!"));
			}

			// leituraValor = null;
		} catch (Exception e) {
			fc.addMessage("message", new FacesMessage("Erro", "O consumo não pôde ser gerado."));
		}
		return null;
	}

	public String inserirConsumoApartamento(Integer idApartamento) {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (leituraValor != null) {
				controlador.inserirConsumoMensalApartamento(idApartamento, leituraValor);
				fc.addMessage("message", new FacesMessage("Sucesso", "A leituraValor do apartamento " + apartamento.getNumero() + " foi gravada!"));
			}

			leituraValor = null;
		} catch (Exception e) {
			fc.addMessage("message", new FacesMessage("Erro", "A leituraValor não pôde ser gravada"));
		}
		return null;
	}

	public String cargaConsumoDocumentoTexto() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			controlador.cargaConsumoDocumentoTexto(leitura);
			fc.addMessage("message", new FacesMessage("Sucesso", "Carga de dados realizada com sucesso!"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String salvarConsumosEmTeste() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (leituraValor != null) {
				// controlador.inserirConsumoMensalApartamentos(apartamentos, consumos);
				fc.addMessage("message", new FacesMessage("Sucesso", "As leituraValors dos apartamentos foram gravadas!"));
			}

			leituraValor = null;
		} catch (Exception e) {
			fc.addMessage("message", new FacesMessage("Erro", "As leituraValors não puderam ser gravadas!"));
		}
		return null;
	}

	public String salvarConsumos() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (apartamentos != null && consumos != null) {
				controlador.inserirConsumoMensalApartamentos(apartamentos, consumos, leitura);
				fc.addMessage("message", new FacesMessage("Sucesso", "As leituraValors dos apartamentos foram gravadas!"));
			}

			leituraValor = null;
		} catch (Exception e) {
			fc.addMessage("message", new FacesMessage("Erro", "As leituraValors não puderam ser gravadas!"));
		}
		return redirect("consumo");
	}

	public String carregarApartamentos() {
		Integer idCondominio = null;
		Integer idTorre = null;

		if (leitura.getCondominio() != null && leitura.getTorre() != null) {
			idCondominio = leitura.getCondominio().getId();
			idTorre = leitura.getTorre().getId();
			apartamentos = apartamentoService.listarApartamentosPorCondominioTorre(idCondominio, idTorre);

		} else if (leitura.getCondominio() != null) {
			idCondominio = leitura.getCondominio().getId();
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

	public Leitura getConsumo() {
		if (leitura == null) {
			leitura = new Leitura();
		}
		return leitura;
	}

	public void setConsumo(Leitura consumo) {
		this.leitura = consumo;
	}

	public List<Leitura> getConsumos() {
		return consumos;
	}

	public void setConsumos(List<Leitura> consumos) {
		this.consumos = consumos;
	}

	public BigDecimal getLeitura() {
		return leituraValor;
	}

	public void setLeitura(BigDecimal leituraValor) {
		this.leituraValor = leituraValor;
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
