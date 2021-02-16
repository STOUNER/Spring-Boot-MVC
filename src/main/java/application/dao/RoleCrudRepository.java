package application.dao;

import application.model.Role;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleCrudRepository extends JpaRepository<Role, Integer> {
}
