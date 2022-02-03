package com.hepta.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.hepta.entity.Clientes;
import com.hepta.persistence.ClientesDao;

@Path("/clientes")
public class ClientesService {

	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;

	private ClientesDao dao;

	public ClientesService() {
		dao = new ClientesDao();
	}

	protected void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Retornar todos os clientes.
	 * 
	 * @return response 200 (OK) - Conseguiu listar
	 */
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response clienteRead() {
		List<Clientes> clientes = new ArrayList<Clientes>();
		try {
			clientes = dao.findAll();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar clientes").build();
		}
		GenericEntity<List<Clientes>> entity = new GenericEntity<List<Clientes>>(clientes) {
		};
		return Response.status(Status.OK).entity(entity).build();
	}

	/**
	 * Métodos simples apenas para testar o REST
	 * 
	 * @return
	 */
	@Path("/teste")
	@Produces(MediaType.TEXT_PLAIN)
	@GET
	public String TesteJersey() {
		return "Funcionando.";
	}

}
