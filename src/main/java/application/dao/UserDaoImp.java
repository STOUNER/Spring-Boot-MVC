package application.dao;


import application.model.Role;
import application.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {
    private EntityManagerFactory entityManagerFactory;
    private SessionFactory sessionFactory;

    public UserDaoImp(EntityManagerFactory entityManagerFactory, SessionFactory sessionFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getUserByName(String login) {
        Session session = sessionFactory.openSession();
        User user = (User) session.createQuery("FROM User u where u.name = :login").setParameter("login", login).getSingleResult();
        return user;
    }

    @Override
    public void save(User s) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        //Проверяем, существует ли данный пользователь в таблице.
        if (s.getId() == null) {
            Role role = session.load(Role.class, s.getRole_number());
            s.addRole(role);
            session.save(s);
            session.flush();
        } else {
            User user = session.get(User.class, s.getId());
            user.setAge(s.getAge());
            user.setName(s.getName());
        }
        session.close();
    }

    @Override
    public Optional<User> findById(Integer aLong) {
        Session session = sessionFactory.openSession();
        Optional<User> user = Optional.of(session.load(User.class, aLong));
        session.close();
        return user;
    }


    @Override
    public List<User> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM User", User.class).getResultList();
    }


    @Override
    public void deleteById(Integer aLong) {
        Session session = sessionFactory.openSession();
        User user = session.load(User.class, aLong);
        session.delete(user);
        session.close();
    }

}

