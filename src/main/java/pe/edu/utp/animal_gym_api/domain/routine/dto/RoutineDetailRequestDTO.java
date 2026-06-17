package pe.edu.utp.animal_gym_api.domain.routine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutineDetailRequestDTO {
	private String dayOfWeek;
	private Integer sets;
	private Integer reps;
	private double weight;
	private Integer calories;
	private Integer restTime;
	private Long exerciseId;
	private Long routineId;
	private Long partnerId;
}
