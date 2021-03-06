package application.service;

import application.dto.UserDTO;
import application.model.Role;
import application.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    public void save(User user);

    public void update(User user);

    public List<User> findAll();

    public void deleteUserById(Integer id);

    public User findById(Integer id);


    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}