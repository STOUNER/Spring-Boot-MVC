package application.dto;

import application.model.Role;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;

public class UserDTO {


    private Long id;

    private String name;

    private String LastName;

    private String email;

    private Integer age;

    private Integer roleId;

    private String password;

    public UserDTO() {
    }

    public UserDTO(String name, String lastName, String email, Integer age, Integer roleId, String password) {
        this.name = name;
        this.LastName = lastName;
        this.email = email;
        this.age = age;
        this.roleId = roleId;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
