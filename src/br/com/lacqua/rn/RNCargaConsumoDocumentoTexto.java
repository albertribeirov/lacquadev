package br.com.lacqua.rn;

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.model.Leitura;
import br.com.lacqua.util.BibliotecaFuncoes;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RNCargaConsumoDocumentoTexto {

    public static void cargaPorArquivoTXT(Leitura pLeitura, EntityManager entityManager) {
        Date dataRealizacaoLeitura = pLeitura.getDataRealizacaoLeitura();
        Integer mesReferencia = pLeitura.getMesReferenciaLeitura();
        Condominio condominio;
        // TODO Verificar se usa biblioteca de ano ou nao
        Integer ano = BibliotecaFuncoes.getAnoFromDate(dataRealizacaoLeitura);

        List<String> texto = BibliotecaFuncoes.lerArquivo("D:/arquivo.txt");


        for (String linha : texto) {
            Leitura consumoGas = new Leitura();
            BigDecimal leitura;
            String linhaConcatenada;
            String numeroApartamento;
            List<String> line;
            List<Apartamento> apartamentos;

            linhaConcatenada = linha;
            line = BibliotecaFuncoes.split(linhaConcatenada, ";");
            numeroApartamento = line.get(0);
            leitura = new BigDecimal(line.get(1));
            leitura = BibliotecaFuncoes.escalarConsumo(leitura);
            condominio = entityManager.find(Condominio.class, pLeitura.getCondominio().getId());

            apartamentos = condominio.getApartamentos();

            for (Apartamento apartamento : apartamentos) {
                if (numeroApartamento.equals(apartamento.getNumero().toString())) {
                    atualizaConsumoGas(consumoGas, leitura, apartamento);
                    consumoGas.setDataRealizacaoLeitura(dataRealizacaoLeitura);
                    consumoGas.setMesReferenciaLeitura(mesReferencia);
                    consumoGas.setAno(ano);
                    entityManager.persist(consumoGas);
                }
            }
        }
    }

    public static void atualizaConsumoGas(Leitura consumoGas, BigDecimal leitura, Apartamento apart) {
        consumoGas.setApartamento(apart);
        consumoGas.setCondominio(apart.getCondominio());

        if (apart.getTorre() != null) {
            consumoGas.setTorre(apart.getTorre());
        }

        if (apart.getCliente() != null) {
            consumoGas.setCliente(apart.getCliente());
        }
        consumoGas.setLeitura(leitura);
    }
}
