package pe.edu.utp.animal_gym_api.domain.partner.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import pe.edu.utp.animal_gym_api.domain.partner.Partner;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRequestDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerResponseDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerDetailDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface PartnerMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "membership", ignore = true)
	@Mapping(target = "routines", ignore = true)
	Partner toEntity(PartnerRequestDTO requestDTO);

	@Mapping(target = "membershipId", source = "membership.id")
	@Mapping(target = "membershipName", source = "membership.name")
	PartnerResponseDTO toResponseDTO(Partner partner);

	@Mapping(target = "membershipId", source = "membership.id")
	@Mapping(target = "membershipName", source = "membership.name")
	@Mapping(target = "routines", source = "routines")
	PartnerDetailDTO toDetailDTO(Partner partner);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "membership", ignore = true)
	@Mapping(target = "routines", ignore = true)
	@Mapping(target = "avatar", ignore = true)
	@Mapping(target = "expirationDate", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "points", ignore = true)
	@Mapping(target = "role", ignore = true)
	void updateEntityFromDTO(PartnerRequestDTO requestDTO, @MappingTarget Partner partner);
}
