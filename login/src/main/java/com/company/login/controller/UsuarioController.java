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

import com.company.login.dto.UsuarioDTO;
import com.company.login.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public ModelAndView novoCadastro(Model model) {

		model.addAttribute("UsuarioDTO", new UsuarioDTO());

		return new ModelAndView("cadastro");
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView salvarUsuario(@ModelAttribute @Valid UsuarioDTO UsuarioDTO, final BindingResult result, Model model, RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			model.addAttribute("UsuarioDTO", UsuarioDTO);

			return new ModelAndView("cadastro");
		} else {
			usuarioService.salvarUsuario(UsuarioDTO);
		}

		ModelAndView modelAndView = new ModelAndView("redirect:/index");

		redirectAttributes.addFlashAttribute("msg_resultado", "Usuário criado com sucesso!");

		return modelAndView;
	}
}
