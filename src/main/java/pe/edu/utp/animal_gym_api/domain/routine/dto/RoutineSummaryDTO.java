package pe.edu.utp.animal_gym_api.domain.routine.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineSummaryDTO {
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
}
