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
		cliente.setNome("teste");
		cliente.setFone("teste");
		cliente.setEmail("teste");
		ordemServicoDao = new OrdemServicoDao();
		clienteDao = new ClientesDao();
	}

	@After
	public void tearDown() {
		cliente.setId(idCliente);
		clienteDao.delete(cliente);
	}

	
	@Test
	public void RetornarOrdemServicoPorcliente() {
		
	}
	/**
	@Test
	public void cadastrarUmaNovaOrdemServico() {
		idCliente = clienteDao.save(cliente).getId();
		Response response = target("/os/inserir/").request().post(Entity.json(
				"{\"data\":\"30/10/2022\",\"equipamento\":\"teste Equipamento 3\",\"servico\":\"teste servico 3\",\"valor\":\100.30,\"idCliente\":\"" + idCliente + "}"));
		
		
		assertEquals("A Resposta HTTP deve ser 201: ", Status.CREATED.getStatusCode(), response.getStatus());
	}
	**/

}
