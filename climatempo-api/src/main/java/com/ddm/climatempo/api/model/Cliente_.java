package com.ddm.climatempo.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Cliente.class)
public abstract class Cliente_ {

	public static volatile ListAttribute<Cliente, Permissao> permissoes;
	public static volatile SingularAttribute<Cliente, Long> cd_Cliente;
	public static volatile SingularAttribute<Cliente, String> ds_Senha;
	public static volatile SingularAttribute<Cliente, String> email;
	public static volatile SingularAttribute<Cliente, String> nm_Cliente;

}

