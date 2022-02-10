package com.hepta.rest;

import java.text.ParseException;
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

import com.hepta.dtos.ClienteOsDto;
import com.hepta.entity.Clientes;
import com.hepta.entity.OrdemServico;
import com.hepta.persistence.ClientesDao;
import com.hepta.persistence.OrdemServicoDao;

@Path("/os")
public class OrdemServicoService {

	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;

	private OrdemServicoDao dao;

	private ClientesDao clientesDao;

	public OrdemServicoService() {
		dao = new OrdemServicoDao();
		clientesDao = new ClientesDao();
	}

	protected void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * Busca Ordem de serviços do Cliente
	 * 
	 * @param idCliente
	 * @return response 200 (OK) - Conseguiu listar
	 */
	@Path("/{idCliente}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response ordemServicoRead(@PathParam("idCliente") int idCliente) {
		List<ClienteOsDto> cosDto = new ArrayList<ClienteOsDto>();
		try {
			cosDto = dao.findByCliente(idCliente);
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar ordem de serviço").build();
		}
		GenericEntity<List<ClienteOsDto>> entity = new GenericEntity<List<ClienteOsDto>>(cosDto) {
		};
		return Response.status(Status.OK).entity(entity).build();
	}

	/**
	 * Busca Ordem de serviço por ID
	 * 
	 * @param idOs
	 * @return Ordem de Serviço
	 */
	@Path("/buscarid/{idOs}")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response ordemServicoPorId(@PathParam("idOs") int idOs) {
		try {
			OrdemServico os = new OrdemServico();
			os = dao.findById(idOs);
			if (os == null) {
				return Response.status(Status.BAD_REQUEST).entity("Ordem de Serviço não encontrada").build();
			}
			return Response.status(Status.OK).entity(os).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar ordem de serviço").build();
		}
	}

	/**
	 * Adciona uma nova ordem de serviço
	 * 
	 * @param os
	 * @return response 201 - Conseguiu Criar
	 * @throws ParseException
	 */
	@Path("/inserir")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Response ordemServicoCreate(OrdemServico os) throws ParseException {
		if (isValidOrdemServico(os) == false) {
			return Response.status(Status.BAD_REQUEST).entity("Campos obrigatórios não preenchidos").build();
		}

		Clientes cliente = clientesDao.findById(os.getIdCliente());
		if (cliente == null) {
			return Response.status(Status.BAD_REQUEST).entity("Cliente inválido").build();
		}
		dao.save(cliente, os);
		return Response.status(Status.CREATED).entity(os).build();
	}

	/**
	 * Atualiza uma ordem de Serviço
	 * 
	 * @param idOs
	 * @param ordemServico
	 * @return response 200 - Conseguiu Atualizar
	 * @throws ParseException
	 */
	@Path("/atualizar/{idOs}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PUT
	public Response ordemServicoUpdate(@PathParam("idOs") int idOs, OrdemServico ordemServico) throws ParseException {
		Clientes cliente = clientesDao.findById(ordemServico.getIdCliente());
		if (isValidOrdemServico(ordemServico) == false) {
			return Response.status(Status.BAD_REQUEST).entity("Campos obrigatórios não preenchidos").build();
		}
		if (dao.findById(idOs) == null) {
			return Response.status(Status.BAD_REQUEST).entity("Ordem de Servico não Cadastrada").build();
		}
		if (cliente == null) {
			return Response.status(Status.BAD_REQUEST).entity("Cliente não Cadastrado").build();
		}
		ordemServico.setId(idOs);
		dao.update(cliente, ordemServico);
		return Response.status(Status.OK).entity(ordemServico).build();
	}

	/**
	 * Deleta ordem de serviço
	 * 
	 * @param idOs
	 * @return 204 (No content) - Cliente removido com sucesso
	 */
	@Path("/deletar/{idOs}")
	@Produces(MediaType.APPLICATION_JSON)
	@DELETE
	public Response ordemServicoDelete(@PathParam("idOs") int idOs) {
		OrdemServico os = dao.findById(idOs);
		if (os == null) {
			return Response.status(Status.BAD_REQUEST).entity("Ordem de serviço não encontrado").build();
		}
		dao.delete(os);
		return Response.noContent().build();
	}

	/**
	 * Valida a Ordem de Serviço
	 * 
	 * @param os
	 * @return Boolean
	 */
	public Boolean isValidOrdemServico(OrdemServico os) {
		if (os.getData() == null || os.getEquipamento() == null || os.getIdCliente() == null
				|| os.getServico() == null) {
			return false;
		}
		return true;
	}

}
