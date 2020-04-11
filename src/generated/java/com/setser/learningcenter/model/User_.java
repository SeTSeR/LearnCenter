package com.setser.learningcenter.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.setser.learningcenter.model.BaseEntity_ {

	public static volatile SingularAttribute<User, String> mail;
	public static volatile SingularAttribute<User, String> passHash;

	public static final String MAIL = "mail";
	public static final String PASS_HASH = "passHash";

}

