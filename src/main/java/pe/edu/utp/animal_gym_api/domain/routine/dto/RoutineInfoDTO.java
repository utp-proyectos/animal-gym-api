package pe.edu.utp.animal_gym_api.domain.routine.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class RoutineInfoDTO {
	private Long id;
	private String name;
	private String description;
	private String goal;
	private LocalDate startDate;
	private LocalDate endDate;
}
