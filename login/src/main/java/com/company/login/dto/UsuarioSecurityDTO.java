package com.company.login.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UsuarioSecurityDTO extends User {

	private static final long serialVersionUID = 1L;

	public UsuarioSecurityDTO(String login, String senha, Collection<? extends GrantedAuthority> authorities) {
		super(login, senha, true, true, true, true, authorities);
	}

}
