package pe.edu.utp.animal_gym_api.domain.partner.service;

import java.time.LocalDate;
import java.util.List;

import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerDetailDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRequestDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerResponseDTO;

public interface PartnerService {
	List<PartnerResponseDTO> findAll();

	PartnerResponseDTO findById(Long id);

	PartnerDetailDTO findDetailById(Long id);

	PartnerResponseDTO create(PartnerRequestDTO requestDTO);

	PartnerResponseDTO update(Long id, PartnerRequestDTO requestDTO);

	void delete(Long id);

	List<PartnerResponseDTO> findByStatus(Boolean status);

	List<PartnerResponseDTO> findByExpirationDateBetween(LocalDate start, LocalDate end);

	List<PartnerResponseDTO> findByMembershipId(Long membershipId);

	List<PartnerResponseDTO> findByName(String name);
}
