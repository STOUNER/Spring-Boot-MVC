package application.dao;

import application.model.Role;
import application.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserDao {


    public User getUserByName(String login);

    public void save(User user);

    public List<User> findAll();

    public void deleteById(Integer aLong);

    public Optional<User> findById(Integer aLong);
}