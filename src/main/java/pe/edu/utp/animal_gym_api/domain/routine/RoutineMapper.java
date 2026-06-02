package pe.edu.utp.animal_gym_api.domain.routine;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineDetailResponseDTO;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineResponseDTO;

@Mapper(componentModel = "spring")
public interface RoutineMapper {

	@Mapping(source = "routineDetails", target = "details")
	RoutineResponseDTO toResponseDto(Routine entity);

	@Mapping(source = "exercise.name", target = "exerciseName")
	@Mapping(source = "exercise.muscleGroup", target = "muscleGroup")
	@Mapping(source = "exercise.equipment", target = "equipment")
	RoutineDetailResponseDTO toDetailResponseDto(RoutineDetail entity);

	@Mapping(target = "role", ignore = true)
	EmployeeResponseDTO toResponseEmployeeDto(Employee employee);
}
