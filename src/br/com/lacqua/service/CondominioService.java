package br.com.lacqua.service;

import java.util.List;

import javax.inject.Inject;

import br.com.lacqua.dao.CondominioDAO;
import br.com.lacqua.exception.ValidationException;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Log.TipoMensagem;
import br.com.lacqua.util.Constantes;

/**
 * M�todos de neg�cio relacionados � entidade Condominio
 */
public class CondominioService extends Service {

	@Inject
	private CondominioDAO condominioDAO;

	@Inject
	private LogService logService;

	/**
	 * Carrega um Condominio cadastrado no banco de dados.
	 * 
	 * @param id Id do condom�nio
	 */
	public Condominio carregar(Integer id) {
		return condominioDAO.carregar(Condominio.class, id);
	}

	/**
	 * Insere um novo Condominio no banco de dados
	 * 
	 * @param condominio Condominio a ser inserido
	 * @throws ValidationException Exce��o de valida��o
	 */
	public void inserir(Condominio condominio) throws ValidationException {
		try {
			beginTransaction();
			
			if (condominioDAO.buscarCondominioPorNome(condominio.getNome(), null)) {
				throw new ValidationException(Constantes.MSG_ERRO_EXISTE_CONDOMINIO_NOME);
			}
			
			if (condominioDAO.buscarCondominioPorCNPJ(condominio.getCnpj(), null)) {
				throw new ValidationException(Constantes.MSG_ERRO_EXISTE_CONDOMINIO_CNPJ);
			}
			
			condominioDAO.salvar(condominio);
			logService.log("Condominio inserido: " + condominio.getNome(), TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * Altera um Condominio cadastrado no banco de dados.
	 * 
	 * @param condominio Condom�nio a ser alterado
	 * @throws ValidationException Exce��o de valida��o
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
	 * @param id N�mero de matr�cula do Condominio a ser exclu�do
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			Condominio condominio = condominioDAO.carregar(Condominio.class, id);
			condominioDAO.excluir(condominio);
			logService.log("Condominio exclu�do: " + id, TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}

	/**
	 * L� todos os Apartamentos cadastrados no banco de dados
	 * 
	 * @return Lista de Condom�nios cadastrados
	 */
	public List<Condominio> listarCondominios() {
		return condominioDAO.listarCondominios();
	}
}