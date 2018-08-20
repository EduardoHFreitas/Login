package com.company.login.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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
}