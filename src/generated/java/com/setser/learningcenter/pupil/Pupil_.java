package com.setser.learningcenter.pupil;

import com.setser.learningcenter.course.Course;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pupil.class)
public abstract class Pupil_ extends com.setser.learningcenter.model.PublicUser_ {

	public static volatile SetAttribute<Pupil, Course> courses;

	public static final String COURSES = "courses";

}

