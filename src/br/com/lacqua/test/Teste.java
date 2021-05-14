package br.com.lacqua.test;

import java.util.function.Consumer;

public class Teste {

	public static void main(String[] args) {
		Consumer<String> func = System.out::println;

		func.accept("Java");
	}
}
