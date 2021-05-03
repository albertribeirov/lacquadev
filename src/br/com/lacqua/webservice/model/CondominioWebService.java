package br.com.lacqua.webservice.model;

import br.com.lacqua.service.CondominioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Path("/condominios")
public class CondominioWebService {

    private static final String ERRO_FORMATAR_JSON = "Erro ao converter objeto para JSON.";
    @Inject
    CondominioService condominioService;

    private static ObjectMapper objectMapper = getMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listarCondominios(){
        try {
            return objectMapper.writeValueAsString(condominioService.listarCondominios());
        } catch (JsonProcessingException exception) {
            return ERRO_FORMATAR_JSON;
        }
    }

    @GET
    @Path("/{idCondominio}")
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarCondominio(@PathParam("idCondominio") int id) {
        try {
            return objectMapper.writeValueAsString(condominioService.carregar(id));
        } catch (JsonProcessingException exception) {
            return ERRO_FORMATAR_JSON;
        }
    }

    private static ObjectMapper getMapper() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(df);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }
}
