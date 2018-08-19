package com.company.login.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.company.login.model.UsuarioModel;
import com.company.login.service.MainService;

@Controller
@RequestMapping("/") 
public class MainController {

	@Autowired
	private MainService usuarioService;

	@RequestMapping(value="/", method= RequestMethod.GET)	
	public String index(){	
	    return "index";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView salvarUsuario(@ModelAttribute @Valid UsuarioModel UsuarioModel, final BindingResult result, Model model, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			model.addAttribute("UsuarioDTO", UsuarioModel);

			return new ModelAndView("");
		} else {
			usuarioService.salvarUsuario(UsuarioModel);
		}

		ModelAndView modelAndView = new ModelAndView("redirect:/index");

		redirectAttributes.addFlashAttribute("msg_resultado", "Usuário criado com sucesso!");

		return modelAndView;
	}

}
