package br.com.lacqua.util;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.EntityManager;

import br.com.lacqua.model.Condominio;
import br.com.lacqua.service.CondominioService;

public class TesteJPA {

	public static void main(String[] args) {

		System.out.println("Iniciando teste de gravação");

		CondominioService condominioService = new CondominioService();
		EntityManager manager = JPAUtil.getEntityManager();
		manager.getTransaction().begin();

		Condominio condominio = new Condominio();
		condominio.setNome("Condomínio Condemônio");
		condominio.setAtivo(true);
		condominio.setCnpj("481866789655");
		condominio.setInscricaoEstadual("24631425631");
		condominio.setInscricaoMunicipal("129381723891");
		condominio.setTaxaLeitura(new BigDecimal(10.60));
		condominio.setTelefone1("8165458798");
		condominio.setTelefone2("8132156578");
		condominio.setInicioContrato(Calendar.getInstance().getTime());
		condominio.setPoco(Boolean.FALSE);
		condominio.setAtivo(Boolean.TRUE);
		condominio.setObservacao("Testando renomeação e criação de colunas.");
		condominio.setLastModified(Calendar.getInstance().getTime());
		condominio.setRuaComNumero("Rua das Bença");
		condominio.setCidade("Olinda");
		condominio.setBairro("Salgadinho");
		condominio.setCep("53110120");
		condominio.setEmail("mail@mail.com");
		condominioService.inserir(condominio);
		
		//manager.persist(condominio);
		manager.getTransaction().commit();
		manager.close();
		
	}
}