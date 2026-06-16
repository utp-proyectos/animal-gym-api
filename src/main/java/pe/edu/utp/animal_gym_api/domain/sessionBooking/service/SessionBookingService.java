package pe.edu.utp.animal_gym_api.domain.sessionBooking.service;

import java.util.List;

import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBooking;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.dto.PartnerEnrolledResponseDTO;

public interface SessionBookingService {
	List<SessionBooking> findByPartnerId(Long partnerId);

	List<PartnerEnrolledResponseDTO> getEnrolledPartnersBySessionId(Long sessionId);

	void addBooking(Long sessionId, String dni);

	void subscribe(Long partnerId, Long sessionId);

	void cancel(Long partnerId, Long sessionId);
}
