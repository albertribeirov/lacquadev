package br.com.lacqua.test;

import java.math.BigDecimal;

public class Teste {

	public static void main(String[] args) {

		BigDecimal conta = BigDecimal.ZERO;
		BigDecimal ate10k = BigDecimal.valueOf(0.004131);
		BigDecimal ate20k = BigDecimal.valueOf(0.00474);
		BigDecimal ate30k = BigDecimal.valueOf(0.00563);
		
		Integer faixa10k = 10000;
		Integer faixa20k = 10000;
		Integer faixa30k = 10000;
		
		Integer leituraMaio = 402430;
		Integer leituraJunho = 425745;
		Integer consumoTotal = leituraJunho - leituraMaio;
		//BigDecimal consumo = new BigDecimal(consumoTotal);
		
		if (consumoTotal >= faixa10k) {
			System.out.println("");
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Consumo total " + consumoTotal);
			System.out.println("---------------------------------------------------------------------");
			System.out.println("");
			System.out.println("Pre�o do litro at� 10m�: " + ate10k);
			conta = ate10k.multiply(new BigDecimal("10000"));
			conta = conta.setScale(2, 2);
			System.out.println("Parcial da conta: " + conta);
			consumoTotal = consumoTotal - faixa10k;
			System.out.println("Consumo para a pr�xima faixa: " + consumoTotal);

			System.out.println("Valor da primeira faixa: " + conta);
			System.out.println("");
			System.out.println("****Entra no c�lculo da segunda faixa (10m� a 20m�)****");
			System.out.println("---------------------------------------------------------------------");
			System.out.println("");
			
		}
		
		if (consumoTotal >= faixa20k) {
			System.out.println("Pre�o do litro de 10m� a 20m�: " + ate20k);
			BigDecimal valorFaixaDois = BigDecimal.ZERO;
			valorFaixaDois = ate20k.multiply(new BigDecimal("10000"));
			System.out.println("Valor da faixa 2: " + valorFaixaDois.setScale(2, 3));
			conta = conta.add(valorFaixaDois);
			conta = conta.setScale(2, 3);
			System.out.println("Parcial da conta: " + conta);
			consumoTotal = consumoTotal - faixa10k;
			System.out.println("Consumo para a pr�xima faixa: " + consumoTotal);			
			System.out.println("Valor da segunda faixa: " + conta);
			System.out.println("");
			System.out.println("****Entra no c�lculo da terceira faixa (20m� a 30m�)****");
			System.out.println("---------------------------------------------------------------------");
			System.out.println("");
			
		}
		
		if (consumoTotal < faixa30k) {
			System.out.println("Consumo da faixa de at� 20m�: " + consumoTotal);
			System.out.println("Pre�o do litro de 20m� a 30m�: " + ate30k);
			BigDecimal valorFaixaTres = BigDecimal.ZERO;
			valorFaixaTres = ate30k.multiply(new BigDecimal(consumoTotal));
			System.out.println("Valor da faixa 3: " + valorFaixaTres.setScale(2, 3));
			conta = conta.add(valorFaixaTres);
			conta = conta.setScale(2, 3);
			System.out.println("Parcial da conta: " + conta);
			System.out.println("Soma de todas as faixas com faixa de 20m� a 30m�: " + conta);
			System.out.println("Valor total da conta: " + conta);
			System.out.println("");
			System.out.println("---------------------------------------------------------------------");
			System.out.println("");
			
		}
	}
}
