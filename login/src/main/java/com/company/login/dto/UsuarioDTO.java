package com.company.login.dto;

import javax.validation.constraints.NotNull;

public class UsuarioDTO {

	private Long idUsuario;

	@NotNull(message = "O Login é de preenchimento obrigatório.")
	private String login;

	@NotNull(message = "A Senha é de preenchimento obrigatório.")
	private String senha;

	public UsuarioDTO() {

	}

	public UsuarioDTO(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
