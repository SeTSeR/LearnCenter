package com.setser.learningcenter.administrator;

import com.setser.learningcenter.course.Course;
import com.setser.learningcenter.course.Course_;
import com.setser.learningcenter.db.UserDAO;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

public class AdministratorDAO extends UserDAO<Administrator, Long> {
    public AdministratorDAO(EntityManager manager) {
        super(manager);
    }

    @Override
    public Administrator getById(Long id) {
        manager.getTransaction().begin();
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Administrator> adminQuery = builder.createQuery(persistentClass);
        Root<Administrator> adminRoot = adminQuery.from(persistentClass);
        adminRoot.fetch(Administrator_.courses, JoinType.LEFT);
        adminQuery.select(adminRoot);
        adminQuery.where(builder.equal(adminRoot.get(Administrator_.id), id));
        Administrator result = manager.createQuery(adminQuery).getSingleResult();
        manager.getTransaction().commit();
        return result;
    }

    @Override
    public Administrator findUserByMail(String mail) {
        manager.getTransaction().begin();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Administrator> adminQuery = builder.createQuery(persistentClass);
        Root<Administrator> adminRoot = adminQuery.from(persistentClass);
        adminRoot.fetch(Administrator_.courses, JoinType.LEFT);
        adminQuery.select(adminRoot);
        adminQuery.where(builder.equal(adminRoot.get(Administrator_.mail), mail));
        Administrator admin = manager.createQuery(adminQuery).getSingleResult();
        manager.getTransaction().commit();
        return admin;
    }
}