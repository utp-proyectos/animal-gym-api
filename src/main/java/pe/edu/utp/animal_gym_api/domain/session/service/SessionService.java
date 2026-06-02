package pe.edu.utp.animal_gym_api.domain.session.service;

import java.util.List;
import java.util.Optional;

import pe.edu.utp.animal_gym_api.domain.session.Session;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionCardDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionDetailDTO;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBooking;

public interface SessionService {
	List<SessionCardDTO> findAll(Long currentPartnerId);

	SessionDetailDTO findById(Long id, Long currentPartnerId);

	SessionDetailDTO save(Session session);

	void deleteById(Long id);

	SessionDetailDTO addBooking(Long sessionId, SessionBooking booking);

	SessionDetailDTO removeBooking(Long sessionId, Long bookingId);
}
