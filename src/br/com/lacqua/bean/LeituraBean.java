package br.com.lacqua.bean;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.TreeMap;
import java.util.Date;

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
import br.com.lacqua.rn.RNCargaConsumoDocumentoTexto;
import br.com.lacqua.service.ApartamentoService;
import br.com.lacqua.service.ClienteService;
import br.com.lacqua.service.CondominioService;
import br.com.lacqua.service.ConsumoService;
import br.com.lacqua.service.LeituraService;
import br.com.lacqua.service.TorreService;
import br.com.lacqua.util.BibliotecaFuncoes;
import br.com.lacqua.util.Constantes;
import br.com.lacqua.util.Mes;

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

	/**
	 * Gera relatório geral da torre/condomínio selecionados e pode gerar tanto o PDF quanto o TXT.
	 */
	public String gerarDemonstrativoTorre(String tipo) throws ValidationException {
		// TODO Criar método que gera relatorio da torre/condominio.
		boolean txt;
		boolean pdf;
		
		txt = (tipo.equals(Constantes.TXT_1)) ? Boolean.TRUE : Boolean.FALSE;
		pdf = (tipo.equals(Constantes.PDF_2)) ? Boolean.TRUE : Boolean.FALSE;
		
		Torre tower = null;
		Condominio condo;
		Leitura leituraTemp = new Leitura();
		FacesContext facesContext = FacesContext.getCurrentInstance();
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

		if (txt || pdf) {
			leituraMesSelecionado = leituraService.listarLeiturasPorCondominioTorreMes(leitura, ano, mes);
			leituraMesAnterior1 = consultarLeituraMesesAnteriores(ano, mes, condo, tower, 1);			
		}
		
		if (pdf) {
			leituraMesProximo = leituraService.listarLeiturasPorCondominioTorreMes(leitura, periodoProximo.get(1), periodoProximo.get(0));
			leituraMesAnterior2 = consultarLeituraMesesAnteriores(ano, mes, condo, tower, 2);
			leituraMesAnterior3 = consultarLeituraMesesAnteriores(ano, mes, condo, tower, 3);			
		}

		try {
			if (txt) {
				controlador.gerarDemonstrativoTorreTXT(leitura, leituraMesSelecionado, leituraMesAnterior1);
				facesContext.addMessage(MESSAGE, new FacesMessage(SUCESSO, "Relatório geral exportado em TXT!"));				
			}
			
			if (pdf) {
				controlador.gerarDemonstrativoTorrePDF(leitura, leituraMesProximo, leituraMesSelecionado, leituraMesAnterior1, leituraMesAnterior2, leituraMesAnterior3);
				facesContext.addMessage(MESSAGE, new FacesMessage(SUCESSO, "Relatório geral exportado em PDF!"));
			}

		} catch (Exception e) {
			facesContext.addMessage(MESSAGE, new FacesMessage(ERRO, e.getMessage()));
		}
		return null;
	}

	public String gerarDemonstrativosApartamentos() throws ValidationException {
		Torre tower = null;
		Condominio condo;
		Leitura leituraTemp = new Leitura();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		List<Leitura> leituraMesProximo;
		List<Leitura> leituraMesSelecionado;
		List<Leitura> leituraMesAnterior3;
		List<Leitura> leituraMesAnterior2;
		List<Leitura> leituraMesAnterior1;
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
			controlador.gerarDemonstrativosApartamentos(leitura, leituraMesProximo, leituraMesSelecionado, leituraMesAnterior1, leituraMesAnterior2, leituraMesAnterior3);
			facesContext.addMessage(MESSAGE, new FacesMessage(SUCESSO, "Demonstrativos exportados!"));
		} catch (Exception e) {
			facesContext.addMessage(MESSAGE, new FacesMessage(ERRO, e.getMessage()));
		}

		return null;
	}

	private List<Leitura> consultarLeituraMesesAnteriores(Integer pAno, Integer pMes, Condominio condominio, Torre torre, Integer qtdMeses) throws ValidationException {
		List<Leitura> listaConsumo;
		Leitura leituraAtual = new Leitura();

		Integer mes = pMes;
		Integer ano = pAno;

		int temp = mes - qtdMeses;
		if (temp < 1) {
			mes = 12;
			mes = mes - qtdMeses;
			ano = ano - 1;
		} else {
			mes = mes - 1;
		}

		leituraAtual.setAno(ano);
		leituraAtual.setMesReferenciaLeitura(mes);
		leituraAtual.setCondominio(condominio);
		if (Objects.nonNull(torre)) {
			leituraAtual.setTorre(torre);
		}

		listaConsumo = leituraService.listarLeiturasPorCondominioTorreMes(leituraAtual, ano, mes);

		return listaConsumo;
	}

	public String gravarConsumosPorCondominioTorreMes() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		List<Integer> listMesAno;
		List<Leitura> leituraMesAnterior;
		List<Leitura> leituraMesSelecionado;
		List<Consumo> consumosMesSelecionado;
		Date dataConsumo = leitura.getDataRealizacaoLeitura();
		Integer ano = BibliotecaFuncoes.getAnoFromDate(dataConsumo);
		Integer mes = BibliotecaFuncoes.getMesFromDate(dataConsumo);
		Integer mesAnterior;
		Integer anoAnterior;
		leitura.setMesReferenciaLeitura(mes);
		leitura.setAno(ano);
		Condominio cond = leitura.getCondominio();
		Torre tower = null;

		if (leitura.getTorre() != null) {
			tower = leitura.getTorre();
		}

		try {
			Consumo cons = new Consumo(cond, tower, ano, mes);
			consumosMesSelecionado = consumoService.listarConsumosPorCondominioTorreMes(cons);

			if (consumosMesSelecionado == null || consumosMesSelecionado.isEmpty()) {

				leituraMesSelecionado = leituraService.listarLeiturasPorCondominioTorreMes(leitura, ano, mes);

				listMesAno = BibliotecaFuncoes.getPeriodoAnterior(ano, mes);
				mesAnterior = listMesAno.get(0);
				anoAnterior = listMesAno.get(1);

				Leitura leituraClone = new Leitura(cond, tower, ano, mes);
				leituraClone.setDataRealizacaoLeitura(dataConsumo);

				leituraMesAnterior = leituraService.listarLeiturasPorCondominioTorreMes(leituraClone, anoAnterior, mesAnterior);

				if (!leituraMesSelecionado.isEmpty() && !leituraMesAnterior.isEmpty()) {
					controlador.gravarConsumosPorCondominioTorreMes(leitura, leituraMesSelecionado, leituraMesAnterior);
					facesContext.addMessage(MESSAGE, new FacesMessage(SUCESSO, "Consumo calculado!"));
				} else {
					facesContext.addMessage(MESSAGE, new FacesMessage(ERRO, "Não há consumo para " + mes + "/" + ano + " ou para " + mesAnterior + "/" + anoAnterior + "."));
				}

			} else {
				facesContext.addMessage(MESSAGE, new FacesMessage(ERRO, "Já existe consumo registrado para este mês."));
			}
		} catch (Exception e) {
			facesContext.addMessage(MESSAGE, new FacesMessage(ERRO, "O consumo mensal não pôde ser gerado."));
			handleException(e);
		}
		return null;
	}

	/*
	 * public String inserirConsumoApartamento(Integer idApartamento) {
	 * FacesContext facesContext = FacesContext.getCurrentInstance();
	 * try {
	 * if (leituraValor != null) {
	 * //apartamentoService.carregarApartamentoPorNumeroTorreCondominio
	 * controlador.inserirConsumoMensalApartamento(idApartamento, leituraValor, condominio, torre);
	 * facesContext.addMessage(MESSAGE, new FacesMessage(SUCESSO, "A leitura do apartamento " +
	 * apartamento.getNumero() + " foi gravada!"));
	 * }
	 * leituraValor = null;
	 * } catch (Exception e) {
	 * facesContext.addMessage(MESSAGE, new FacesMessage(ERRO, "A leitura não pôde ser gravada"));
	 * }
	 * return null;
	 * }
	 */

	public String cargaLeituraFromTxt() {
		FacesContext facesContext = FacesContext.getCurrentInstance();

		try {
			List<String> texto = BibliotecaFuncoes.lerArquivo("D:/arquivo.txt");

			for (String linha : texto) {
				Leitura leituraGas = new Leitura();
				BigDecimal leituraValor;
				String linhaConcatenada;
				String numeroApartamento;
				List<String> line;
				List<Apartamento> listaApartamentos;

				linhaConcatenada = linha;
				line = BibliotecaFuncoes.split(linhaConcatenada, ";");
				numeroApartamento = line.get(0);
				leituraValor = new BigDecimal(line.get(1));
				leituraValor = BibliotecaFuncoes.escalarConsumo(leituraValor);

				Integer torreId = null;
				if (leitura.getTorre() != null) {
					torreId = leitura.getTorre().getId();
				}

				listaApartamentos = apartamentoService.listarApartamentosPorCondominioTorre(leitura.getCondominio().getId(), torreId);
				Integer ano = BibliotecaFuncoes.getAnoFromDate(leitura.getDataRealizacaoLeitura());
				Integer mes = BibliotecaFuncoes.getMesFromDate(leitura.getDataRealizacaoLeitura());

				for (Apartamento apartamento : listaApartamentos) {
					if (numeroApartamento.equals(apartamento.getNumero().toString())) {
						RNCargaConsumoDocumentoTexto.atualizaConsumoGas(leituraGas, leituraValor, apartamento);
						leituraGas.setDataRealizacaoLeitura(leitura.getDataRealizacaoLeitura());
						leituraGas.setMesReferenciaLeitura(mes);
						leituraGas.setAno(ano);

						if (leituraService.carregarLeituraDoApartamentoNoMesAno(mes, ano, apartamento)) {
							controlador.inserirLeituraApartamento(leituraGas);
						}
					}
				}
			}

			facesContext.addMessage(MESSAGE, new FacesMessage(SUCESSO, "Carga de dados realizada com sucesso!"));

		} catch (Exception e) {
			facesContext.addMessage(MESSAGE, new FacesMessage(ERRO, e.getMessage()));
			handleException(e);
		}

		return null;
	}

	public String salvarConsumosEmTeste() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			if (leituraValor != null) {
				// controlador.inserirConsumoMensalApartamentos(apartamentos, consumos);
				facesContext.addMessage(MESSAGE, new FacesMessage(SUCESSO, "As leituras dos apartamentos foram gravadas!"));
			}

			leituraValor = null;
		} catch (Exception e) {
			facesContext.addMessage(MESSAGE, new FacesMessage(ERRO, "As leituras não puderam ser gravadas!"));
		}
		return null;
	}

	public String salvarConsumos() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			if (apartamentos != null && consumos != null) {
				//controlador.inserirConsumoMensalApartamentos(apartamentos, consumos, leitura);
				facesContext.addMessage(MESSAGE, new FacesMessage(SUCESSO, "As leituras dos apartamentos foram gravadas!"));
			}
			leituraValor = null;
		} catch (Exception e) {
			facesContext.addMessage(MESSAGE, new FacesMessage(ERRO, "As leituras não puderam ser gravadas!"));
		}
		return redirect("consumo");
	}

	public String salvarLeitura() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			leituraService.inserir(leitura);
			facesContext.addMessage(MESSAGE, new FacesMessage(SUCESSO, "A leitura do apartamento foi gravada!"));
			leitura = null;
		} catch (Exception e) {
			facesContext.addMessage(MESSAGE, new FacesMessage(ERRO, "A leitura do apartamento não foi gravada!"));
		}
		return null;
	}

	/**
	 * 
	 */
	public List<Apartamento> carregarApartamentos() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Integer idCondominio;
		Integer idTorre;
		Date dataRealizLeitura = leitura.getDataRealizacaoLeitura();
		Integer ano = BibliotecaFuncoes.getAnoFromDate(dataRealizLeitura);
		Integer mes = BibliotecaFuncoes.getMesFromDate(dataRealizLeitura);

		try {
			if (leitura.getCondominio() != null && leitura.getTorre() != null) {
				idCondominio = leitura.getCondominio().getId();
				idTorre = leitura.getTorre().getId();
				apartamentos = apartamentoService.listarApartamentosPorCondominioTorre(idCondominio, idTorre);

			} else if (leitura.getCondominio() != null) {
				idCondominio = leitura.getCondominio().getId();
				apartamentos = apartamentoService.listarApartamentosPorCondominio(idCondominio);
			}

			List<Integer> periodo = BibliotecaFuncoes.getPeriodoAnterior(ano, mes);
			mes = periodo.get(0);
			ano = periodo.get(1);
			leituras = leituraService.listarLeiturasPorCondominioTorreMes(leitura, ano, mes);

			TreeMap<Integer, BigDecimal> mapLeitura = new TreeMap<>();
			for (Leitura leitura : leituras) {
				Integer numeroApartamento = leitura.getApartamento().getNumero();
				mapLeitura.put(numeroApartamento, leitura.getLeitura());
			}

			for (Apartamento apartamento : apartamentos) {
				Integer numeroAp = apartamento.getNumero();
				apartamento.setLeituraAnterior(mapLeitura.get(numeroAp));
			}

		} catch (Exception e) {
			handleException(e);
			facesContext.addMessage(MESSAGE, new FacesMessage(ERRO, e.getMessage()));
		}
		return apartamentos;
	}

	/**
	 * Lista leituras de um período de acordo com condomínio, torre, ano e mês.
	 */
	public List<Leitura> listarLeituras() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			Integer ano = BibliotecaFuncoes.getAnoFromDate(leitura.getDataRealizacaoLeitura());
			Integer mes = BibliotecaFuncoes.getMesFromDate(leitura.getDataRealizacaoLeitura());
			leituras = leituraService.listarLeiturasPorCondominioTorreMes(leitura, ano, mes);
			return leituras;
		} catch (Exception e) {
			facesContext.addMessage(MESSAGE, new FacesMessage(ERRO, e.getMessage()));
			handleException(e);
		}

		return null;
	}

	public String carregar(Integer id) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			leitura = leituraService.carregar(id);
			facesContext.addMessage(MESSAGE, new FacesMessage(SUCESSO, "Leitura carregada!"));
		} catch (Exception e) {
			handleException(e);
			facesContext.addMessage(MESSAGE, new FacesMessage(ERRO, "Leitura não carregada!"));
			handleException(e);
		}
		return null;
	}

	public String alterar(Integer id) {

		return null;
	}

	public String excluir(Integer id) {
		leituraService.excluir(id);
		leituras = null;

		return redirect(Constantes.LISTAR_LEITURAS);
	}

	/*
	 * Getters/Setters
	 */

	public List<Apartamento> getApartamentos() {
		if (apartamentos == null) {
			apartamentos = apartamentoService.listarApartamentos();
		}

		if (apartamentos.size() > 500) {
			apartamentos = null;
		}

		return apartamentos;
	}

	public void setApartamentos(List<Apartamento> apartamentos) {
		this.apartamentos = apartamentos;
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

	public List<Leitura> getLeituras() {
		return leituras;
	}

	public void setLeituras(List<Leitura> leituras) {
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
