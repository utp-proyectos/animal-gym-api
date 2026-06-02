package pe.edu.utp.animal_gym_api.domain.routine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineDetailResponseDTO {
	private Long id;
	private String dayOfWeek;
	private Integer sets;
	private Integer reps;
	private Double weight;
	private Integer restTime;

	private String exerciseName;
	private String muscleGroup;
	private String equipment;
}
