package com.company.login.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.company.login.dto.UsuarioDTO;
import com.company.login.dto.UsuarioSecurityDTO;
import com.company.login.entity.Usuario;
import com.company.login.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws BadCredentialsException,DisabledException {
 
		Usuario usuario = usuarioRepository.findByLogin(login);

		if(usuario == null) {
			throw new BadCredentialsException("Usuário não encontrado no sistema!");
		}
 
		return new UsuarioSecurityDTO(usuario.getLogin(), usuario.getSenha(), new ArrayList<GrantedAuthority>());
	}

	public void salvarUsuario(UsuarioDTO usuarioDTO){
 
		Usuario usuario =  new Usuario();
		usuario.setLogin(usuarioDTO.getLogin());
		usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioDTO.getSenha()));
 
		this.usuarioRepository.save(usuario);
	}	

//	public List<UsuarioDTO> consultarUsuarios(){
// 
//		List<UsuarioDTO> usuariosDTO = new ArrayList<UsuarioDTO>();
// 
//		List<Usuario> usuariosEntity = this.usuarioRepository.findAll();
// 
//		usuariosEntity.forEach(usuario ->{
// 
//			usuariosDTO.add(
//					new UsuarioDTO(usuario.getCodigo(),
//							usuario.getNome(), 
//							usuario.getLogin(), 
//							null, 
//							usuario.isAtivo(),
//							null));
//		});
// 
// 
//		return usuariosDTO;
//	}
 
	public void excluir(Long idUsuario){
		this.usuarioRepository.deleteById(idUsuario);
	}
 
	public UsuarioDTO consultarUsuario(Long idUsuario){
 
		Usuario usuario = this.usuarioRepository.findById(idUsuario).get();
 
		return new UsuarioDTO(usuario.getLogin(), null);
 
	}
 
	public void alterarUsuario(UsuarioDTO usuarioDTO){
 
		Usuario usuario =  this.usuarioRepository.findById(usuarioDTO.getIdUsuario()).get();
 
		usuario.setLogin(usuarioDTO.getLogin());
		if (!StringUtils.isEmpty(usuarioDTO.getSenha())) {
			usuario.setSenha(new BCryptPasswordEncoder().encode(usuarioDTO.getSenha()));
		}			

		this.usuarioRepository.saveAndFlush(usuario);
	}
}