package pe.edu.utp.animal_gym_api.domain.bill.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import pe.edu.utp.animal_gym_api.domain.bill.dto.BillRequestDTO;
import pe.edu.utp.animal_gym_api.domain.bill.dto.BillResponseDTO;

public interface BillService {
	List<BillResponseDTO> findAll(Authentication authentication);

	BillResponseDTO findById(Long id, Authentication authentication);

	BillResponseDTO save(BillRequestDTO dto);
}
