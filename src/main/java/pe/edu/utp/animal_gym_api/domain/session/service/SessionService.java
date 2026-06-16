package pe.edu.utp.animal_gym_api.domain.session.service;

import java.io.IOException;
import java.util.List;

import pe.edu.utp.animal_gym_api.domain.session.dto.SessionCardDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionRequestDTO;

public interface SessionService {
	List<SessionCardDTO> findAll(Long currentPartnerId);

	SessionCardDTO findById(Long id, Long currentPartnerId);

	SessionCardDTO save(SessionRequestDTO dto) throws IOException;

	SessionCardDTO update(Long id, SessionRequestDTO dto) throws IOException;

	void deleteById(Long id);
}
