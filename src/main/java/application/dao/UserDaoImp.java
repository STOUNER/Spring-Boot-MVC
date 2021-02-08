package application.dao;


import application.model.Role;
import application.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    private SessionFactory sessionFactory;

    @Bean
    @PostConstruct
    public void createSessionFactory() {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Override
    public User getUserByName(String login) {
        Session session = sessionFactory.openSession();
        User user = (User) session.createQuery("FROM User u where u.name = :login").setParameter("login", login).getSingleResult();
        return user;
    }

    @Override
    public <S extends User> S save(S s) {
        Session session = sessionFactory.openSession();
        //Проверяем, существует ли данный пользователь в таблице.
        User user = session.load(User.class, s.getId());
        if (user == null) {
            session.save(s);
            session.close();
        } else {
            user.setAge(s.getAge());
            user.setName(s.getName());
        }
        session.close();
        return s;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<User> findById(Long aLong) {
        Session session = sessionFactory.openSession();
        Optional<User> user = Optional.of(session.load(User.class, aLong));
        session.close();
        return user;
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<User> findAll() {
        Session session = sessionFactory.openSession();
        return session.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public List<User> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }


    @Override
    public void deleteById(Long aLong) {
        Session session = sessionFactory.openSession();
        User user = session.load(User.class, aLong);
        session.delete(user);
        session.close();
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {

    }

    @Override
    public void deleteAll() {

    }


}

