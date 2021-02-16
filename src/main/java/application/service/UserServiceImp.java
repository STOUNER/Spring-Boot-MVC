package application.service;

import application.dao.RoleCrudRepository;
import application.dao.UserCrudRepository;
import application.dto.UserDTO;
import application.model.Role;
import application.model.User;
import application.utill.CreateFirstUsers;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@EnableTransactionManagement
public class UserServiceImp implements UserService {
    private UserCrudRepository userCrudRepository;
    private RoleCrudRepository roleCrudRepository;

    public UserServiceImp(UserCrudRepository userCrudRepository, RoleCrudRepository roleCrudRepository, CreateFirstUsers createFirstUsers) {
        this.userCrudRepository = userCrudRepository;
        this.roleCrudRepository = roleCrudRepository;
    }

    @Override
    @Transactional
    public void save(User user) {
        Role role = roleCrudRepository.findById(user.getRole_number()).get();
        user.getRoleSet().add(role);
        userCrudRepository.save(user);
    }

    @Override
    @Transactional
    public void update(User user) {
        Role role = roleCrudRepository.findById(user.getRole_number()).get();
        user.getRoleSet().add(role);
        userCrudRepository.save(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public List<User> findAll() {
        return userCrudRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteUserById(Integer id) {
        userCrudRepository.deleteById(id);
    }

    @Override
    @Transactional
    public User findById(Integer id) {
        Optional<User> resultUserList = userCrudRepository.findById(id);
        return resultUserList.get();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userCrudRepository.getUserByName(s);
        UserDTO resultUserDTO = new UserDTO(user);
        return resultUserDTO;
    }
}
