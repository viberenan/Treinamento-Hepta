package com.hepta.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;

public class OrdemServico {

	private Integer id;
	private Date data;
	private String equipamento;
	private String servico;
	private BigDecimal valor;
	private byte[] nota;
	private Integer idCliente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(String equipamento) {
		this.equipamento = equipamento;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public byte[] getNota() {
		return nota;
	}

	public void setNota(byte[] nota) {
		this.nota = nota;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	@Override
	public String toString() {
		return "OrdemServico [id=" + id + ", data=" + data + ", equipamento=" + equipamento + ", servico=" + servico
				+ ", valor=" + valor + ", nota=" + Arrays.toString(nota) + ", idCliente=" + idCliente + "]";
	}
}