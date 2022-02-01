package com.hepta.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hepta.entity.Clientes;

public class ClientesDao {

	/**
	 * Salva o cliente no Banco de dados.
	 * 
	 * @param cliente
	 */
	public void save(Clientes cliente) {
		Connection conn = Conexao.open();
		PreparedStatement pstm = null;
		String sql = "INSERT INTO clientes(nome, email, telefone) VALUES (?,?,?)";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, cliente.getNome());
			pstm.setString(2, cliente.getEmail());
			pstm.setString(3, cliente.getFone());
			pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.close(conn, pstm, null);
		}
	}

	/**
	 * Atualiza um Cliente no Banco de dados.
	 * 
	 * @param cliente
	 */
	public void update(Clientes cliente) {
		Connection conn = Conexao.open();
		PreparedStatement pstm = null;
		String sql = "UPDATE clientes SET nome = ?, email = ?, telefone = ? WHERE id = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, cliente.getNome());
			pstm.setString(2, cliente.getEmail());
			pstm.setString(3, cliente.getFone());
			pstm.setInt(4, cliente.getId());
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.close(conn, pstm, null);
		}
	}

	/**
	 * Deleta um Cliente do Banco de dados.
	 * 
	 * @param cliente
	 */
	public void delete(Clientes cliente) {
		Connection conn = Conexao.open();
		PreparedStatement pstm = null;
		String sql = "DELETE FROM clientes WHERE id = ?";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, cliente.getId());
			pstm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lista todos os Clientes.
	 * 
	 * @return List<Clientes>
	 */
	public List<Clientes> findAll() {
		Connection conn = Conexao.open();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM clientes";
		List<Clientes> clientes = new ArrayList<Clientes>();
		Clientes cliente = null;
		try {
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				cliente = new Clientes();
				cliente.setId(rs.getInt("id"));
				cliente.setNome(rs.getString("nome"));
				cliente.setEmail(rs.getString("email"));
				cliente.setFone(rs.getString("telefone"));
				clientes.add(cliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.close(conn, pstm, rs);
		}
		return clientes;
	}

	/**
	 * Buscar Cliente pelo seu id.
	 * 
	 * @param idCliente
	 * @return Cliente
	 */
	public Clientes findById(Integer idCliente) {
		Connection conn = Conexao.open();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM clientes WHERE id = ?";
		Clientes cliente = null;
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idCliente);
			rs = pstm.executeQuery();
			cliente = new Clientes();
			cliente.setId(rs.getInt("id"));
			cliente.setNome(rs.getString("nome"));
			cliente.setEmail(rs.getString("email"));
			cliente.setFone(rs.getString("telefone"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Conexao.close(conn, pstm, rs);
		}
		return cliente;
	}
}
