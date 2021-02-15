package application.service;

import application.dao.UserDao;
import application.dto.UserDTO;
import application.model.Role;
import application.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@EnableTransactionManagement
public class UserServiceImp implements UserService {
    private UserDao userDao;

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void update(UserDTO userDTO) {
        userDao.updateUser(userDTO);
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void deleteUserById(Integer id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional
    public User findById(Integer id) {
        User user = userDao.findById(id).get();
        return user;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.getUserByName(s);
    }
}
