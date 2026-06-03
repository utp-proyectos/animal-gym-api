package pe.edu.utp.animal_gym_api.domain.employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findByDni(String dni);

	@Query("""
			SELECT new pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO(
			    TREAT(u.person AS Employee).id,
			    TREAT(u.person AS Employee).firstName,
			    TREAT(u.person AS Employee).lastName,
			    TREAT(u.person AS Employee).image,
			    u.role
			)
			FROM User u
			WHERE TYPE(u.person) = Employee
			""")
	List<EmployeeResponseDTO> findAllCardEmployees();
}
