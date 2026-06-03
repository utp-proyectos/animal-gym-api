package pe.edu.utp.animal_gym_api.domain.employee;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pe.edu.utp.animal_gym_api.common.enums.Role;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDetailDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeUser;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
	@Mapping(target = "id", ignore = true)
	Employee toEntity(EmployeeUser dto);

	@Mapping(target = "role", source = "role")
	EmployeeResponseDTO toResponseDto(Employee employee, Role role);

	@Mapping(target = "role", source = "role")
	@Mapping(target = "id", source = "employee.id")
	@Mapping(target = "dni", source = "employee.dni")
	@Mapping(target = "firstName", source = "employee.firstName")
	@Mapping(target = "lastName", source = "employee.lastName")
	@Mapping(target = "phoneNumber", source = "employee.phoneNumber")
	@Mapping(target = "gender", source = "employee.gender")
	@Mapping(target = "email", source = "employee.email")
	@Mapping(target = "birthDate", source = "employee.birthDate")
	@Mapping(target = "hireDate", source = "employee.hireDate")
	@Mapping(target = "image", source = "employee.image")
	@Mapping(target = "salary", source = "employee.salary")
	@Mapping(target = "contractType", source = "employee.contractType")
	@Mapping(target = "specialty", source = "employee.specialty")
	EmployeeResponseDetailDTO toDetailDto(Employee employee, Role role);
}
