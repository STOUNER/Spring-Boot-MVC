package application.dao;

import application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCrudRepository extends JpaRepository<User, Integer>, UserCustomCrudRepository<User> {

}