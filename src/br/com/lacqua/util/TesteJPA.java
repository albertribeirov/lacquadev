package br.com.lacqua.util;

import java.util.List;

public class TesteJPA {

	public static void main(String[] args) {
		List<String> listaClientes = BibliotecaFuncoes.lerArquivo("D:/clientes.txt");
		
		System.out.println(listaClientes.toString());
		
		
		System.out.println("Fim!");
		
	}
}