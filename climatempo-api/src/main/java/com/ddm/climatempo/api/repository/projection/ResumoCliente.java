package com.ddm.climatempo.api.repository.projection;

public class ResumoCliente {
	
	private long cd_Cliente;
	private String nm_Cliente;
	private String email;
	private boolean ativo;
	
	public ResumoCliente(long cd_Cliente, String nm_Cliente, String email, boolean ativo) {
		super();
		this.cd_Cliente = cd_Cliente;
		this.nm_Cliente = nm_Cliente;
		this.email = email;
		this.ativo = ativo;
		
	}

	
	public long getCd_Cliente() {
		return cd_Cliente;
	}
	public void setCd_Cliente(long cd_Cliente) {
		this.cd_Cliente = cd_Cliente;
	}
	public String getNm_Cliente() {
		return nm_Cliente;
	}
	public void setNm_Cliente(String nm_Cliente) {
		this.nm_Cliente = nm_Cliente;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
