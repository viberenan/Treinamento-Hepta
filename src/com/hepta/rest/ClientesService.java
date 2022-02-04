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
	 * Retorna todos os clientes.
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
	 * Busca Cliente pelo Id.
	 * 
	 * @param idCliente: Id do Cliente
	 * @return response 200 (OK) - Conseguiu Buscar
	 */
	@Path("/{idCliente}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response clienteReadId(@PathParam("idCliente") int idCliente) {
		Clientes cliente = new Clientes();
		cliente = dao.findById(idCliente);
		if (cliente == null) {
			return Response.status(Status.BAD_REQUEST).entity("Cliente não encontrado").build();
		}
		return Response.status(Status.OK).entity(cliente).build();
	}

	/**
	 * Adiciona um novo Cliente
	 * 
	 * @param cliente
	 * @return response 201 - Conseguiu Criar
	 */
	@Path("/inserir")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Response clienteCreate(Clientes cliente) {
		if (isValidCliente(cliente) == false) {
			return Response.status(Status.BAD_REQUEST).entity("Campos obrigatórios não preenchidos").build();
		}
		if (dao.findByEmail(cliente.getEmail()) != null) {
			return Response.status(Status.BAD_REQUEST).entity("Cliente já cadastrado").build();
		}
		dao.save(cliente);
		return Response.status(Status.CREATED).entity(cliente).build();
	}

	/**
	 * Atualiza um Cliente
	 * 
	 * @param idCliente
	 * @return response 200 (OK) - Conseguiu Atualizar
	 */
	@Path("/atualizar/{idCliente}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PUT
	public Response clienteUpdate(@PathParam("idCliente") int idCliente, Clientes cliente) {
		if (isValidCliente(cliente) == false) {
			return Response.status(Status.BAD_REQUEST).entity("Campos obrigatórios não preenchidos").build();
		}
		if (dao.findByEmail(cliente.getEmail()) != null) {
			return Response.status(Status.BAD_REQUEST).entity("Email já cadastrado").build();
		}
		if (dao.findById(idCliente) == null) {
			return Response.status(Status.BAD_REQUEST).entity("Cliente não cadastrado").build();
		}
		cliente.setId(idCliente);
		dao.update(cliente);
		return Response.status(Status.OK).entity(cliente).build();
	}

	/**
	 * Deleta um Cliente
	 * 
	 * @param idCliente
	 * @return 204 (No content) - Cliente removido com sucesso
	 */
	@Path("/deletar/{idCliente}")
	@Produces(MediaType.APPLICATION_JSON)
	@DELETE
	public Response clienteDelete(@PathParam("idCliente") int idCliente) {
		Clientes cliente = dao.findById(idCliente);
		if (cliente == null) {
			return Response.status(Status.BAD_REQUEST).entity("Cliente não encontrado").build();
		}
		dao.delete(cliente);
		return Response.noContent().build();
	}

	/**
	 * Verifica se cliente é valido
	 * 
	 * @param cliente
	 * @return Boolean
	 */
	public Boolean isValidCliente(Clientes cliente) {
		if (cliente.getNome() == null || cliente.getFone() == null || cliente.getEmail() == null) {
			return false;
		}
		return true;
	}
}
