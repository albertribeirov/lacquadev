package br.com.lacqua.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.ejb.ControladorConsumo;
import br.com.lacqua.exception.ValidationException;
import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Cliente;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Consumo;
import br.com.lacqua.model.Leitura;
import br.com.lacqua.model.Torre;
import br.com.lacqua.service.ApartamentoService;
import br.com.lacqua.service.ClienteService;
import br.com.lacqua.service.CondominioService;
import br.com.lacqua.service.ConsumoService;
import br.com.lacqua.service.LeituraService;
import br.com.lacqua.service.TorreService;
import br.com.lacqua.util.BibliotecaFuncoes;
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

	@Inject
	private ConsumoService consumoService;

	@EJB
	private ControladorConsumo controlador;

	private Apartamento apartamento;
	private Torre torre;
	private Condominio condominio;
	private Cliente cliente;
	private Leitura leitura;
	private BigDecimal leituraValor;
	private Consumo consumo;
	private Date mesAnoLeitura;

	private List<Consumo> consumos;
	private List<Leitura> leituras;
	private List<Apartamento> apartamentos;
	private List<Torre> torres;
	private List<Condominio> condominios;
	private List<Cliente> clientes;

	public String inserirConsumoMes() {
		return null;
	}

	public String gerarDemonstrativosCondominioTorreMes() throws ValidationException {
		Torre tower = null;
		Condominio condo = null;
		Leitura leituraTemp = new Leitura();
		FacesContext fc = FacesContext.getCurrentInstance();
		List<Leitura> leituraMesProximo = new ArrayList<>();
		List<Leitura> leituraMesSelecionado = new ArrayList<>();
		List<Leitura> leituraMesAnterior3 = new ArrayList<>();
		List<Leitura> leituraMesAnterior2 = new ArrayList<>();
		List<Leitura> leituraMesAnterior1 = new ArrayList<>();
		Date dataConsumo = leitura.getDataRealizacaoLeitura();
		condo = leitura.getCondominio();

		if (leitura.getTorre() != null) {
			tower = leitura.getTorre();
			leituraTemp.setTorre(tower);
		}
		Integer ano = BibliotecaFuncoes.getAnoFromDate(dataConsumo);
		Integer mes = BibliotecaFuncoes.getMesFromDate(dataConsumo);
		leitura.setMesReferenciaLeitura(mes);
		leitura.setAno(ano);
		leituraTemp.setAno(ano);
		leituraTemp.setMesReferenciaLeitura(mes);
		leituraTemp.setCondominio(condo);
		
		List<Integer> periodoProximo = BibliotecaFuncoes.getPeriodoProximo(ano, mes);

		leituraMesProximo = leituraService.listarLeiturasPorCondominioTorreMes(leitura, periodoProximo.get(1), periodoProximo.get(0));
		leituraMesSelecionado = leituraService.listarLeiturasPorCondominioTorreMes(leitura, ano, mes);
		leituraMesAnterior1 = consultarLeituraMesesAnteriores(ano, mes, condo, tower, 1);
		leituraMesAnterior2 = consultarLeituraMesesAnteriores(ano, mes, condo, tower, 2);
		leituraMesAnterior3 = consultarLeituraMesesAnteriores(ano, mes, condo, tower, 3);

		try {
			controlador.gerarDemonstrativosCondominioTorre(leitura, leituraMesProximo, leituraMesSelecionado, leituraMesAnterior1, leituraMesAnterior2, leituraMesAnterior3);
			fc.addMessage(MESSAGE, new FacesMessage(SUCESSO, "Demonstrativos exportados!"));
		} catch (Exception e) {
			fc.addMessage(MESSAGE, new FacesMessage(ERRO, e.getMessage()));
			e.printStackTrace();
		}

		return null;
	}

	private List<Leitura> consultarLeituraMesesAnteriores(Integer pAno, Integer pMes, Condominio pCond, Torre pTorre, Integer qtdMeses) throws ValidationException {
		List<Leitura> listaConsumo = new ArrayList<Leitura>();
		Leitura leitura = new Leitura();

		Integer mes = pMes;
		Integer ano = pAno;

		Integer temp = mes - qtdMeses;
		if (temp < 1) {
			mes = 12;
			mes = mes - qtdMeses;
			ano = ano - 1;
		}

		leitura.setAno(mes);
		leitura.setMesReferenciaLeitura(ano);
		leitura.setCondominio(pCond);
		if (pTorre != null) {
			leitura.setTorre(pTorre);
		}

		listaConsumo = leituraService.listarLeiturasPorCondominioTorreMes(leitura, ano, mes);

		return listaConsumo;
	}

	public String gravarConsumosPorCondominioTorreMes() {
		FacesContext fc = FacesContext.getCurrentInstance();
		List<Integer> listMesAno = new ArrayList<Integer>();
		List<Leitura> leituraMesAnterior = new ArrayList<Leitura>();
		List<Leitura> leituraMesSelecionado = new ArrayList<Leitura>();
		List<Consumo> consumosMesSelecionado = new ArrayList<Consumo>();
		Date dataConsumo = leitura.getDataRealizacaoLeitura();
		Integer ano = BibliotecaFuncoes.getAnoFromDate(dataConsumo);
		Integer mes = BibliotecaFuncoes.getMesFromDate(dataConsumo);
		Integer mesAnterior = null;
		Integer anoAnterior = null;
		leitura.setMesReferenciaLeitura(mes);
		leitura.setAno(ano);
		Condominio cond = leitura.getCondominio();
		Torre torre = null;

		if (leitura.getTorre() != null) {
			torre = leitura.getTorre();
		}

		try {
			Consumo cons = new Consumo();
			cons.setCondominio(cond);
			cons.setTorre(torre);
			cons.setAno(ano);
			cons.setMes(mes);
			consumosMesSelecionado = consumoService.listarConsumosPorCondominioTorreMes(cons);

			if (consumosMesSelecionado == null || consumosMesSelecionado.isEmpty()) {

				leituraMesSelecionado = leituraService.listarLeiturasPorCondominioTorreMes(leitura, ano, mes);

				listMesAno = BibliotecaFuncoes.getPeriodoAnterior(ano, mes);
				mesAnterior = listMesAno.get(0);
				anoAnterior = listMesAno.get(1);

				Leitura leituraClone = new Leitura(cond, torre, ano, mes);
				leituraClone.setDataRealizacaoLeitura(dataConsumo);

				leituraMesAnterior = leituraService.listarLeiturasPorCondominioTorreMes(leituraClone, anoAnterior, mesAnterior);

				if (!leituraMesSelecionado.isEmpty() && !leituraMesAnterior.isEmpty()) {
					controlador.gravarConsumosPorCondominioTorreMes(leitura, leituraMesSelecionado, leituraMesAnterior);
					fc.addMessage(MESSAGE, new FacesMessage(SUCESSO, "Consumo calculado!"));
				} else {
					fc.addMessage(MESSAGE, new FacesMessage(ERRO, "N�o h� consumo para " + mes + "/" + ano + " ou para " + mesAnterior + "/" + anoAnterior + "."));
				}

			} else {
				fc.addMessage(MESSAGE, new FacesMessage(ERRO, "J� existe consumo registrado para este m�s."));
			}
		} catch (Exception e) {
			fc.addMessage(MESSAGE, new FacesMessage(ERRO, "O consumo mensal n�o p�de ser gerado."));
			e.printStackTrace();
		}
		return null;
	}

	public String inserirConsumoApartamento(Integer idApartamento) {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (leituraValor != null) {
				controlador.inserirConsumoMensalApartamento(idApartamento, leituraValor);
				fc.addMessage(MESSAGE, new FacesMessage(SUCESSO, "A leitura do apartamento " + apartamento.getNumero() + " foi gravada!"));
			}

			leituraValor = null;
		} catch (Exception e) {
			fc.addMessage(MESSAGE, new FacesMessage(ERRO, "A leitura n�o p�de ser gravada"));
		}
		return null;
	}

	public String cargaLeituraFromTxt() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {

			Integer ano = BibliotecaFuncoes.getAnoFromDate(getMesAnoLeitura());
			Integer mes = BibliotecaFuncoes.getMesFromDate(getMesAnoLeitura());
			leitura.setAno(ano);
			leitura.setMesReferenciaLeitura(mes);
			controlador.cargaConsumoDocumentoTexto(leitura);
			fc.addMessage(MESSAGE, new FacesMessage(SUCESSO, "Carga de dados realizada com sucesso!"));

		} catch (Exception e) {
			e.printStackTrace();
			fc.addMessage(MESSAGE, new FacesMessage(ERRO, e.getMessage()));
		}

		return null;
	}

	public String salvarConsumosEmTeste() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (leituraValor != null) {
				// controlador.inserirConsumoMensalApartamentos(apartamentos, consumos);
				fc.addMessage(MESSAGE, new FacesMessage(SUCESSO, "As leituras dos apartamentos foram gravadas!"));
			}

			leituraValor = null;
		} catch (Exception e) {
			fc.addMessage(MESSAGE, new FacesMessage(ERRO, "As leituras n�o puderam ser gravadas!"));
		}
		return null;
	}

	public String salvarConsumos() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (apartamentos != null && consumos != null) {
				//controlador.inserirConsumoMensalApartamentos(apartamentos, consumos, leitura);
				fc.addMessage(MESSAGE, new FacesMessage(SUCESSO, "As leituras dos apartamentos foram gravadas!"));
			}

			leituraValor = null;
		} catch (Exception e) {
			fc.addMessage(MESSAGE, new FacesMessage(ERRO, "As leituras n�o puderam ser gravadas!"));
		}
		return redirect("consumo");
	}

	public String salvarLeitura() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			leituraService.inserir(leitura);
			fc.addMessage(MESSAGE, new FacesMessage(SUCESSO, "A leitura do apartamento foi gravada!"));
			leitura = null;
		} catch (Exception e) {
			fc.addMessage(MESSAGE, new FacesMessage(ERRO, "A leitura do apartamento n�o foi gravada!"));
		}
		return null;
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

	/*
	 * Lista leituras de um per�odo de acordo com condom�nio, torre, ano e m�s.
	 */
	public List<Leitura> listarLeituras() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			Integer ano = BibliotecaFuncoes.getAnoFromDate(leitura.getDataRealizacaoLeitura());
			Integer mes = BibliotecaFuncoes.getMesFromDate(leitura.getDataRealizacaoLeitura());
			leituras = leituraService.listarLeiturasPorCondominioTorreMes(leitura, ano, mes);
			return leituras;
		} catch (Exception e) {
			fc.addMessage(MESSAGE, new FacesMessage(ERRO, e.getMessage()));
			handleException(e);
		}

		return null;
	}

	public String carregar(Integer id) {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			leitura = leituraService.carregar(id);
			fc.addMessage(MESSAGE, new FacesMessage(SUCESSO, "Leitura carregada!"));
		} catch (Exception e) {
			handleException(e);
			fc.addMessage(MESSAGE, new FacesMessage(ERRO, "Leitura n�o carregada!"));
			handleException(e);
		}
		return null;
	}

	public String alterar(Integer id) {

		return null;
	}

	public String excluir(Integer id) {

		return null;
	}

	/*
	 * Getters/Setters
	 */

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

	public Leitura getLeitura() {
		if (leitura == null) {
			leitura = new Leitura();
		}
		return leitura;
	}

	public void setConsumo(Leitura consumo) {
		this.leitura = consumo;
	}

	public List<Consumo> getConsumos() {
		return consumos;
	}

	public void setConsumos(List<Consumo> consumos) {
		this.consumos = consumos;
	}

	public BigDecimal getLeituraValor() {
		return leituraValor;
	}

	public void setLeituraValor(BigDecimal leituraValor) {
		this.leituraValor = leituraValor;
	}

	public Mes[] getMeses() {
		return Mes.MESES;
	}

	public List<Leitura> getListaLeituras() {
		return leituras;
	}

	public void setListaLeituras(List<Leitura> leituras) {
		this.leituras = leituras;
	}

	public Consumo getConsumo() {
		if (consumo == null) {
			consumo = new Consumo();
		}
		return consumo;
	}

	public void setConsumo(Consumo consumo) {
		this.consumo = consumo;
	}

	public Date getMesAnoLeitura() {
		return mesAnoLeitura;
	}

	public void setMesAnoLeitura(Date mesAnoLeitura) {
		this.mesAnoLeitura = mesAnoLeitura;
	}
}
