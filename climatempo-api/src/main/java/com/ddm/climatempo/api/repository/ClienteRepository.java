package com.ddm.climatempo.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddm.climatempo.api.model.Cliente;
import com.ddm.climatempo.api.repository.cliente.ClienteRepositoryQuery;


public interface ClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepositoryQuery{
	
	public Optional<Cliente> findByEmail(String email);
	
}

