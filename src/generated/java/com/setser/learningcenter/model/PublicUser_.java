package com.setser.learningcenter.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PublicUser.class)
public abstract class PublicUser_ extends com.setser.learningcenter.model.User_ {

	public static volatile SingularAttribute<PublicUser, String> firstName;
	public static volatile SingularAttribute<PublicUser, String> lastName;
	public static volatile SingularAttribute<PublicUser, String> patronymic;
	public static volatile SingularAttribute<PublicUser, Boolean> displayMail;
	public static volatile SingularAttribute<PublicUser, Boolean> displayName;
	public static volatile SingularAttribute<PublicUser, String> bio;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String PATRONYMIC = "patronymic";
	public static final String DISPLAY_MAIL = "displayMail";
	public static final String DISPLAY_NAME = "displayName";
	public static final String BIO = "bio";

}

