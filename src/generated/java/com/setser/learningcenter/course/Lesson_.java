package com.setser.learningcenter.course;

import com.setser.learningcenter.teacher.Teacher;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Lesson.class)
public abstract class Lesson_ extends com.setser.learningcenter.model.BaseEntity_ {

	public static volatile SingularAttribute<Lesson, Teacher> teacher;
	public static volatile SingularAttribute<Lesson, String> description;
	public static volatile SingularAttribute<Lesson, Course> course;
	public static volatile SingularAttribute<Lesson, LocalDateTime> lessonTime;

	public static final String TEACHER = "teacher";
	public static final String DESCRIPTION = "description";
	public static final String COURSE = "course";
	public static final String LESSON_TIME = "lessonTime";

}

