package com.company.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.company.login.entity.Usuario;

@Component
@Transactional
public interface MainRepository extends JpaRepository<Usuario, Long> {

	Usuario findByLogin(String login);
	
}
