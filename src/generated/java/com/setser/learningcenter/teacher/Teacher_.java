package com.setser.learningcenter.teacher;

import com.setser.learningcenter.course.Lesson;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Teacher.class)
public abstract class Teacher_ extends com.setser.learningcenter.model.PublicUser_ {

	public static volatile SingularAttribute<Teacher, String> companyName;
	public static volatile SetAttribute<Teacher, Lesson> lessons;

	public static final String COMPANY_NAME = "companyName";
	public static final String LESSONS = "lessons";

}

