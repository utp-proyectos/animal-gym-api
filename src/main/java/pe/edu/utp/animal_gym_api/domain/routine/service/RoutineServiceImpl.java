package pe.edu.utp.animal_gym_api.domain.routine.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.employee.EmployeeRepository;
import pe.edu.utp.animal_gym_api.domain.exercises.Exercise;
import pe.edu.utp.animal_gym_api.domain.exercises.ExerciseRepository;
import pe.edu.utp.animal_gym_api.domain.routine.Routine;
import pe.edu.utp.animal_gym_api.domain.routine.RoutineDetail;
import pe.edu.utp.animal_gym_api.domain.routine.RoutineMapper;
import pe.edu.utp.animal_gym_api.domain.routine.RoutineRepository;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineResponseDTO;

@Service
public class RoutineServiceImpl implements RoutineService {

	@Autowired
	RoutineRepository routineRepository;
	@Autowired
	ExerciseRepository exerciseRepository;
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	RoutineMapper routineMapper;

	@Override
	public List<RoutineResponseDTO> findAll() {
		return routineRepository.findAll().stream()
				.map(routineMapper::toResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public RoutineResponseDTO findById(Long id) {
		return routineRepository.findById(id)
				.map(routineMapper::toResponseDto)
				.orElseThrow(() -> new EntityNotFoundException("Routine not found with ID: " + id));
	}

	@Override
	public RoutineResponseDTO save(Routine routine) {
		if (routine.getEmployee() != null && routine.getEmployee().getId() != null) {
			Employee employee = employeeRepository.findById(routine.getEmployee().getId())
					.orElseThrow(
							() -> new EntityNotFoundException("Employee not found with ID: " + routine.getEmployee().getId()));
			routine.setEmployee(employee);
		}

		// Si es una actualización
		if (routine.getId() != null) {
			Routine existingRoutine = routineRepository.findById(routine.getId())
					.orElseThrow(() -> new EntityNotFoundException("Routine not found with ID: " + routine.getId()));

			// Preservar detalles si la lista viene vacía o null en el request
			if (routine.getRoutineDetails() == null || routine.getRoutineDetails().isEmpty()) {
				routine.setRoutineDetails(existingRoutine.getRoutineDetails());
			}

			// Preservar empleado si no se envió uno nuevo
			if (routine.getEmployee() == null) {
				routine.setEmployee(existingRoutine.getEmployee());
			}
		}

		Routine savedRoutine = routineRepository.save(routine);
		return routineMapper.toResponseDto(savedRoutine);
	}

	@Override
	public void deleteById(Long id) {
		if (!routineRepository.existsById(id)) {
			throw new EntityNotFoundException("Cannot delete: Routine not found with ID: " + id);
		}
		routineRepository.deleteById(id);
	}

	@Override
	public RoutineResponseDTO addRoutineDetail(Long routineId, RoutineDetail detail) {
		Routine routine = routineRepository.findById(routineId)
				.orElseThrow(() -> new EntityNotFoundException("Routine not found with ID: " + routineId));

		// Validar y rescatar el Ejercicio
		if (detail.getExercise() != null && detail.getExercise().getId() != null) {
			Exercise exercise = exerciseRepository.findById(detail.getExercise().getId())
					.orElseThrow(
							() -> new EntityNotFoundException("Exercise not found with ID: " + detail.getExercise().getId()));
			detail.setExercise(exercise);
		}

		routine.getRoutineDetails().add(detail);

		return routineMapper.toResponseDto(routineRepository.save(routine));
	}

	@Override
	public RoutineResponseDTO removeRoutineDetail(Long routineId, Long detailId) {
		Routine routine = routineRepository.findById(routineId)
				.orElseThrow(() -> new EntityNotFoundException("Routine not found with ID: " + routineId));

		boolean removed = routine.getRoutineDetails()
				.removeIf(detail -> detail.getId().equals(detailId));

		if (!removed) {
			throw new EntityNotFoundException("Detail not found with ID " + detailId + " in this routine");
		}

		return routineMapper.toResponseDto(routineRepository.save(routine));
	}
}
