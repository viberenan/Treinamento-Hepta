package com.hepta.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.hepta.entity.Clientes;
import com.hepta.entity.OrdemServico;

public class OrdemServicoDao {

	public void save(Clientes cliente, OrdemServico os) {
		Connection conn = Conexao.open();
		PreparedStatement pstm = null;
		String sql = "INSERT INTO os(data, equipamento, servico, valor, nota, idcli) VALUES (?,?,?,?,?,?)";
		try {
			pstm = conn.prepareStatement(sql);
			pstm.setDate(1, os.getData());
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
	
	
}
