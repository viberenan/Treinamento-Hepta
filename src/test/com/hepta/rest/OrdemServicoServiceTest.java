package test.com.hepta.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hepta.entity.Clientes;
import com.hepta.entity.OrdemServico;
import com.hepta.persistence.ClientesDao;
import com.hepta.persistence.OrdemServicoDao;
import com.hepta.rest.OrdemServicoService;

public class OrdemServicoServiceTest extends JerseyTest {

	@Override
	protected Application configure() {
		forceSet(TestProperties.CONTAINER_PORT, "0");
		return new ResourceConfig(OrdemServicoService.class);
	}

	private Clientes cliente;

	private ClientesDao clienteDao;

	private OrdemServico ordemServico;

	private OrdemServicoDao ordemServicoDao;

	private Integer idCliente;

	private Integer idOs;

	@Before
	public void setup() {
		cliente = new Clientes();
		ordemServico = new OrdemServico();
		ordemServicoDao = new OrdemServicoDao();
		clienteDao = new ClientesDao();
		ordemServico.setData("30/10/2020");
		ordemServico.setEquipamento("teste");
		ordemServico.setServico("teste");
	}

	@After
	public void tearDown() {
		ordemServico.setId(idOs);
		ordemServicoDao.delete(ordemServico);
		cliente.setId(idCliente);
		clienteDao.delete(cliente);
	}

	@Test
	public void RetornarOrdemServicoPorcliente() throws ParseException {
		idCliente = clienteDao.save(cliente).getId();
		ordemServico.setIdCliente(idCliente);
		idOs = ordemServicoDao.save(cliente, ordemServico).getId();
		Response response = target("/os/" + idCliente).request().get();
		assertEquals("A Resposta HTTP deve ser 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("deve conter no Content-Type : ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	@Test
	public void cadastrarUmaNovaOrdemServico() throws ParseException {
		idCliente = clienteDao.save(cliente).getId();
		ordemServico.setIdCliente(idCliente);
		Response response = target("/os/inserir/").request().post(Entity.json(ordemServico));
		assertEquals("A Resposta HTTP deve ser 201: ", Status.CREATED.getStatusCode(), response.getStatus());
		assertEquals("deve conter no Content-Type : ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
		assertNotNull(response.getEntity());
		idOs = response.readEntity(OrdemServico.class).getId();
	}

	@Test
	public void retornarOrdemServicoPorId() throws ParseException {
		idCliente = clienteDao.save(cliente).getId();
		ordemServico.setIdCliente(idCliente);
		idOs = ordemServicoDao.save(cliente, ordemServico).getId();
		Response response = target("/os/buscarid/" + idOs).request().get();
		assertEquals("A Resposta HTTP deve ser 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("deve conter no Content-Type : ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
	}

	@Test
	public void deletarOrdemServico() throws ParseException {
		idCliente = clienteDao.save(cliente).getId();
		ordemServico.setIdCliente(idCliente);
		idOs = ordemServicoDao.save(cliente, ordemServico).getId();
		Response response = target("/os/deletar/" + idOs).request().delete();
		assertEquals("A Resposta HTTP deve ser 204: ", Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void atualizarOrdemServico() throws ParseException {
		idCliente = clienteDao.save(cliente).getId();
		ordemServico.setIdCliente(idCliente);
		idOs = ordemServicoDao.save(cliente, ordemServico).getId();
		OrdemServico osAtualiza = new OrdemServico();
		osAtualiza.setData("20/10/2020");
		osAtualiza.setEquipamento("teste");
		osAtualiza.setServico("teste");
		osAtualiza.setIdCliente(idCliente);
		Response response = target("/os/atualizar/" + idOs).request().put(Entity.json(osAtualiza));
		assertEquals("A Resposta HTTP deve ser 200: ", Status.OK.getStatusCode(), response.getStatus());
		assertEquals("deve conter no Content-Type : ", MediaType.APPLICATION_JSON,
				response.getHeaderString(HttpHeaders.CONTENT_TYPE));
		assertNotNull(response.getEntity());
	}

}
