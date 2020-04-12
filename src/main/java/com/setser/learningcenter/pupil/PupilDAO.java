package com.setser.learningcenter.pupil;

import com.setser.learningcenter.db.UserDAO;
import com.setser.learningcenter.course.Course;
import com.setser.learningcenter.course.Course_;
import com.setser.learningcenter.course.Lesson;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

public class PupilDAO extends UserDAO<Pupil, Long> {
    public PupilDAO(EntityManager manager) {
        super(manager);
    }

    @Override
    public Pupil findUserByMail(String mail) {
        manager.getTransaction().begin();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Pupil> query = builder.createQuery(persistentClass);
        Root<Pupil> pupilRoot = query.from(persistentClass);
        query.select(pupilRoot);
        query.where(builder.equal(pupilRoot.get(Pupil_.mail), mail));
        Pupil result = manager.createQuery(query).getSingleResult();
        manager.getTransaction().commit();
        return result;
    }

    public List<Lesson> getTimetable(Pupil pupil) {
        manager.getTransaction().begin();
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Lesson> query = builder.createQuery(Lesson.class);
        Root<Pupil> pupilRoot = query.from(persistentClass);
        Join<Pupil, Course> joinCourse = pupilRoot.join(Pupil_.courses);
        Join<Course, Lesson> joinLessons = joinCourse.join(Course_.lessons);
        query.select(joinLessons);
        query.where(builder.equal(pupilRoot.get(Pupil_.id), pupil.getId()));
        List<Lesson> results = manager.createQuery(query).getResultList();

        manager.getTransaction().commit();
        return results;
    }
}
