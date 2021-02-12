package application.utill;

import application.dao.UserDao;
import application.model.Role;
import application.model.User;
import application.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.util.Collections;

@Service
public class CreateFirstUsers {
    @Autowired
    private EntityManagerFactory entityManagerFactory;
    private SessionFactory sessionFactory;

    public CreateFirstUsers(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Transactional
    public void CreateFirstUsers() {
        Role roleUser = new Role(1, "ROLE_USER");
        Role roleAdmin = new Role(2, "ROLE_ADMIN");
        User user1 = new User("Denis", "Ivanov", "User@gmail.com", 24, 1, "1224");
        User user2 = new User("Ivan", "Petrov", "Admin@gmail.com", 32, 2, "1224");
        User user3 = new User("Ivan", "Petrov", "Admin@gmail.com", 32, 2, "1224");

        Session session = sessionFactory.openSession();
        session.save(roleUser);
        session.save(roleAdmin);
        session.save(user1);
        session.save(user2);
        session.save(user3);
        session.close();
    }

    @Bean
    public void createSessionFactoryUtil() {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        CreateFirstUsers();
    }
}

