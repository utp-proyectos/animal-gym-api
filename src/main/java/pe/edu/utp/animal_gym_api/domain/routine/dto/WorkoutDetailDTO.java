package pe.edu.utp.animal_gym_api.domain.routine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutDetailDTO {
	private Long id;
	private String exerciseName;
	private String dayOfWeek;
	private String muscleGroup;
	private String equipment;

	// Métricas del entrenamiento
	private Integer sets;
	private Integer reps;
	private Double weight;
	private Integer calories;
	private Integer restTime;
}
