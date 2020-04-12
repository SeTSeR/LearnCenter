package com.setser.learningcenter.course;

import com.setser.learningcenter.db.GenericDAO;

import javax.persistence.EntityManager;

public class LessonDAO extends GenericDAO<Lesson, Long> {
    public LessonDAO(EntityManager manager) {
        super(manager);
    }
}
