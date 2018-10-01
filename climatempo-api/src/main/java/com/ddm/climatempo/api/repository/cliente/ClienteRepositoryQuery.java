package com.ddm.climatempo.api.repository.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ddm.climatempo.api.model.Cliente;
import com.ddm.climatempo.api.repository.filter.ClienteFilter;
import com.ddm.climatempo.api.repository.projection.ResumoCliente;

public interface ClienteRepositoryQuery {
	
	public Page<Cliente> filtrar(ClienteFilter clienteFilter, Pageable pageable);
	public Page<ResumoCliente> resumir (ClienteFilter clienteFilter, Pageable pageable);

}
