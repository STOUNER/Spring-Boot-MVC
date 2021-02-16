package application.utill;


import application.model.Role;
import application.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;

@Service
@Lazy(value = false)
public class CreateFirstUsers {
    private EntityManagerFactory entityManagerFactory;
    private SessionFactory sessionFactory;

    public CreateFirstUsers(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Transactional
    public void CreateFirstUsers() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Role role1 = new Role("ROLE_USER", 2);
        Role role2 = new Role("ROLE_ADMIN", 1);

        User user1 = new User("Denis", "Ivanov", "User@gmail.com", 24, 1, "1224");
        User user2 = new User("Ivan", "Petrov", "Admin@gmail.com", 32, 2, "1224");

        user1.addRole(role1);
        user2.addRole(role2);
        session.persist(user1);
        session.persist(user2);
        session.flush();
        session.close();


    }

    @Bean
    public void createSessionFactoryUtil() {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        CreateFirstUsers();
    }
}