package com.setser.learningcenter.administrator;

import com.setser.learningcenter.course.Course;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Administrator.class)
public abstract class Administrator_ extends com.setser.learningcenter.model.User_ {

	public static volatile SetAttribute<Administrator, Course> courses;

	public static final String COURSES = "courses";

}

