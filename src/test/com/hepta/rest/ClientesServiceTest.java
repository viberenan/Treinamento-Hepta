package test.com.hepta.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Test;

import com.hepta.entity.Clientes;
import com.hepta.persistence.ClientesDao;
import com.hepta.rest.ClientesService;

public class ClientesServiceTest extends JerseyTest {

	private Clientes cliente;

	private Integer id;

	private ClientesDao dao;

	@Override
	protected Application configure() {
		return new ResourceConfig(ClientesService.class);
	}

	@After
	public void limparBanco() {
		if (id != null) {
			cliente = new Clientes();
			dao = new ClientesDao();
			cliente.setId(id);
			dao.delete(cliente);
		}
	}

	@Test
	public void cadastrarUmNovoCliente() {
		Response response = target("/clientes/inserir/").request()
				.post(Entity.json("{\"nome\":\"teste\",\"fone\":\"111111111\",\"email\":\"teste@teste.com\"}"));
		assertEquals("A Resposta HTTP deve ser 201: ", Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("deve conter no Content-Type : ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
		assertNotNull(response.getEntity());
		id = response.readEntity(Clientes.class).getId();
	}

	@Test
	public void retornaListaDeClientes() {
		Response response = target("/clientes/").request().get();
		assertEquals("A Resposta HTTP deve ser 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("deve conter no Content-Type : ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	@Test
	public void atualizarUmCliente() {
		cliente = new Clientes();
		cliente.setNome("teste1");
		cliente.setEmail("teste1");
		cliente.setFone("teste1");
		dao = new ClientesDao();
		id = dao.save(cliente).getId();
		Response response = target("/clientes/atualizar/" + id).request()
				.put(Entity.json("{\"nome\":\"teste\",\"fone\":\"111111111\",\"email\":\"teste@teste.com\"}"));
		assertEquals("A Resposta HTTP deve ser 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("deve conter no Content-Type : ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
		assertNotNull(response.getEntity());
	}

	@Test
	public void retornaClientePeloId() {
		cliente = new Clientes();
		cliente.setNome("teste1");
		cliente.setEmail("teste1");
		cliente.setFone("teste1");
		dao = new ClientesDao();
		id = dao.save(cliente).getId();
		Response response = target("/clientes/" + id).request().get();
		assertEquals("A Resposta HTTP deve ser 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("deve conter no Content-Type : ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	@Test
	public void deletaUmcliente() {
		cliente = new Clientes();
		cliente.setNome("teste1");
		cliente.setEmail("teste1");
		cliente.setFone("teste1");
		dao = new ClientesDao();
		id = dao.save(cliente).getId();
		Response response = target("/clientes/deletar/" + id).request().delete();
		assertEquals("A Resposta HTTP deve ser 204: ", Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}
}
