package pe.edu.utp.animal_gym_api.domain.employee;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findByDni(String dni);
}
