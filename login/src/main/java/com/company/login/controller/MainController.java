package com.company.login.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.login.entity.Usuario;
import com.company.login.model.UsuarioModel;
import com.company.login.repository.MainRepository;
import com.company.login.service.MainService;

@Controller
@RequestMapping("/") 
public class MainController {

	@Autowired
	private MainService usuarioService;

	@Autowired
	private MainRepository mainRepository;
		
	@RequestMapping(value="/", method= RequestMethod.GET)	
	public ModelAndView index(UsuarioModel usuarioModel){
		ModelAndView mv = new ModelAndView("index");
		mv.addObject(usuarioModel);
		return mv;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView salvarUsuario(@ModelAttribute UsuarioModel usuarioModel, Model model, RedirectAttributes redirectAttributes) {

		validar(usuarioModel);
				
		usuarioService.salvarUsuario(usuarioModel);
			
		ModelAndView modelAndView = new ModelAndView("redirect:/index");

		redirectAttributes.addFlashAttribute("sucesso", "Usuário criado com sucesso!");

		return modelAndView;
	}

	private void validar(UsuarioModel usuarioModel) {
		validarUsuario(usuarioModel.getLogin());
		validarSenha(usuarioModel.getSenha());
		validarConfirmacaoSenha(usuarioModel);
	}

	private void validarConfirmacaoSenha(UsuarioModel usuarioModel) {
		if (!usuarioModel.getSenha().equals(usuarioModel.getConfirmarSenha())) {
			throw new RuntimeException("A Senhas informadas não coincidem!");
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
