package pe.edu.utp.animal_gym_api.domain.membership.service;

import java.io.IOException;
import java.util.List;

import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipResponseDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipAssignmentRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipAssignmentResponseDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipPurchasePreviewDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipPurchaseRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipPurchaseResponseDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipSelfResponseDTO;

public interface MembershipService {
	List<MembershipResponseDTO> findAll();

	MembershipResponseDTO findById(Long id);

	MembershipResponseDTO create(MembershipRequestDTO requestDTO) throws IOException;

	MembershipResponseDTO update(Long id, MembershipRequestDTO requestDTO) throws IOException;

	void delete(Long id);

	List<MembershipResponseDTO> findByStatus(Boolean status);

	List<MembershipResponseDTO> findByPriceRange(Double minPrice, Double maxPrice);

	List<MembershipResponseDTO> findWithAvailableCapacity();

	MembershipAssignmentResponseDTO assignToPartner(
			Long membershipId, MembershipAssignmentRequestDTO requestDTO, String operatorDni);

	MembershipPurchasePreviewDTO previewAssignment(Long membershipId, String partnerDni);

	MembershipSelfResponseDTO findCurrentForPartner(String partnerDni);

	MembershipPurchasePreviewDTO previewPurchase(Long membershipId, String partnerDni);

	MembershipPurchaseResponseDTO purchaseForCurrentPartner(
			Long membershipId, MembershipPurchaseRequestDTO requestDTO, String partnerDni);
}
