package com.hepta.entity;

public class OrdemServico {

	private Integer id;
	private String data;
	private String equipamento;
	private String servico;
	private byte[] nota;
	private Integer idCliente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
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
				+ ", nota=" + nota + ", idCliente=" + idCliente + "]";
	}
}
