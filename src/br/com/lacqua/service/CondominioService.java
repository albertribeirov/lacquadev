package br.com.lacqua.service;

import java.util.List;

import javax.inject.Inject;

import br.com.lacqua.dao.CondominioDAO;
import br.com.lacqua.exception.ValidationException;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Log.TipoMensagem;
import br.com.lacqua.util.Constantes;

/**
 * Métodos de negócio relacionados à entidade Condominio
 */
public class CondominioService extends Service {

	@Inject
	private CondominioDAO condominioDAO;

	@Inject
	private LogService logService;

	/**
	 * Carrega um Condominio cadastrado no banco de dados.
	 * 
	 * @param id Id do condomínio
	 */
	public Condominio carregar(Integer id) {
		return condominioDAO.carregar(Condominio.class, id);
	}

	/**
	 * Insere um novo Condominio no banco de dados
	 * 
	 * @param condominio Condominio a ser inserido
	 * @throws ValidationException Exceção de validação
	 */
	public Integer inserir(Condominio condominio) throws ValidationException {
		try {
			beginTransaction();
			
			if (condominioDAO.buscarCondominioPorNome(condominio.getNome(), null)) {
				throw new ValidationException(Constantes.MSG_ERRO_EXISTE_CONDOMINIO_NOME);
			}
			
			if (condominioDAO.buscarCondominioPorCNPJ(condominio.getCnpj(), null)) {
				throw new ValidationException(Constantes.MSG_ERRO_EXISTE_CONDOMINIO_CNPJ);
			}
			
			Integer id = condominioDAO.salvar(condominio);
			logService.log("Condominio inserido: " + condominio.getNome(), TipoMensagem.INFO);

			commitTransaction();
			return id;

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Altera um Condominio cadastrado no banco de dados.
	 * 
	 * @param condominio Condomínio a ser alterado
	 * @throws ValidationException Exceção de validação
	 */
	public void alterar(Condominio condominio) throws ValidationException {
		try {
			beginTransaction();
			
			if (condominioDAO.buscarCondominioPorNome(condominio.getNome(), condominio.getId())) {
				throw new ValidationException(Constantes.MSG_ERRO_EXISTE_CONDOMINIO_NOME);
			}
			
			if (condominioDAO.buscarCondominioPorCNPJ(condominio.getCnpj(), condominio.getId())) {
				throw new ValidationException(Constantes.MSG_ERRO_EXISTE_CONDOMINIO_CNPJ);
			}

			condominioDAO.alterar(condominio);
			logService.log("Condominio alterado: " + condominio.getNome(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Exclui um Condominio do banco de dados
	 * 
	 * @param id Número de matrícula do Condominio a ser excluído
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			Condominio condominio = condominioDAO.carregar(Condominio.class, id);
			condominioDAO.excluir(condominio);
			logService.log("Condominio excluído: " + id, TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Lê todos os Apartamentos cadastrados no banco de dados
	 * 
	 * @return Lista de Condomínios cadastrados
	 */
	public List<Condominio> listarCondominios() {
		return condominioDAO.listarCondominios();
	}
}