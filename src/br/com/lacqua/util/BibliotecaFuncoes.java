package br.com.lacqua.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BibliotecaFuncoes {

	public static List<String> lerArquivo(String nomeArquivo) {

		String linha = "";
		List<String> lista = new ArrayList<>();

		try (Scanner scanner = new Scanner(new File(nomeArquivo))) {

			while (scanner.hasNextLine()) {
				linha = scanner.nextLine();
				lista.add(linha);

			}
		} catch (IOException e) {
			e.printStackTrace();

		}
		return lista;
	}

	public static List<String> split(String texto, String separador) {

		Integer indice= null;
		String linha = "";
		List<String> lista = new ArrayList<String>();

		while (texto.contains(separador)) {
			indice = texto.indexOf(separador);
			linha = texto.substring(0, indice);
			indice += 1;
			texto = texto.substring(indice);
			lista.add(linha);
		} 
		lista.add(texto);

		return lista;
	}
}
