package com.ddm.climatempo.api.repository.cidade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.ddm.climatempo.api.model.Cidade;
import com.ddm.climatempo.api.model.Cidade_;
import com.ddm.climatempo.api.repository.filter.CidadeFilter;
import com.ddm.climatempo.api.repository.projection.ResumoCidade;


public class CidadeRepositoryImpl implements CidadeRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public Page<Cidade> filtrar(CidadeFilter cidadeFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Cidade> criteria = builder.createQuery(Cidade.class);
		Root<Cidade> root = criteria.from(Cidade.class);

		Predicate[] predicates = criarRestricoes(cidadeFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<Cidade> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(cidadeFilter));
		
	}
	
	
	@Override
	public Page<ResumoCidade> resumir(CidadeFilter cidadeFilter, Pageable pageable) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<ResumoCidade> criteria = builder.createQuery(ResumoCidade.class);
		Root<Cidade> root = criteria.from(Cidade.class);
		
		criteria.select(builder.construct(ResumoCidade.class 
				, root.get(Cidade_.cd_Cidade)
				, root.get(Cidade_.nm_Cidade)
				, root.get(Cidade_.nm_Pais)
				, root.get(Cidade_.sg_Pais)));


		Predicate[] predicates = criarRestricoes(cidadeFilter, builder, root);
		criteria.where(predicates);

		TypedQuery<ResumoCidade> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);

		return new PageImpl<>(query.getResultList(), pageable, total(cidadeFilter));
	}
	
	
	private Predicate[] criarRestricoes(CidadeFilter cidadeFilter, CriteriaBuilder builder,
			Root<Cidade> root) {
		List<Predicate> predicates = new ArrayList<>();

		if(!StringUtils.isEmpty(cidadeFilter.getNomeCidade())) {
			predicates
					.add(builder.like(
							builder.lower(root.get(Cidade_.nm_Cidade)), "%" + cidadeFilter.getNomeCidade().toLowerCase() + "%"));
		}
		
		if(!StringUtils.isEmpty(cidadeFilter.getNomePais())) {
			predicates
					.add(builder.like(
							builder.lower(root.get(Cidade_.nm_Pais)), "%" + cidadeFilter.getNomePais().toLowerCase() + "%"));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}
	
	private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
		
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Long total(CidadeFilter cidadeFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Cidade> root = criteria.from(Cidade.class);
		
		Predicate [] predicates = criarRestricoes(cidadeFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
}
