package com.hepta.dtos;

import java.math.BigDecimal;
import java.util.Arrays;

public class ClienteOsDto {

	private Integer osId;
	private String nome;
	private String fone;
	private String data;
	private String equipamento;
	private String servico;
	private BigDecimal valor;
	private byte[] nota;

	public Integer getOsId() {
		return osId;
	}

	public void setOsId(Integer osId) {
		this.osId = osId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
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

	@Override
	public String toString() {
		return "ClienteOsDto [osId=" + osId + ", nome=" + nome + ", fone=" + fone + ", data=" + data + ", equipamento="
				+ equipamento + ", servico=" + servico + ", valor=" + valor + ", nota=" + Arrays.toString(nota) + "]";
	}

}
