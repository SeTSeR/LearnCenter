package com.setser.learningcenter.course;

import com.setser.learningcenter.db.GenericDAO;
import com.setser.learningcenter.teacher.Teacher;
import org.hibernate.HibernateException;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;

public class CourseDAO extends GenericDAO<Course, Long> {
    public CourseDAO(EntityManager manager) {
        super(manager);
    }

    public List<Course> findCourses(@NotNull String query, boolean filterShowed) throws HibernateException {
        manager.getTransaction().begin();
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Course> courseQuery = builder.createQuery(persistentClass);
        Root<Course> courseRoot = courseQuery.from(persistentClass);
        courseQuery.select(courseRoot);
        if (!query.isEmpty()) {
            Predicate predicate = builder.like(courseRoot.get(Course_.description), "%" + query + "%");
            if (filterShowed) {
                predicate = builder.and(predicate, builder.equal(courseRoot.get(Course_.isDisplayed), true));
            }
            courseQuery.where(predicate);
        } else {
            if (filterShowed) {
                courseQuery.where(builder.equal(courseRoot.get(Course_.isDisplayed), true));
            }
        }

        List<Course> results = manager.createQuery(courseQuery).getResultList();
        manager.getTransaction().commit();
        return results;
    }

    public List<Teacher> getTeachers(@NotNull Course course) throws HibernateException {
        manager.getTransaction().begin();
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Teacher> courseQuery = builder.createQuery(Teacher.class);
        Root<Course> courseRoot = courseQuery.from(persistentClass);
        Join<Course, Lesson> lessonJoin = courseRoot.join(Course_.lessons);
        Join<Lesson, Teacher> teacherJoin = lessonJoin.join(Lesson_.teacher);
        courseQuery.select(teacherJoin);
        courseQuery.where(builder.equal(courseRoot.get(Course_.id), course.getId()));
        List<Teacher> results = manager.createQuery(courseQuery).getResultList();
        manager.getTransaction().commit();

        return results;
    }
}
