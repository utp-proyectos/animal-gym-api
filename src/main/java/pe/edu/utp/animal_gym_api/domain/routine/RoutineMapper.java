package pe.edu.utp.animal_gym_api.domain.routine;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineCardDTO;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineDetailResponseDTO;

@Mapper(componentModel = "spring")
public interface RoutineMapper {

	// --- ENTITY -> DTO (Respuestas) ---

	@Mapping(source = "employee.firstName", target = "coachName")
	@Mapping(source = "employee.lastName", target = "coachLastName")
	RoutineCardDTO toCardDto(Routine entity);

	@Mapping(source = "employee", target = "employee")
	@Mapping(source = "routineDetails", target = "details")
	RoutineDetailResponseDTO toDetailDto(Routine entity);

	@Mapping(source = "exercise.name", target = "exerciseName")
	@Mapping(source = "exercise.muscleGroup", target = "muscleGroup")
	@Mapping(source = "exercise.equipment", target = "equipment")
	RoutineDetailResponseDTO toDetailResponseDto(RoutineDetail entity);

	// --- MÉTODOS DE APOYO ---
	EmployeeResponseDTO toEmployeeSummary(Employee employee);
}
