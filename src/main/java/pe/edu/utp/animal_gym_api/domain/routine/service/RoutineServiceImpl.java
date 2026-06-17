package pe.edu.utp.animal_gym_api.domain.routine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.employee.EmployeeRepository;
import pe.edu.utp.animal_gym_api.domain.exercises.Exercise;
import pe.edu.utp.animal_gym_api.domain.exercises.ExerciseRepository;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;
import pe.edu.utp.animal_gym_api.domain.partner.PartnerRepository;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRoutinesResponseDTO;
import pe.edu.utp.animal_gym_api.domain.routine.Routine;
import pe.edu.utp.animal_gym_api.domain.routine.RoutineDetail;
import pe.edu.utp.animal_gym_api.domain.routine.RoutineMapper;
import pe.edu.utp.animal_gym_api.domain.routine.RoutineRepository;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineDetailRequestDTO;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineRequestDTO;

@Service
public class RoutineServiceImpl implements RoutineService {

	@Autowired
	RoutineRepository routineRepository;

	@Autowired
	ExerciseRepository exerciseRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	PartnerRepository partnerRepository;

	@Autowired
	RoutineMapper routineMapper;

	@Override
	@Transactional
	public PartnerRoutinesResponseDTO save(RoutineRequestDTO requestDTO) {

		Partner partner = findPartnerById(requestDTO.getPartnerId());
		Employee employee = findEmployeeById(requestDTO.getEmployeeId());

		Routine routine = routineMapper.toEntity(requestDTO);
		routine.setEmployee(employee);
		partner.getRoutines().add(routine);
		routineRepository.save(routine);

		return routineMapper.toRoutinesResponseDTO(partner);
	}

	@Override
	@Transactional
	public PartnerRoutinesResponseDTO update(Long routineId, RoutineRequestDTO requestDTO) {

		Routine routine = routineRepository.findById(routineId)
				.orElseThrow(() -> new EntityNotFoundException("Rutina no encontrada con ID: " + routineId));

		if (requestDTO.getEmployeeId() != null) {
			Employee employee = findEmployeeById(requestDTO.getEmployeeId());
			routine.setEmployee(employee);
		}

		routineMapper.updateEntityFromDto(requestDTO, routine);
		routineRepository.save(routine);

		Partner partner = findPartnerById(requestDTO.getPartnerId());
		return routineMapper.toRoutinesResponseDTO(partner);
	}

	@Override
	@Transactional
	public PartnerRoutinesResponseDTO delete(Long partnerId, Long routineId) {

		Partner partner = findPartnerById(partnerId);

		Routine routineToDelete = partner.getRoutines().stream()
				.filter(routine -> routine.getId().equals(routineId))
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException(
						"La rutina con ID " + routineId + " no pertenece al socio con ID " + partnerId));

		partner.getRoutines().remove(routineToDelete);
		partnerRepository.save(partner);

		return routineMapper.toRoutinesResponseDTO(partner);
	}

	@Override
	@Transactional
	public PartnerRoutinesResponseDTO saveDetail(RoutineDetailRequestDTO detailRequestDTO) {

		Routine routine = routineRepository.findById(detailRequestDTO.getRoutineId())
				.orElseThrow(() -> new EntityNotFoundException(
						"Rutina no encontrada con ID: " + detailRequestDTO.getRoutineId()));

		Exercise exercise = exerciseRepository.findById(detailRequestDTO.getExerciseId())
				.orElseThrow(() -> new EntityNotFoundException(
						"Ejercicio no encontrado con ID: " + detailRequestDTO.getExerciseId()));

		RoutineDetail detail = routineMapper.toDetailEntity(detailRequestDTO);
		detail.setExercise(exercise);

		routine.getRoutineDetails().add(detail);
		routineRepository.save(routine);

		Partner partner = findPartnerById(detailRequestDTO.getPartnerId());

		return routineMapper.toRoutinesResponseDTO(partner);
	}

	@Override
	@Transactional
	public PartnerRoutinesResponseDTO updateDetail(Long detailId, RoutineDetailRequestDTO detailRequestDTO) {

		Routine routine = routineRepository.findById(detailRequestDTO.getRoutineId())
				.orElseThrow(() -> new EntityNotFoundException(
						"Rutina no encontrada con ID: " + detailRequestDTO.getRoutineId()));

		RoutineDetail detailToUpdate = routine.getRoutineDetails().stream()
				.filter(detail -> detail.getId().equals(detailId))
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException(
						"El detalle con ID " + detailId + " no pertenece a la rutina con ID " + detailRequestDTO.getRoutineId()));

		if (detailRequestDTO.getExerciseId() != null) {
			Exercise exercise = exerciseRepository.findById(detailRequestDTO.getExerciseId())
					.orElseThrow(() -> new EntityNotFoundException(
							"Ejercicio no encontrado con ID: " + detailRequestDTO.getExerciseId()));
			detailToUpdate.setExercise(exercise);
		}

		routineMapper.updateDetailEntityFromDto(detailRequestDTO, detailToUpdate);
		routineRepository.save(routine);

		Partner partner = findPartnerById(detailRequestDTO.getPartnerId());
		return routineMapper.toRoutinesResponseDTO(partner);
	}

	@Override
	@Transactional
	public PartnerRoutinesResponseDTO deleteDetail(Long partnerId, Long routineId, Long detailId) {

		Partner partner = findPartnerById(partnerId);

		Routine routine = partner.getRoutines().stream()
				.filter(r -> r.getId().equals(routineId))
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException(
						"La rutina con ID " + routineId + " no pertenece al socio con ID " + partnerId));

		RoutineDetail detailToDelete = routine.getRoutineDetails().stream()
				.filter(detail -> detail.getId().equals(detailId))
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException(
						"El detalle con ID " + detailId + " no existe en la rutina con ID " + routineId));

		routine.getRoutineDetails().remove(detailToDelete);
		routineRepository.save(routine);

		return routineMapper.toRoutinesResponseDTO(partner);
	}

	public Partner findPartnerById(Long partnerId) {
		return partnerRepository.findById(partnerId)
				.orElseThrow(() -> new EntityNotFoundException("Socio no encontrado con ID: " + partnerId));
	}

	public Employee findEmployeeById(Long employeeId) {
		return employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con ID: " + employeeId));
	}

}
