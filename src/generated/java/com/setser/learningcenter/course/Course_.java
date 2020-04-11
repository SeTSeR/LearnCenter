package com.setser.learningcenter.course;

import com.setser.learningcenter.administrator.Administrator;
import com.setser.learningcenter.pupil.Pupil;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Course.class)
public abstract class Course_ extends com.setser.learningcenter.model.BaseEntity_ {

	public static volatile SetAttribute<Course, Pupil> pupils;
	public static volatile SingularAttribute<Course, Boolean> isDisplayed;
	public static volatile SingularAttribute<Course, String> description;
	public static volatile SetAttribute<Course, Administrator> admins;
	public static volatile SetAttribute<Course, Lesson> lessons;

	public static final String PUPILS = "pupils";
	public static final String IS_DISPLAYED = "isDisplayed";
	public static final String DESCRIPTION = "description";
	public static final String ADMINS = "admins";
	public static final String LESSONS = "lessons";

}

