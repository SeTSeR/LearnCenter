package com.setser.learningcenter.teacher;

import com.setser.learningcenter.db.UserDAO;
import com.setser.learningcenter.course.Lesson;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO extends UserDAO<Teacher, Long> {
    public TeacherDAO(EntityManager manager) {
        super(manager);
    }

    @Override
    public Teacher findUserByMail(String mail) {
        manager.getTransaction().begin();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Teacher> teacherQuery = builder.createQuery(persistentClass);
        Root<Teacher> teacherRoot = teacherQuery.from(persistentClass);
        teacherQuery.select(teacherRoot);
        teacherQuery.where(builder.equal(teacherRoot.get(Teacher_.mail), mail));
        Teacher result = manager.createQuery(teacherQuery).getSingleResult();
        manager.getTransaction().commit();
        return result;
    }

    public List<Teacher> findTeachers(@NotNull String query) {
        manager.getTransaction().begin();
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Teacher> teacherQuery = builder.createQuery(persistentClass);
        Root<Teacher> teacherRoot = teacherQuery.from(persistentClass);
        teacherQuery.select(teacherRoot);
        if (!query.isEmpty()) {
            Expression<String> whereExpr = builder.concat(teacherRoot.get(Teacher_.firstName),
                    builder.concat(" ", teacherRoot.get(Teacher_.lastName)));
            teacherQuery.where(builder.like(whereExpr, "%" + query + "%"));
        }

        List<Teacher> results = manager.createQuery(teacherQuery).getResultList();
        manager.getTransaction().commit();
        return results;
    }

    public List<Lesson> getTimetable(@NotNull Teacher teacher) {
        manager.getTransaction().begin();
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Teacher> teacherQuery = builder.createQuery(persistentClass);
        Root<Teacher> teacherRoot = teacherQuery.from(persistentClass);
        teacherRoot.fetch(Teacher_.lessons);
        teacherQuery.where(builder.equal(teacherRoot.get(Teacher_.id), teacher.getId()));
        Teacher result = manager.createQuery(teacherQuery).getSingleResult();
        manager.getTransaction().commit();
        return new ArrayList<>(result.getLessons());
    }
}
