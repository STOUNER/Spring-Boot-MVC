package application.dto;


import application.model.Role;
import application.model.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Transient;
import java.util.*;

public class UserDTO implements UserDetails {


    private Integer id;

    private String name;

    private String LastName;

    private String email;

    private Integer age;

    private Integer roleId;

    private String password;

    private List<RoleDTO> roles;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.LastName = user.getLastName();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.roleId = user.getRole_number();
        this.password = user.getPassword();
        if (user.getRoleSet().size() != 0) {
            this.roles = ParseUserRole(user);
        }

    }


    public UserDTO(Integer id, String name, String lastName, String email, Integer age, Integer roleId, String password, List<RoleDTO> roleList) {
        this.id = id;
        this.name = name;
        this.LastName = lastName;
        this.email = email;
        this.age = age;
        this.roleId = roleId;
        this.password = password;
        this.roles = roleList;
    }
    @JsonCreator
    public UserDTO(Integer id, String name, String lastName, String email, Integer age, Integer roleId, String password) {
        this.id = id;
        this.name = name;
        this.LastName = lastName;
        this.email = email;
        this.age = age;
        this.roleId = roleId;
        this.password = password;
    }

    public List<RoleDTO> ParseUserRole(User user) {
        Set<Role> userRoleSet = user.getRoleSet();
        List<RoleDTO> resultRoleDTOList = new LinkedList<RoleDTO>();
        for (Role role : userRoleSet) {
            resultRoleDTOList.add(new RoleDTO(role.getId(), role.getRoleName(), role.getRoleNumber()));
        }
        return resultRoleDTOList;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
