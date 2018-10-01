package com.ddm.climatempo.api.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ddm.climatempo.api.model.Cliente;
import com.ddm.climatempo.api.repository.ClienteRepository;

@Service
public class AppUserDetailsService implements UserDetailsService{

	@Autowired
	private ClienteRepository clienteRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<Cliente> clienteOptional = clienteRepository.findByEmail(email);
		
		
		if (clienteOptional.isPresent()) {
			Cliente cliente = clienteOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
			System.out.println(cliente.getNm_Cliente());
			return new UsuarioClienteSistema(cliente, getPermissoesCliente(cliente));
		}
		
		Cliente cliente = clienteOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
		return new UsuarioClienteSistema(cliente, getPermissoesCliente(cliente));
	}

	private Collection<? extends GrantedAuthority> getPermissoesCliente(Cliente cliente) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		cliente.getPermissoes().forEach(p -> authorities.add(new SimpleGrantedAuthority(p.getDescricao().toUpperCase())));
		return authorities;
	}
	
}
