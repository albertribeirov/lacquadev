package br.com.lacqua.ejb;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.lacqua.dao.ApartamentoDAO;
import br.com.lacqua.model.Apartamento;
import br.com.lacqua.service.ApartamentoService;

/**
 * Session Bean implementation class ControladorConsumoBean
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorConsumoBean implements ControladorConsumo {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void calcularConsumo() {

	}
	
	@Override
	public void cadastrarIntervalo(Integer inicio, Integer fim, Apartamento ap) {
		Integer primeiro = inicio;
		 
		while (primeiro <= fim) {
			Apartamento apartamento = new Apartamento();
			apartamento.setCondominio(ap.getCondominio());
			apartamento.setNumero(primeiro.toString());
			apartamento.setTorre(ap.getTorre());
			//Teste já que não é possível cadastrar apartamento sem hidrômetro
			apartamento.setSerialHidrometro(primeiro.toString()+primeiro.toString());
			em.persist(apartamento);
			primeiro++;
		}
	}
}
