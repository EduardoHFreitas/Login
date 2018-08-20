package com.company.login.validator;

import com.company.login.model.UsuarioModel;

public class ValidadorUsuario {
	
	public void validar(UsuarioModel usuarioModel) {
		validarUsuario(usuarioModel.getLogin());
		validarSenha(usuarioModel.getSenha());
		validarConfirmacaoSenha(usuarioModel);
	}

	protected void validarUsuario(String login) {
		validarLoginInformado(login);
		validarTamanhoLogin(login);
		validarCaracteresLogin(login);
	}

	protected void validarCaracteresLogin(String login) {
		if (!login.matches("[A-Za-z0-9^@_]{8,16}")) {
			throw new RuntimeException("O campo de Login contém caracteres inválidos!");
		}
	}

	protected void validarTamanhoLogin(String login) {
		if (login.length() < 8 || login.length() > 16) {
			throw new RuntimeException("O Login deve possuir no mínimo 8 caracteres e no máximo 16 caracteres! \n Seu Login possui " + login.length() + " caracteres.");
		}
	}

	protected void validarLoginInformado(String login) {
		if (login == null || login.isEmpty()) {
			throw new RuntimeException("O Login deve ser informado!");
		}
	}

	protected void validarSenha(String senha) {
		validarSenhaInformada(senha);
		validarTamanhoSenha(senha);
		validarCaracteresSenha(senha);
	}

	protected void validarCaracteresSenha(String senha) {
		if (!senha.matches("[A-Za-z0-9^@!#$%&*]{8,16}")) {
			throw new RuntimeException("O campo de Senha contém caracteres inválidos!");
		}
	}

	protected void validarTamanhoSenha(String senha) {
		if (senha.length() < 8 || senha.length() > 16) {
			throw new RuntimeException("A Senha deve possuir no mínimo 8 caracteres e no máximo 16 caracteres! \n Sua Senha possui " + senha.length() + " caracteres.");
		}
	}

	protected void validarSenhaInformada(String senha) {
		if (senha == null || senha.isEmpty()) {
			throw new RuntimeException("A Senha deve ser informada!");
		}
	}

	protected void validarConfirmacaoSenha(UsuarioModel usuarioModel) {
		if (!usuarioModel.getSenha().equals(usuarioModel.getConfirmarSenha())) {
			throw new RuntimeException("A Senhas informadas não coincidem!");
		}
	}

	
}
