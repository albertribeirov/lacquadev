package br.com.lacqua.ejb;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.ConsumoGas;

/**
 * Session Bean implementation class ControladorConsumoBean
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ControladorConsumoBean implements ControladorConsumo {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void cadastrarIntervalo(Integer inicio, Integer fim, Apartamento ap) {
		Integer primeiro = inicio;
		 
		while (primeiro <= fim) {
			Apartamento apartamento = new Apartamento();
			apartamento.setCondominio(ap.getCondominio());
			apartamento.setNumero(primeiro.toString());
			apartamento.setTorre(ap.getTorre());
			em.persist(apartamento);
			primeiro++;
		}
	}

	@Override
	public void calcularConsumo() {
		
	}

	@Override
	public void inserirConsumoMensalApartamento(Integer idApartamento, BigDecimal leitura) {
		Apartamento ap = em.find(Apartamento.class, idApartamento);
		ConsumoGas consumo = new ConsumoGas();
		
		consumo.setApartamento(ap);
		consumo.setLeitura(leitura);
		consumo.setCliente(ap.getCliente());
		consumo.setTorre(ap.getTorre());
		consumo.setCondominio(ap.getCondominio());
		
		em.persist(consumo);
	}
	
	@Override
	public void inserirConsumoMensalApartamentos(List<Apartamento> pApartamentos, List<ConsumoGas> pConsumos, ConsumoGas pConsumoGas) {
		
		Iterator<Apartamento> it = pApartamentos.iterator();
		while (it.hasNext()) {
			Apartamento ap = it.next();
			ConsumoGas consumo = new ConsumoGas();
			BigDecimal leitura = null;
			boolean isGravar = false;
			
			if (ap.getLeitura() != null) {
				isGravar = true;
				leitura = ap.getLeitura();
				leitura = leitura.setScale(0, RoundingMode.HALF_EVEN);
			System.out.println("---------------------------------  " + leitura + "  ---------------------------------");
				consumo.setLeitura(leitura);
			}				

			if (ap.getCliente() != null) {
				consumo.setCliente(ap.getCliente());
			}
			
			if (ap.getTorre() != null) {
				consumo.setTorre(ap.getTorre());
			}

			if (pConsumoGas != null) {
				consumo.setMesReferenciaLeitura(pConsumoGas.getMesReferenciaLeitura());
				consumo.setDataRealizacaoLeitura(pConsumoGas.getDataRealizacaoLeitura());
			}
			
			consumo.setApartamento(ap);
			consumo.setCondominio(ap.getCondominio());
			consumo.setTorre(ap.getTorre());
			
			if (isGravar == true) {
				em.persist(consumo);
			}
		}
	}
}
