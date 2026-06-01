package pe.edu.utp.animal_gym_api.domain.employee.service;

import java.util.List;

import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDetailDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeUser;

public interface EmployeeService {
	List<EmployeeResponseDTO> findAll();

	EmployeeResponseDetailDTO findById(Long id);

	EmployeeResponseDTO save(EmployeeUser dto);

	void delete(Long id);
}
