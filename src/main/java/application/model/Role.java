package application.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.*;

//Role
@Entity
@Table(name = "Role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_number")
    private Integer roleNumber;
    @ManyToMany(mappedBy = "roleSet", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<User> users = new HashSet<>() {
    };

    public Role() {
    }

    public Role(String roleName, Integer roleNumber) {
        this.roleName = roleName;
        this.roleNumber = roleNumber;
    }


    @Override
    public String getAuthority() {
        return roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleNumber() {
        return roleNumber;
    }

    public void setRoleNumber(Integer roleNumber) {
        this.roleNumber = roleNumber;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(roleName, role.roleName) && Objects.equals(roleNumber, role.roleNumber) && Objects.equals(users, role.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName, roleNumber);
    }
}

