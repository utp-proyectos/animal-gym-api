package pe.edu.utp.animal_gym_api.domain.partner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import pe.edu.utp.animal_gym_api.domain.partner.Partner;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRequestDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerResponseDTO;

@Mapper(componentModel = "spring")
public interface PartnerMapper {

	@Mapping(target = "firstName", source = "name")
	@Mapping(target = "phoneNumber", source = "phone")
	@Mapping(target = "hireDate", source = "joinDate")
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "membership", ignore = true)
	Partner toEntity(PartnerRequestDTO requestDTO);

	@Mapping(target = "membershipId", source = "membership.id")
	@Mapping(target = "membershipName", source = "membership.name")
	PartnerResponseDTO toResponseDTO(Partner partner);

	@Mapping(target = "password", ignore = true)
	@Mapping(target = "role", ignore = true)
	void updateEntityFromDTO(PartnerRequestDTO requestDTO, @MappingTarget Partner partner);
}
