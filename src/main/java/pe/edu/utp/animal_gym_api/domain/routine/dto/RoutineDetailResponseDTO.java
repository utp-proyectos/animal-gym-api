package pe.edu.utp.animal_gym_api.domain.routine.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutineDetailResponseDTO {
	// Parte izquierda: Tabla de rutinas
	private List<RoutineInfoDTO> routineList;

	// Parte derecha: Detalle del ejercicio seleccionado
	// Representa el acordeón azul "Extensión de tríceps — Lunes"
	private List<WorkoutDetailDTO> workoutDetails;
}
