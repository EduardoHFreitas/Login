package com.company.login.model;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UsuarioSecurityModel extends User {

	private static final long serialVersionUID = 1L;

	public UsuarioSecurityModel(String login, String senha) {
		super(login, senha, true, true, true, true, new ArrayList<GrantedAuthority>());
	}

}
