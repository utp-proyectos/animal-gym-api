package pe.edu.utp.animal_gym_api.domain.routine;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import pe.edu.utp.animal_gym_api.domain.exercises.Exercise;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRoutinesResponseDTO;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineDetailRequestDTO;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineRequestDTO;

@Mapper(componentModel = "spring")
public interface RoutineMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "employee", ignore = true)
	@Mapping(target = "routineDetails", ignore = true)
	Routine toEntity(RoutineRequestDTO dto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "employee", ignore = true)
	@Mapping(target = "routineDetails", ignore = true)
	void updateEntityFromDto(RoutineRequestDTO dto, @MappingTarget Routine routine);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "exercise", ignore = true)
	RoutineDetail toDetailEntity(RoutineDetailRequestDTO dto);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "exercise", ignore = true)
	void updateDetailEntityFromDto(RoutineDetailRequestDTO dto, @MappingTarget RoutineDetail entity);

	PartnerRoutinesResponseDTO toRoutinesResponseDTO(Partner partner);

	PartnerRoutinesResponseDTO.RoutineInfo toRoutineInfo(Routine routine);

	PartnerRoutinesResponseDTO.DetailInfo toDetailInfo(RoutineDetail routineDetail);

	PartnerRoutinesResponseDTO.ExerciseInfo toExerciseInfo(Exercise exercise);
}
