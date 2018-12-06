package br.com.lacqua.bean;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.lacqua.model.Sindico;
import br.com.lacqua.service.SindicoService;
import br.com.lacqua.util.Constantes;

@SuppressWarnings("serial")
@Named("sindicoBean")
@RequestScoped
public class SindicoBean extends AbstractBean {

	@Inject
	private SindicoService sindicoService;

	private Sindico sindico;

	private List<Sindico> sindicos;

	/*
	 * Listar sindicos
	 */
	public List<Sindico> getSindicos() throws Exception {
		if (sindicos == null) {
			sindicos = sindicoService.listarSindicos();
		}
		return sindicos;
	}
	
	/*
	 * Altera um sindico
	 */
	public String alterar(Integer id) {
		sindico = sindicoService.carregar(id);
		return null;

	}

	/*
	 * Exclui um sindico
	 */
	public String excluir(Integer id) {
		sindicoService.excluir(id);
		sindicos = null;
		return "cadastrarSindico";

	}

	/*
	 * Salva um sindico
	 */
	public String salvar() {
		try {
			if (sindico.getId() == null) {
				sindicoService.inserir(sindico);

			} else {
				sindicoService.alterar(sindico);
			}

			sindico = null;
			return Constantes.SUCESSO;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/*
	 * Cadastrar um novo sindico
	 */
	public String novoSindico() {
		return "novoSindico";
	}

	/*
	 * Obter sindico
	 */
	public Sindico getSindico() {
		if (sindico == null) {
			sindico = new Sindico();
		}
		return sindico;
	}

	/*
	 * Setar sindico
	 */
	public void setSindico(Sindico sindico) {
		this.sindico = sindico;
	}
}