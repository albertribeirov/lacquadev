package br.com.lacqua.service;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import br.com.lacqua.dao.TarifaCompesaDAO;
import br.com.lacqua.model.TarifaCompesa;
import br.com.lacqua.model.Log.TipoMensagem;

@SuppressWarnings("serial")
public class TarifaCompesaService extends Service {

	@Inject
	private TarifaCompesaDAO tarifaCompesaDAO;

	@Inject
	private LogService logService;

	public TarifaCompesa carregar(Integer id) {
		return tarifaCompesaDAO.carregar(TarifaCompesa.class, id);
	}

	public void inserir(TarifaCompesa tarifaCompesa) {
		try {
			beginTransaction();
			
			tarifaCompesa.setLastModified(Calendar.getInstance().getTime());
			tarifaCompesaDAO.salvar(tarifaCompesa);
			logService.log("Tarifa inserida: " + tarifaCompesa.getId(), TipoMensagem.INFO);
			
			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	public void alterar(TarifaCompesa tarifaCompesa) {
		try {
			beginTransaction();
			
			tarifaCompesa.setLastModified(Calendar.getInstance().getTime());
			tarifaCompesaDAO.alterar(tarifaCompesa);
			logService.log("Tarifa alterada: " + tarifaCompesa.getId(), TipoMensagem.INFO);
			
			commitTransaction();
			
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Exclui uma tarifa do banco de dados
	 * 
	 * @param integer Número da tarifa a ser excluída
	 * @throws ServiceException
	 */
	public void excluir(Integer id) {
		try {
			beginTransaction();

			TarifaCompesa tarifaCompesa = tarifaCompesaDAO.carregar(TarifaCompesa.class, id);
			tarifaCompesaDAO.excluir(tarifaCompesa);
			logService.log("Condominio excluído: " + id, TipoMensagem.INFO);

			commitTransaction();

		} catch (RuntimeException e) {
			rollbackTransaction();
			throw e;
		}
	}
	
	/**
	 * Lê todas as tarifas da compesa cadastradas no banco de dados
	 * 
	 * @return Lista de tarifas da compesa cadastrados
	 * @throws ServiceException
	 */
	public List<TarifaCompesa> listarTarifasCompesa(){
		return tarifaCompesaDAO.listarTarifasCompesa();
	}
}