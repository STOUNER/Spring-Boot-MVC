package application.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "User")
public class User implements UserDetails  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Column
    private String LastName;

    @Column
    private String email;

    @Column
    private Integer age;

    @Column(name = "roleNumber")
    private Integer role_number;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Role> roleSet = new HashSet<>(0);

    @Column
    private String password;

    public User() {
    }

    public User(String name, String lastName, String email, Integer age, Integer role_number, String password) {
        this.name = name;
        this.LastName = lastName;
        this.email = email;
        this.age = age;
        this.role_number = role_number;
        this.password = password;
    }

    public User(Integer id, String name, String lastName, String email, Integer age, Integer role_number, String password) {
        this.id = id;
        this.name = name;
        this.LastName = lastName;
        this.email = email;
        this.age = age;
        this.role_number = role_number;
        this.password = password;
    }


    public void addRole(Role role) {
        roleSet.add(role);
        role.getUsers().add(this);
    }

    public void removeAddress(Role role) {
        roleSet.remove(role);
        role.getUsers().remove(this);
    }


    public void setRoleSet(Set<Role> roles) {
        this.roleSet = roles;
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

    public Integer getRole_number() {
        return role_number;
    }

    public void setRole_number(Integer roleId) {
        this.role_number = roleId;
    }

    public Set<Role> getRoleSet() {
        return roleSet;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonDeserialize(as = SimpleGrantedAuthority.class)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleSet;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(LastName, user.LastName) && Objects.equals(email, user.email) && Objects.equals(age, user.age) && Objects.equals(role_number, user.role_number) && Objects.equals(roleSet, user.roleSet) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, LastName, email, age, role_number, password);
    }
}