package pe.edu.utp.animal_gym_api.domain.partner.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerDetailDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRequestDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerResponseDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRoutinesResponseDTO;
import pe.edu.utp.animal_gym_api.domain.partner.service.dto.PersonProfileRequest;

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

	PartnerRoutinesResponseDTO findRoutinesByPartnerId(Long id);

	List<PartnerRoutinesResponseDTO> findAllWithRoutines();

	String updateAvatar(Long id, MultipartFile file) throws IOException;

	PartnerResponseDTO updateProfile(Long id, PersonProfileRequest request);
}
