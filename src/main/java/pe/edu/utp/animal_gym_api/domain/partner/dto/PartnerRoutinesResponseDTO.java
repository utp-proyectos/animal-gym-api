package pe.edu.utp.animal_gym_api.domain.partner.dto;

import java.time.LocalDate;
import java.util.List;

import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;

public record PartnerRoutinesResponseDTO(
		Long id,
		String firstName,
		String lastName,
		List<RoutineInfo> routines) {

	public record RoutineInfo(
			Long id,
			String name,
			String description,
			String goal,
			LocalDate startDate,
			LocalDate endDate,
			EmployeeResponseDTO employee,
			List<DetailInfo> routineDetails) {
	}

	public record DetailInfo(
			Long id,
			String dayOfWeek,
			Integer sets,
			Integer reps,
			double weight,
			Integer calories,
			Integer restTime,
			ExerciseInfo exercise) {
	}

	public record ExerciseInfo(
			Long id,
			String name,
			String description,
			String muscleGroup,
			String equipment) {
	}
}
