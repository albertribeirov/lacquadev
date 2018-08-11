package br.com.lacqua.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import br.com.lacqua.model.Condominio;
import br.com.lacqua.util.Constantes;

@Named("condominioBean")
@SessionScoped
public class CondominioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction ut;

	// Atributos da classe
	private Integer id;
	private String nome;
	private String cnpj;
	private String inscricaoEstadual;
	private String inscricaoMunicipal;
	private String telefone1;
	private String telefone2;
	private String taxaLeitura;
	private String ruaComNumero;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
	private Boolean ativo;
	private Date inicioContrato;
	private Date fimContrato;
	private Boolean temPoco;
	private String obs;

	private List<Condominio> condominiosList = new ArrayList<Condominio>();
	private ListDataModel<Condominio> condominios = new ListDataModel<Condominio>(condominiosList);
	private Condominio editCondominio;

	// Getters e setters
	// -------------------------------------------------------------------------------------------------

	public Condominio getCondominio() {
		return editCondominio;
	}

	public void setCondominio(Condominio condominio) {
		this.editCondominio = condominio;
	}

	// Métodos de operações da classe
	public String novo() {
		return Constantes.CONDOMINIO_INCLUIR;
	}

	// TODO Falta implementação do método salvar (validações)
	public String salvar() {
		return Constantes.CONDOMINIO_SUCESSO;
	}

	public String atualizar(Condominio condominio) throws Exception {
		editCondominio = null;

		try {
			ut.begin();

			if (condominio.getId() == null) {
				em.persist(condominio);

			} else {
				em.merge(condominio);

			}

			ut.commit();

		} catch (Exception e) {
			throw e;
		}

		lerCondominios();
		return null;
	}

	public String inserir() {
		Condominio condominio = new Condominio();
		editCondominio = condominio;
		condominiosList.add(condominio);
		return null;
	}

	public String editar(Condominio condominio) {
		editCondominio = condominio;
		return null;
	}

	public String cancelar(Condominio condominio) {
		if (condominio.getId() == null) {
			condominiosList.remove(condominio);
		}
		editCondominio = null;
		return null;
	}

	public boolean isEdit(Condominio condominio) {
		return condominio == editCondominio;
	}

	public String excluir(Condominio condominio) throws Exception {
		try {

			ut.begin();
			Condominio condominioDB = em.find(Condominio.class, condominio.getId());
			em.remove(condominioDB);

			ut.commit();
		} catch (Exception e) {
			if (ut.getStatus() == Status.STATUS_ACTIVE) {
				ut.rollback();
			}
			throw e;
		}

		lerCondominios();
		return null;
	}

	@SuppressWarnings("unchecked")
	protected void lerCondominios() throws Exception {
		Query q = em.createQuery("SELECT c FROM Condominio c ORDER BY c.id");
		List<Condominio> result = q.getResultList();
		this.condominiosList.clear();
		this.condominiosList.addAll(result);
	}

	public ListDataModel<Condominio> getCondominios() {
		return condominios;
	}

	public void setCondominios(ListDataModel<Condominio> condominios) {
		this.condominios = condominios;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void npj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getTaxaLeitura() {
		return taxaLeitura;
	}

	public void setTaxaLeitura(String taxaLeitura) {
		this.taxaLeitura = taxaLeitura;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Date getInicioContrato() {
		return inicioContrato;
	}

	public void setInicioContrato(Date inicioContrato) {
		this.inicioContrato = inicioContrato;
	}

	public Date getFimContrato() {
		return fimContrato;
	}

	public void setFimContrato(Date fimContrato) {
		this.fimContrato = fimContrato;
	}

	public Boolean getTemPoco() {
		return temPoco;
	}

	public void setTemPoco(Boolean temPoco) {
		this.temPoco = temPoco;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public String getRuaComNumero() {
		return ruaComNumero;
	}

	public void setRuaComNumero(String ruaComNumero) {
		this.ruaComNumero = ruaComNumero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
}
