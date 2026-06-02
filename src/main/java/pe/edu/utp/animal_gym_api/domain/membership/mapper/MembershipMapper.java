package pe.edu.utp.animal_gym_api.domain.membership.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import pe.edu.utp.animal_gym_api.domain.membership.Membership;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipResponseDTO;

@Mapper(componentModel = "spring")
public interface MembershipMapper {

	Membership toEntity(MembershipRequestDTO requestDTO);

	MembershipResponseDTO toResponseDTO(Membership membership);

	void updateEntityFromDTO(MembershipRequestDTO requestDTO, @MappingTarget Membership membership);
}
