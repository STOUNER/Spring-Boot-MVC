package application.dto;


import java.util.Objects;

public class RoleDTO {

    private Long id;

    private Long roleId;

    private String roleName;


    public RoleDTO() {
    }

    public RoleDTO(Long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return Objects.equals(id, roleDTO.id) && Objects.equals(roleId, roleDTO.roleId) && Objects.equals(roleName, roleDTO.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleId, roleName);
    }
}
