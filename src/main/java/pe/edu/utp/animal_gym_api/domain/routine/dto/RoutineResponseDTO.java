package pe.edu.utp.animal_gym_api.domain.routine.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineResponseDTO {
	private Long id;
	private String name;
	private String description;
	private String goal;
	private LocalDate startDate;
	private LocalDate endDate;

	private EmployeeResponseDTO employee;
	private List<RoutineDetailResponseDTO> details;
}
