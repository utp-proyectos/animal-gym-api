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
	@Mapping(target = "avatar", source = "image")
	Partner toEntity(PartnerRequestDTO requestDTO);

	@Mapping(target = "membershipId", source = "membership.id")
	@Mapping(target = "membershipName", source = "membership.name")
	@Mapping(target = "image", source = "avatar")
	PartnerResponseDTO toResponseDTO(Partner partner);

	@Mapping(target = "membershipId", source = "membership.id")
	@Mapping(target = "membershipName", source = "membership.name")
	@Mapping(target = "routines", source = "routines")
	@Mapping(target = "image", source = "avatar")
	PartnerDetailDTO toDetailDTO(Partner partner);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "membership", ignore = true)
	@Mapping(target = "routines", ignore = true)
	@Mapping(target = "avatar", source = "image")
	@Mapping(target = "expirationDate", ignore = true)
	void updateEntityFromDTO(PartnerRequestDTO requestDTO, @MappingTarget Partner partner);
}
