package com.setser.learningcenter.course;

import com.setser.learningcenter.GenericDAO;

import javax.persistence.EntityManager;

public class LessonDAO extends GenericDAO<Lesson, Long> {
    public LessonDAO(EntityManager manager) {
        super(manager);
    }
}
