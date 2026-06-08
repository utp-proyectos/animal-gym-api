package pe.edu.utp.animal_gym_api.domain.employee;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDetailDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeUser;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	@Mapping(target = "id", ignore = true)
	Employee toEntity(EmployeeUser dto);

	@Mapping(target = "role", source = "role")
	EmployeeResponseDTO toResponseDto(Employee employee);

	@Mapping(target = "role", source = "role")
	EmployeeResponseDetailDTO toDetailDto(Employee employee);
}
