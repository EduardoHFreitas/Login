package com.company.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.login.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByLogin(String login);

}
