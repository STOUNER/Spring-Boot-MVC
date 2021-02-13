package application.dto;

public class RoleDTO {
    private Integer id;
    private String roleName;
    private Integer roleNumber;

    public RoleDTO(Integer id, String roleName, Integer roleNumber) {
        this.id = id;
        this.roleName = roleName;
        this.roleNumber = roleNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
