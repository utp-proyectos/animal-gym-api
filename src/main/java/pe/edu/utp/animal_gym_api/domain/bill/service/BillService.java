package pe.edu.utp.animal_gym_api.domain.bill.service;

import java.util.List;

import pe.edu.utp.animal_gym_api.domain.bill.dto.BillRequestDTO;
import pe.edu.utp.animal_gym_api.domain.bill.dto.BillResponseDTO;

public interface BillService {
	List<BillResponseDTO> findAll();

	BillResponseDTO findById(Long id);

	BillResponseDTO save(BillRequestDTO dto);
}
