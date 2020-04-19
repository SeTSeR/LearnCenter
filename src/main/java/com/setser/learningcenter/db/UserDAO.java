package com.setser.learningcenter.db;

import com.setser.learningcenter.model.User;

import javax.persistence.EntityManager;
import java.io.Serializable;

public abstract class UserDAO<T extends User, ID extends Serializable> extends GenericDAO<T, ID> {
    public UserDAO(EntityManager manager) {
        super(manager);
    }

    public abstract T findUserByMail(String mail);
}
