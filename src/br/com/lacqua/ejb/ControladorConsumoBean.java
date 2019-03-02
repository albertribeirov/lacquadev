package br.com.lacqua.ejb;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.ConsumoGas;
import br.com.lacqua.util.BibliotecaFuncoes;

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
	public void inserirConsumoMensalApartamentos(List<Apartamento> pApartamentos, List<ConsumoGas> pConsumos,
			ConsumoGas pConsumoGas) {

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

	@Override
	public void cargaConsumoDocumentoTexto(ConsumoGas pConsumoGas) {
		Date dataRealizacaoLeitura = pConsumoGas.getDataRealizacaoLeitura();
		Integer mesReferencia = pConsumoGas.getMesReferenciaLeitura();
		Condominio cond = new Condominio();

		List<String> texto = BibliotecaFuncoes.lerArquivo("F:/atlantico.txt");
		Iterator<String> it = texto.iterator();

		while (it.hasNext()) {
			ConsumoGas consumoGas = new ConsumoGas();
			BigDecimal leitura = BigDecimal.ZERO;
			String linhaConcatenada = "";
			String numeroApartamento = "";
			List<String> line = new ArrayList<String>();
			List<Apartamento> aps = new ArrayList<Apartamento>();

			linhaConcatenada = it.next();
			line = BibliotecaFuncoes.split(linhaConcatenada, ";");
			numeroApartamento = line.get(0);
			leitura = new BigDecimal(line.get(1));
			leitura = BibliotecaFuncoes.escalarConsumo(leitura);
			cond = em.find(Condominio.class, Integer.parseInt("12"));

			aps = cond.getApartamentos();

			Iterator<Apartamento> iter = aps.iterator();
			while (iter.hasNext()) {
				Apartamento apart = iter.next();

				if (numeroApartamento.equals(apart.getNumero())) {
					consumoGas.setApartamento(apart);
					consumoGas.setCondominio(apart.getCondominio());

					if (apart.getTorre() != null) {
						consumoGas.setTorre(apart.getTorre());
					}

					if (apart.getCliente() != null) {
						consumoGas.setCliente(apart.getCliente());
					}

					consumoGas.setLeitura(leitura);
					consumoGas.setDataRealizacaoLeitura(dataRealizacaoLeitura);
					consumoGas.setMesReferenciaLeitura(mesReferencia);
					em.persist(consumoGas);
					System.out.println("Sucesso! Apartamento " + numeroApartamento + " teve esta leitura: " + leitura);
				}
			}
		}
	}
}
