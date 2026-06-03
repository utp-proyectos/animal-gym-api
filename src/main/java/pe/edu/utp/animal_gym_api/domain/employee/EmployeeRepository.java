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

	// @Query("SELECT new
	// pe.edu.utp.animal_gym_api.dto.employee.DtoCardEmployee(e.firstName,
	// e.lastName, u.role) " +
	// "FROM Employee e JOIN User u ON u.person.id = e.id")
	// List<EmployeeResponseDTO> findAllCardEmployees();
}
