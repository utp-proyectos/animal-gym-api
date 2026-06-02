package pe.edu.utp.animal_gym_api.domain.routine.service;

import java.util.List;

import pe.edu.utp.animal_gym_api.domain.routine.Routine;
import pe.edu.utp.animal_gym_api.domain.routine.RoutineDetail;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineResponseDTO;

public interface RoutineService {
	List<RoutineResponseDTO> findAll();

	RoutineResponseDTO findById(Long id);

	RoutineResponseDTO save(Routine routine);

	void deleteById(Long id);

	// Métodos para el detalle (ejercicios dentro de la rutina)
	RoutineResponseDTO addRoutineDetail(Long routineId, RoutineDetail detail);

	RoutineResponseDTO removeRoutineDetail(Long routineId, Long detailId);
}
