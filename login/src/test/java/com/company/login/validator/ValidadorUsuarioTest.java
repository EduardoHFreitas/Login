package com.company.login.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.company.login.entity.Usuario;
import com.company.login.model.UsuarioModel;

@RunWith(MockitoJUnitRunner.class)
public class ValidadorUsuarioTest {

	private UsuarioModel usuarioModel;

	ValidadorUsuario validadorUsuario = new ValidadorUsuario();
	
	private Usuario usuario;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		usuarioModel = new UsuarioModel();
		usuario = new Usuario();
		usuario.setLogin(usuarioModel.getLogin());
	}

	@Test(expected = RuntimeException.class)
	public void deveLancarExcecaoQuandoLoginNaoForInformado() {
		validadorUsuario.validarLoginInformado(usuarioModel.getLogin());
	}

	@Test(expected = RuntimeException.class)
	public void deveLancarExcecaoQuandoLoginPossuirMenosDeOitoCaracteres() {
		usuarioModel.setLogin("Teste");

		validadorUsuario.validarTamanhoLogin(usuarioModel.getLogin());
	}

	@Test(expected = RuntimeException.class)
	public void deveLancarExcecaoQuandoLoginPossuirMaisDeDezesseisCaracteres() {
		usuarioModel.setLogin("TesteComMaisDeDezesseisCaracteres");

		validadorUsuario.validarTamanhoLogin(usuarioModel.getLogin());
	}

	@Test(expected = RuntimeException.class)
	public void deveLancarExcecaoQuandoLoginPossuirCaracteresInvalidos() {
		usuarioModel.setLogin("TesteCom.Ponto");

		validadorUsuario.validarCaracteresLogin(usuarioModel.getLogin());
	}
	
	@Test(expected = RuntimeException.class)
	public void deveLancarExcecaoQuandoSenhaNaoForInformado() {
		usuarioModel.setLogin("LoginValido");
		validadorUsuario.validarSenhaInformada(usuarioModel.getSenha());
	}

	@Test(expected = RuntimeException.class)
	public void deveLancarExcecaoQuandoSenhaPossuirMenosDeOitoCaracteres() {
		usuarioModel.setLogin("LoginValido");
		usuarioModel.setSenha("Teste");

		validadorUsuario.validarTamanhoSenha(usuarioModel.getSenha());
	}

	@Test(expected = RuntimeException.class)
	public void deveLancarExcecaoQuandoSenhaPossuirMaisDeDezesseisCaracteres() {
		usuarioModel.setLogin("LoginValido");
		usuarioModel.setSenha("TesteComMaisDeDezesseisCaracteres");

		validadorUsuario.validarTamanhoSenha(usuarioModel.getSenha());
	}

	@Test(expected = RuntimeException.class)
	public void deveLancarExcecaoQuandoSenhaPossuirCaracteresInvalidos() {
		usuarioModel.setLogin("LoginValido");
		usuarioModel.setSenha("Senha¬Invalida");

		validadorUsuario.validarCaracteresSenha(usuarioModel.getSenha());
	}
	
	@Test(expected = RuntimeException.class)
	public void deveLancarExcecaoQuandoConfirmacaoDeSenhaForDiferenteDaSenha() {
		usuarioModel.setLogin("LoginValido");
		usuarioModel.setSenha("Senha¬Invalida");
		usuarioModel.setConfirmarSenha("SenhaValida");
		
		validadorUsuario.validarConfirmacaoSenha(usuarioModel);
	}
}
