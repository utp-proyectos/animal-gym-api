package pe.edu.utp.animal_gym_api.domain.membership.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import pe.edu.utp.animal_gym_api.domain.membership.Membership;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipResponseDTO;

@Mapper(componentModel = "spring")
public interface MembershipMapper {

	@Mapping(target = "active", ignore = true)
	@Mapping(target = "expired", ignore = true)
	@Mapping(target = "remainingDays", ignore = true)
	@Mapping(target = "enrolledMembers", ignore = true)
	@Mapping(target = "id", ignore = true)
	Membership toEntity(MembershipRequestDTO requestDTO);

	@Mapping(target = "active", ignore = true)
	@Mapping(target = "expired", ignore = true)
	@Mapping(target = "remainingDays", ignore = true)
	@Mapping(target = "enrolledMembers", ignore = true)
	MembershipResponseDTO toResponseDTO(Membership membership);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "active", ignore = true)
	@Mapping(target = "expired", ignore = true)
	@Mapping(target = "remainingDays", ignore = true)
	@Mapping(target = "enrolledMembers", ignore = true)
	void updateEntityFromDTO(MembershipRequestDTO requestDTO, @MappingTarget Membership membership);
}
