package com.ddm.climatempo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ddm.climatempo.api.model.Cidade;
import com.ddm.climatempo.api.repository.cidade.CidadeRepositoryQuery;

public interface CidadeRepository extends JpaRepository<Cidade, Long>, CidadeRepositoryQuery{

}
