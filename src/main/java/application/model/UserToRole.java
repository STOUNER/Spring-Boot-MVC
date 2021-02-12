package application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "user_to_role")
public class UserToRole {
    @Id
    private Integer id;
    @Column
    private Integer userId;
    @Column
    private Integer roleId;

    public UserToRole() {

    }

    public UserToRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
}
