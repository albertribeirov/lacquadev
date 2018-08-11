package br.com.lacqua.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.lacqua.model.Cliente;

public class TesteJPA {

	public static void main(String[] args) {

		/*
		Condominio condominio = new Condominio();
		condominio.setNome("CondNEW TEST JPA");
		condominio.setEndereco(2);
		condominio.setAtivo(true);
		condominio.setCnpj("53434534");
		condominio.setInscricaoEstadual("6756756765");
		condominio.setInscricaoMunicipal("7567567");
		condominio.setTaxaDeLeitura(new BigDecimal(2.50));
		condominio.setTelefone1("8131312525");
		condominio.setTelefone2("8131314646");
		*/
		
		Cliente cliente = new Cliente();
		cliente.setNome("Beltrano de tal da Silva");
		cliente.setNomeReferencia("Beltrano de Tal");
		cliente.setEnderecoId(1);
		cliente.setTelefone("8197084514");
		cliente.setApartamentoId(300);
		cliente.setAtivo(true);
		cliente.setEmail("email@dominio.com.br");
		cliente.setObservacao("Cliente de teste cadastrado para verificação do JPA.");
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("lacqua");
		EntityManager manager = emf.createEntityManager();
		
		manager.getTransaction().begin();
		manager.persist(cliente);
		manager.getTransaction().commit();
		manager.close();
	}
}