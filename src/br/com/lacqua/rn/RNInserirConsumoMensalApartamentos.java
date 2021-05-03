package br.com.lacqua.rn;

import br.com.lacqua.model.Apartamento;
import br.com.lacqua.model.Leitura;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

public class RNInserirConsumoMensalApartamentos {

    public static void inserirConsumoMensalApartamentos(List<Apartamento> apartamentos, Leitura leitura, EntityManager entityManager) {
        for (Apartamento apartamento : apartamentos) {
            Leitura consumo = new Leitura();
            BigDecimal leituraToSave;
            boolean isGravar = false;

            if (Objects.nonNull(apartamento.getLeitura())) {
                isGravar = true;
                leituraToSave = apartamento.getLeitura();
                leituraToSave = leituraToSave.setScale(0, RoundingMode.HALF_EVEN);
                consumo.setLeitura(leituraToSave);
            }

            if (Objects.nonNull(apartamento.getCliente())) {
                consumo.setCliente(apartamento.getCliente());
            }

            if (Objects.nonNull(apartamento.getTorre())) {
                consumo.setTorre(apartamento.getTorre());
            }

            if (Objects.nonNull(leitura)) {
                consumo.setMesReferenciaLeitura(leitura.getMesReferenciaLeitura());
                consumo.setDataRealizacaoLeitura(leitura.getDataRealizacaoLeitura());
            }

            consumo.setApartamento(apartamento);
            consumo.setCondominio(apartamento.getCondominio());
            consumo.setTorre(apartamento.getTorre());

            if (isGravar) {
                entityManager.persist(consumo);
            }
        }
    }
}
