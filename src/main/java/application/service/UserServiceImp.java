package application.service;

import application.dao.UserDao;
import application.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private final UserDao userDao;

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional
    public User findById(Long id) {
        User user = userDao.findById(id).get();
        return user;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.getUserByName(s);
    }

}
