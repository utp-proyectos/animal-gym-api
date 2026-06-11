package pe.edu.utp.animal_gym_api.domain.employee.service;

import java.io.IOException;
import java.util.List;

import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDetailDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeUser;

public interface EmployeeService {
	List<EmployeeResponseDTO> findAll();

	EmployeeResponseDetailDTO findById(Long id);

	EmployeeResponseDTO save(EmployeeUser dto) throws IOException;

	void delete(Long id);

	EmployeeResponseDTO update(Long id, EmployeeUser dto) throws IOException;
}
