package com.ddm.climatempo.api.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ddm.climatempo.api.event.RecursoCriadoEvent;
import com.ddm.climatempo.api.model.Cliente;
import com.ddm.climatempo.api.repository.ClienteRepository;
import com.ddm.climatempo.api.repository.filter.ClienteFilter;
import com.ddm.climatempo.api.repository.projection.ResumoCliente;
import com.ddm.climatempo.api.service.ClienteService;

@RestController //meta dados. converte json em objeto
@RequestMapping ("/cliente")
public class ClienteResource {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_CONSULTAR_CLIENTE') and #oauth2.hasScope('read')")
	public Page<Cliente> pesquisar(ClienteFilter clienteFilter, Pageable pageable) {
		return clienteRepository.filtrar(clienteFilter, pageable);
	}
	
	//parametro para distinguir qual método getmapping usar, se é pesquisar ou resumir
		@GetMapping(params = "resumo")
		@PreAuthorize("hasAuthority('ROLE_CONSULTAR_CLIENTE') and #oauth2.hasScope('read')")
		public Page<ResumoCliente> resumir(ClienteFilter clienteFilter, Pageable pageable) {
			return clienteRepository.resumir(clienteFilter, pageable);
		}
	
	
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CLIENTE') and #oauth2.hasScope('write')")
	public ResponseEntity<Cliente> criar(@Valid @RequestBody Cliente cliente, HttpServletResponse response) {
		Cliente clienteSalvo = clienteRepository.save(cliente); //alterar para salvar ao invés de save. Verificar AgendamentoResouce
		//o this significa que na classe recursoCriadoEvent o "source" que recebe por parametro é passado no this, ou seja quem chama
		publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteSalvo.getCd_Cliente()));
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
		//retornará o status 201 - created no html
		
	}
	
	@GetMapping("/{cd_Cliente}")
	@PreAuthorize("hasAuthority('ROLE_CONSULTAR_CLIENTE') and #oauth2.hasScope('read')")
	public ResponseEntity<Cliente> buscarPeloCodigo (@PathVariable Long cd_Cliente) {
		System.out.println("Entrou buscar cliente por codigo");
		Cliente cliente = clienteRepository.findOne(cd_Cliente);
		return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{cd_Cliente}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_CLIENTE') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT) //retornará codigo 204, significa que efetuou com sucesso porém nao existe retorno
	public void remover(@PathVariable Long cd_Cliente) {
		clienteRepository.delete(cd_Cliente);
		
	}
	
	@PutMapping("/{cd_Cliente}")
	@PreAuthorize("hasAuthority('ROLE_EDITAR_CLIENTE') and #oauth2.hasScope('write')")
	public ResponseEntity<Cliente> atualizar(@PathVariable Long cd_Cliente, @Valid @RequestBody Cliente cliente) {
		Cliente clienteSalvo = clienteService.atualizar(cd_Cliente, cliente);
		return ResponseEntity.ok(clienteSalvo);
		
	}
	
	
	@PutMapping("/{cd_Cliente}/ativo")
	@PreAuthorize("hasAuthority('ROLE_EDITAR_CLIENTE') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarAtivo(@PathVariable long cd_Cliente, @RequestBody Boolean ativo) {
		clienteService.atualizarAtivo(cd_Cliente, ativo);
		
	}

}
