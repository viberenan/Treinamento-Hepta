package com.hepta.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.hepta.dtos.ClienteOsDto;
import com.hepta.entity.Clientes;
import com.hepta.entity.OrdemServico;

public class OrdemServicoDao {

	private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * Salva uma nova ordem de Serviço
	 * 
	 * @param cliente
	 * @param os
	 * @throws ParseException
	 */
	public void save(Clientes cliente, OrdemServico os) throws ParseException {
		java.sql.Date sqlDate = null;
		Connection conn = Conexao.open();
		PreparedStatement pstm = null;
		String sql = "INSERT INTO os(data, equipamento, servico, valor, nota, idcli) VALUES (?,?,?,?,?,?)";
		if (os.getData() != null) {
			java.util.Date date = format.parse(os.getData());
			sqlDate = new java.sql.Date(date.getTime());
		}
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setDate(1, sqlDate);
			pstm.setString(2, os.getEquipamento());
			pstm.setString(3, os.getServico());
			pstm.setBigDecimal(4, os.getValor());
			pstm.setBytes(5, os.getNota());
			pstm.setInt(6, cliente.getId());
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.close(conn, pstm, null);
		}
	}

	/**
	 * Atualiza ordem de Serviço
	 * 
	 * @param cliente
	 * @param os
	 * @throws ParseException
	 */
	public void update(Clientes cliente, OrdemServico os) throws ParseException {
		java.util.Date date = format.parse(os.getData());
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		Connection conn = Conexao.open();
		PreparedStatement pstm = null;
		String sql = "UPDATE os SET data = ?, equipamento = ?, servico = ?, valor = ?, nota = ?, idcli = ? WHERE id = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setDate(1, sqlDate);
			pstm.setString(2, os.getEquipamento());
			pstm.setString(3, os.getServico());
			pstm.setBigDecimal(4, os.getValor());
			pstm.setBytes(5, os.getNota());
			pstm.setInt(6, cliente.getId());
			pstm.setInt(7, os.getId());
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.close(conn, pstm, null);
		}
	}

	/**
	 * Deleta uma Ordem de Serviço
	 * 
	 * @param os
	 */
	public void delete(OrdemServico os) {
		Connection conn = Conexao.open();
		PreparedStatement pstm = null;
		String sql = "DELETE FROM os WHERE id = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, os.getId());
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna uma Ordem de Serviço pelo Id
	 * 
	 * @param idOs
	 * @return Ordem Serviço
	 */
	public OrdemServico findById(Integer idOs) {
		Connection conn = Conexao.open();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM os WHERE id = ?";
		OrdemServico os = null;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idOs);
			rs = pstm.executeQuery();
			if (rs.next()) {
				os = new OrdemServico();
				os.setId(rs.getInt("id"));
				os.setData(rs.getDate("data").toString());
				os.setEquipamento(rs.getString("equipamento"));
				os.setServico(rs.getString("servico"));
				os.setValor(rs.getBigDecimal("valor"));
				os.setNota(rs.getBytes("nota"));
				os.setIdCliente(rs.getInt("idcli"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.close(conn, pstm, rs);
		}
		return os;
	}

	/**
	 * Retorna uma lista de ordem de serviço por cliente.
	 * 
	 * @param cliente
	 * @return List<ClienteOsDto>
	 */
	public List<ClienteOsDto> findByCliente(Integer idCliente) {
		Connection conn = Conexao.open();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select os.id, os.data, os.equipamento, os.servico, os.valor, os.nota, clientes.nome, clientes.telefone from os join clientes on os.idcli = clientes.id "
				+ "WHERE os.idcli = ?;";
		List<ClienteOsDto> coss = new ArrayList<ClienteOsDto>();
		ClienteOsDto cos = null;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idCliente);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cos = new ClienteOsDto();
				cos.setOsId(rs.getInt("os.id"));
				cos.setData(format.format(rs.getDate("os.data")));
				cos.setNome(rs.getString("clientes.nome"));
				cos.setFone(rs.getString("clientes.telefone"));
				cos.setEquipamento(rs.getString("os.equipamento"));
				cos.setServico(rs.getString("os.servico"));
				cos.setValor(rs.getBigDecimal("os.valor"));
				cos.setNota(rs.getBytes("os.nota"));
				coss.add(cos);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.close(conn, pstm, rs);
		}
		return coss;
	}

}
