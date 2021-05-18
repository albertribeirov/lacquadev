package br.com.lacqua.webservice.model;

import br.com.lacqua.exception.ValidationException;
import br.com.lacqua.model.Condominio;
import br.com.lacqua.service.CondominioService;
import br.com.lacqua.util.Constantes;
import br.com.lacqua.util.JSONMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/condominios")
public class CondominioWebService {

    @Inject
    CondominioService condominioService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String listarCondominios() {
        try {
            return JSONMapper.getMapper().writeValueAsString(condominioService.listarCondominios());
        } catch (JsonProcessingException exception) {
            return JSONMapper.ERRO_FORMATAR_JSON;
        }
    }

    @GET
    @Path("/{idCondominio}")
    @Produces(MediaType.APPLICATION_JSON)
    public String buscarCondominio(@PathParam("idCondominio") int id) {
        try {
            return JSONMapper.getMapper().writeValueAsString(condominioService.carregar(id));
        } catch (JsonProcessingException exception) {
            return JSONMapper.ERRO_FORMATAR_JSON;
        }
    }

    @POST
    @Path("/novo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cadastrarCondominio(Condominio condominio, @Context UriInfo uriInfo) throws JsonProcessingException {
        Integer id;
        System.out.println(condominio);
        try {
            id = condominioService.inserir(condominio);
            Condominio c = condominioService.carregar(id);
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            uriBuilder.path(String.valueOf(id));
            URI uri = uriBuilder.build();
            return Response.created(uri).entity(JSONMapper.getMapper().writeValueAsString(c)).build();
        } catch (ValidationException e) {
            e.printStackTrace();
            return Response.status(400).entity(Constantes.MSG_ERRO_EXISTE_CONDOMINIO_NOME).build();
        }
    }
}