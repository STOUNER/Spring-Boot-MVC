package application.dao;

import application.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudRepository<User, Long> {
    @Override
    <S extends User> S save(S s);

    @Override
    <S extends User> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    Optional<User> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    List<User> findAll();

    //Вспоминаем ковариантность и нам становится стыдно
    @Override
    List<User> findAllById(Iterable<Long> iterable);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(User user);

    @Override
    void deleteAll(Iterable<? extends User> iterable);

    @Override
    void deleteAll();

    public User getUserByName(String login);
}