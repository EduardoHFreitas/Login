package com.company.login.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "usuario", schema = "webapp")
@Entity
public class Usuario implements Serializable {

	/**
	 * CREATE TABLE usuario ( idUsuario INT PRIMARY KEY NOT NULL, login VARCHAR(60)
	 * NOT NULL, senha VARCHAR(400) NOT NULL, );
	 * 
	 * 
	 * CREATE SEQUENCE idUsuario;
	 * 
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_usuario")
	@SequenceGenerator(name = "seq_id_usuario", sequenceName = "seq_id_usuario")
	@Column(name = "idUsuario")
	private Long idUsuario;

	@Column(name = "login")
	private String login;

	@Column(name = "senha")
	private String senha;

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
