package application.service;

import application.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    public void save(User user);

    public List<User> findAll();

    public void deleteUserById(Long id);

    public User findById(Long id);



    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}