package application.dto;


import application.model.Role;
import org.hibernate.mapping.Set;

import java.util.HashSet;
import java.util.List;

public class UserDTO {


    private Integer id;

    private String name;

    private String LastName;

    private String email;

    private Integer age;

    private Integer roleId;

    private String password;

    private List<RoleDTO> roles;

    public UserDTO() {
    }

    public UserDTO(Integer id, String name, String lastName, String email, Integer age, Integer roleId, String password, List<RoleDTO> roleList) {
        this.name = name;
        this.LastName = lastName;
        this.email = email;
        this.age = age;
        this.roleId = roleId;
        this.password = password;
        this.roles = roleList;
    }

    public UserDTO(Long id, String name, String lastName, String email, Integer age, Integer roleId, String password) {
        this.name = name;
        this.LastName = lastName;
        this.email = email;
        this.age = age;
        this.roleId = roleId;
        this.password = password;
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
}
