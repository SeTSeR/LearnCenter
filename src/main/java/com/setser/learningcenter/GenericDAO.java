package com.setser.learningcenter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class GenericDAO<T, ID extends Serializable> implements AutoCloseable {
    protected Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    protected EntityManager manager;

    public GenericDAO(EntityManager manager) {
        this.manager = manager;
    }

    public T getById(ID id) {
        manager.getTransaction().begin();
        T result = manager.find(persistentClass, id);
        manager.getTransaction().commit();
        return result;
    }

    public List<T> getAll() {
        manager.getTransaction().begin();
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<T> query = builder.createQuery(persistentClass);
        query.select(query.from(persistentClass));
        List<T> results = manager.createQuery(query).getResultList();
        manager.getTransaction().commit();
        return results;
    }

    public void delete(T t) {
        manager.getTransaction().begin();
        manager.remove(manager.contains(t) ? t : manager.merge(t));
        manager.getTransaction().commit();
    }

    public void update(T t) {
        manager.getTransaction().begin();
        manager.merge(t);
        manager.getTransaction().commit();
    }

    public void save(T t) {
        manager.getTransaction().begin();
        manager.persist(t);
        manager.getTransaction().commit();
    }

    public void close() {
        manager.close();
    }
}
