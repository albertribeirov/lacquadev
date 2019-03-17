package br.com.lacqua.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;

public class TesteJPA {

	public static void main(String[] args) {

		List<String> lista = BibliotecaFuncoes.lerArquivo("D:\\teste.txt");
		BigDecimal valor = BigDecimal.ZERO;
		BigDecimal conversor = new BigDecimal("12.35");
		BigDecimal total = BigDecimal.ZERO;
		String data = "15/03/2018";
		String torre = "1";

		System.out.println("Apto  " + "Anterior  " + "Atual  " + "Consumo  " + "Valor	" + "Data	" + "Torre	");
		Iterator<String> it = lista.iterator();
		while (it.hasNext()) {
			BigDecimal consumo = BigDecimal.ZERO;
			String linha = (String) it.next();
			List<String> dados = BibliotecaFuncoes.split(linha, ";");
			String apartamento = (String) dados.get(0);
			BigDecimal anterior = new BigDecimal((String) dados.get(1));
			BigDecimal atual = new BigDecimal((String) dados.get(2));

			consumo = atual.subtract(anterior);

			valor = consumo.multiply(conversor);
			valor = valor.setScale(2, RoundingMode.HALF_EVEN);
			total = total.add(valor);
			System.out.println(apartamento + "  " + anterior + "  " + atual + "  " + consumo + "  " + valor + "  " + data + "  " + torre);
			

		}
		System.out.println("");
		System.out.println("Total do condomínio: " + total);
	}
}