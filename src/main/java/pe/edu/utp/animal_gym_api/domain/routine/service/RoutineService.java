package pe.edu.utp.animal_gym_api.domain.routine.service;

import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRoutinesResponseDTO;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineDetailRequestDTO;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineRequestDTO;

public interface RoutineService {

	PartnerRoutinesResponseDTO save(RoutineRequestDTO requestDTO);

	PartnerRoutinesResponseDTO update(Long routineId, RoutineRequestDTO requestDTO);

	PartnerRoutinesResponseDTO delete(Long partnerId, Long routineId);

	PartnerRoutinesResponseDTO saveDetail(RoutineDetailRequestDTO detailRequestDTO);

	PartnerRoutinesResponseDTO updateDetail(Long detailId, RoutineDetailRequestDTO detailRequestDTO);

	PartnerRoutinesResponseDTO deleteDetail(Long partnerId, Long routineId, Long detailId);
}
