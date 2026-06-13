package pe.edu.utp.animal_gym_api.domain.session.service;

import java.io.IOException;
import java.util.List;

import pe.edu.utp.animal_gym_api.domain.session.dto.SessionCardDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionDetailDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionRequestDTO;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBooking;

public interface SessionService {
	List<SessionCardDTO> findAll(Long currentPartnerId);

	SessionDetailDTO findById(Long id, Long currentPartnerId);

	SessionDetailDTO save(SessionRequestDTO dto) throws IOException;

	SessionDetailDTO update(Long id, SessionRequestDTO dto) throws IOException;

	void deleteById(Long id);

	SessionDetailDTO addBooking(Long sessionId, SessionBooking booking);

	SessionDetailDTO removeBooking(Long sessionId, Long bookingId);
}
