package com.setser.learningcenter.administrator;

import com.setser.learningcenter.db.UserDAO;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class AdministratorDAO extends UserDAO<Administrator, Long> {
    public AdministratorDAO(EntityManager manager) {
        super(manager);
    }

    @Override
    public Administrator findUserByMail(String mail) {
        manager.getTransaction().begin();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Administrator> adminQuery = builder.createQuery(persistentClass);
        Root<Administrator> adminRoot = adminQuery.from(persistentClass);
        adminRoot.fetch(Administrator_.courses);
        adminQuery.select(adminRoot);
        adminQuery.where(builder.equal(adminRoot.get(Administrator_.mail), mail));
        Administrator admin = manager.createQuery(adminQuery).getSingleResult();
        manager.getTransaction().commit();
        return admin;
    }
}
