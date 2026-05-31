package pe.edu.utp.animal_gym_api.domain.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	@Query("SELECT e FROM Empleado e WHERE LOWER(e.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
	List<Employee> buscarPorNombre(@Param("nombre") String nombre);

	Optional<Employee> findByDni(String dni);
}
