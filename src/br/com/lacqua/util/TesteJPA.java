package br.com.lacqua.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

public class TesteJPA {

	public static void main(String[] args) {

		List<String> lista = BibliotecaFuncoes.lerArquivo("F:/ap.txt");
		BigDecimal valor = BigDecimal.ZERO;
		BigDecimal conversor = new BigDecimal("13.62");
		BigDecimal total = BigDecimal.ZERO;

		Iterator<String> it = lista.iterator();
		while (it.hasNext()) {
			BigDecimal resultado = BigDecimal.ZERO;
			String linha = (String) it.next();
			List<String> dados = BibliotecaFuncoes.split(linha, ";");
			String apartamento = (String) dados.get(0);
			BigDecimal anterior = new BigDecimal((String) dados.get(1));
			BigDecimal atual = new BigDecimal((String) dados.get(2));

			resultado = atual.subtract(anterior);

			valor = resultado.multiply(conversor);
			valor = valor.setScale(2, RoundingMode.HALF_EVEN);
			total = total.add(valor);
			System.out.println("Apartamento " + apartamento + " || consumo " + resultado + " || RS " + valor);
			

		}
		System.out.println("");
		System.out.println("Total do condomínio: " + total);
	}
}