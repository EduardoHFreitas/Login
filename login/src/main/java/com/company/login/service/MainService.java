package com.company.login.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.login.entity.Usuario;
import com.company.login.model.UsuarioModel;
import com.company.login.model.UsuarioSecurityModel;
import com.company.login.repository.MainRepository;

@Service
@Transactional
public class MainService implements UserDetailsService {

	@Autowired
	private MainRepository mainRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws RuntimeException, DisabledException {

		Usuario usuario = mainRepository.findByLogin(login);
		
		if (login == null) {
			throw new RuntimeException("O Usuário não cadastrado!");	
		}

		return new UsuarioSecurityModel(usuario.getLogin(), usuario.getSenha());
	}

	public void salvarUsuario(UsuarioModel usuarioModel) throws RuntimeException, DisabledException {

		validarUsuario(usuarioModel);
		
		String senha = getEncriptedPassword(usuarioModel.getSenha());

		Usuario usuario = new Usuario();
		usuario.setLogin(usuarioModel.getLogin());
		usuario.setSenha(senha);

		this.mainRepository.save(usuario);
	}

	private String getEncriptedPassword(String senha) {
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Erro ao encriptar senha.");
		}
	}

	private void validarUsuario(UsuarioModel usuarioDTO) {
		validarUsuario(usuarioDTO.getLogin());
		validarSenha(usuarioDTO.getSenha());
		
		if (!usuarioDTO.getSenha().equals(usuarioDTO.getConfirmarSenha())) {
			
		}
	}

	private void validarUsuario(String login) {
		if (login == null || login.isEmpty()) {
			throw new RuntimeException("O Usuário deve ser informado!");
		}
		
		if (login.length() < 8 || login.length() > 16) {
			throw new RuntimeException("O Usuário deve possuir no mínimo 8 caracteres e no máximo 16 caracteres! \n Seu Usuário possui " + login.length() + " caracteres.");
		}
		
		if (!login.matches("[A-Za-z0-9^@_]{8,16}")) {
			throw new RuntimeException("O campo de Usuário contém caracteres inválidos!");
		}
		
		Usuario usuario = mainRepository.findByLogin(login);
		
		if (usuario != null) {
			String usuarioSugerido = getSugestaoUsuario(login);
			throw new RuntimeException("Usuário já cadastrado! \n Você poderia usar: " + usuarioSugerido);
		}
	}

	private String getSugestaoUsuario(String login) {
		String loginSugerido;
		Usuario usuario;
		do {
			Random random = new Random(System.currentTimeMillis());
			loginSugerido = login + random.nextInt() % 3;
			usuario = mainRepository.findByLogin(loginSugerido);
		} while (usuario != null);
		
		return loginSugerido;
	}

	private void validarSenha(String senha) {
		if (senha == null || senha.isEmpty()) {
			throw new RuntimeException("A Senha deve ser informada!");
		}
		
		if (senha.length() < 8 || senha.length() > 16) {
			throw new RuntimeException("A Senha deve possuir no mínimo 8 caracteres e no máximo 16 caracteres! \n Sua Senha possui " + senha.length() + " caracteres.");
		}
		
		if (!senha.matches("[A-Za-z0-9^@!#$%&*]{8,16}")) {
			throw new RuntimeException("O campo de Senha contém caracteres inválidos!");
		}
	}

}