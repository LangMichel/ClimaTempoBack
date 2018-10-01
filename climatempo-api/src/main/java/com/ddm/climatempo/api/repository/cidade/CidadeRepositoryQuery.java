package com.ddm.climatempo.api.repository.cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ddm.climatempo.api.model.Cidade;
import com.ddm.climatempo.api.repository.filter.CidadeFilter;
import com.ddm.climatempo.api.repository.projection.ResumoCidade;

public interface CidadeRepositoryQuery {
	
	public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable);	
	public Page<ResumoCidade> resumir (CidadeFilter cidadeFilter, Pageable pageable);

}
