package pe.edu.utp.animal_gym_api.domain.membership.service;

import java.io.IOException;
import java.util.List;

import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipResponseDTO;

public interface MembershipService {
	List<MembershipResponseDTO> findAll();

	MembershipResponseDTO findById(Long id);

	MembershipResponseDTO create(MembershipRequestDTO requestDTO) throws IOException;

	MembershipResponseDTO update(Long id, MembershipRequestDTO requestDTO) throws IOException;

	void delete(Long id);

	List<MembershipResponseDTO> findByStatus(Boolean status);

	List<MembershipResponseDTO> findByPriceRange(Double minPrice, Double maxPrice);

	List<MembershipResponseDTO> findWithAvailableCapacity();
}