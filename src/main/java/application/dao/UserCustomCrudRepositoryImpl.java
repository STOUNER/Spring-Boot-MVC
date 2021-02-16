package application.dao;

import application.model.User;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserCustomCrudRepositoryImpl implements UserCustomCrudRepository {

    private EntityManager em;

    public UserCustomCrudRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public User getUserByName(String login) {

        User user = (User) em.createQuery("FROM User u where u.name = :login").setParameter("login", login).getSingleResult();
        return user;
    }
}
