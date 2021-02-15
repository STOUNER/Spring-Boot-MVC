package application.dao;


import application.dto.UserDTO;
import application.model.Role;
import application.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        session.close();
        return user;
    }

    @Override
    public void save(User s) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        //Проверяем, существует ли данный пользователь в таблице.
        Role role = session.load(Role.class, s.getRole_number());
        s.addRole(role);
        session.save(s);
        transaction.commit();
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
        List<User> userList = session.createQuery("FROM User", User.class).getResultList();
        session.close();
        return userList;
    }


    @Override
    public void deleteById(Integer aLong) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, aLong);
        session.delete(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void updateUser(UserDTO oldUser) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, oldUser.getId());
        Role role = session.load(Role.class, oldUser.getRoleId());

        user.getRoleSet().clear();
        user.setName(oldUser.getName());
        user.setLastName(oldUser.getLastName());
        user.setAge(oldUser.getAge());
        user.setEmail(oldUser.getEmail());
        user.addRole(role);

        session.update(user);
        transaction.commit();
        session.close();
    }
}

