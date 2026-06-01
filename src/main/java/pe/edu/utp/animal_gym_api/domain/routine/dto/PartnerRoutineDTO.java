package pe.edu.utp.animal_gym_api.domain.routine.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRoutineDTO {
	private Long partnerId;
	private String firstName;
	private String lastName;
	private String dni;
	// Esto es para las "etiquetas" azules y verdes de la tabla
	private List<RoutineSummaryDTO> routines;
}
