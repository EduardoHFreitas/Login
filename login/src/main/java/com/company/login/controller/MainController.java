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
import com.company.login.validator.ValidadorUsuario;

@Controller
@RequestMapping("/") 
public class MainController {

	@Autowired
	private MainService usuarioService;

	@Autowired
	private MainRepository mainRepository;
	
	ValidadorUsuario validadorUsuarioModel = new ValidadorUsuario();
	
	@RequestMapping(value="/", method= RequestMethod.GET)	
	public ModelAndView index(UsuarioModel usuarioModel){
		ModelAndView mv = new ModelAndView("index");
		mv.addObject(usuarioModel);
		return mv;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView salvarUsuario(@ModelAttribute UsuarioModel usuarioModel, Model model, RedirectAttributes redirectAttributes) {
		
		validarLoginJaExistente(usuarioModel.getLogin());

		validadorUsuarioModel.validar(usuarioModel);
				
		usuarioService.salvarUsuario(usuarioModel);
			
		ModelAndView modelAndView = new ModelAndView("redirect:/index");

		redirectAttributes.addFlashAttribute("sucesso", "Usuário criado com sucesso!");

		return modelAndView;
	}
	
	private void validarLoginJaExistente(String login) {
		Usuario usuario = mainRepository.findByLogin(login);
		
		if (usuario != null) {
			String usuarioSugerido = getSugestaoUsuario(login);
			throw new RuntimeException("Login já cadastrado! \n Você poderia usar: " + usuarioSugerido);
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

}
