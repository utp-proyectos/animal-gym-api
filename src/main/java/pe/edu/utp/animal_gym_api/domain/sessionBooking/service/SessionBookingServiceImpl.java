package pe.edu.utp.animal_gym_api.domain.sessionBooking.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;
import pe.edu.utp.animal_gym_api.domain.partner.PartnerRepository;
import pe.edu.utp.animal_gym_api.domain.session.Session;
import pe.edu.utp.animal_gym_api.domain.session.SessionRepository;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBooking;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBookingRepository;

@Service
public class SessionBookingServiceImpl implements SessionBookingService {

	@Autowired
	SessionBookingRepository sessionBookingRepository;
	@Autowired
	SessionRepository sessionRepository;
	@Autowired
	PartnerRepository partnerRepository;

	@Override
	public List<SessionBooking> findByPartnerId(Long partnerId) {
		if (!partnerRepository.existsById(partnerId)) {
			throw new EntityNotFoundException("Partner not found with ID: " + partnerId);
		}
		return sessionBookingRepository.findByPartner_Id(partnerId);
	}

	@Override
	public void subscribe(Long partnerId, Long sessionId) {
		// Validar si ya existe la reserva
		if (sessionBookingRepository.existsByPartner_IdAndSession_Id(partnerId, sessionId)) {
			throw new DataIntegrityViolationException("Partner is already subscribed to this session.");
		}

		// Buscar entidades o lanzar 404 si no existen
		Partner partner = partnerRepository.findById(partnerId)
				.orElseThrow(() -> new EntityNotFoundException("Partner not found with ID: " + partnerId));

		Session session = sessionRepository.findById(sessionId)
				.orElseThrow(() -> new EntityNotFoundException("Session not found with ID: " + sessionId));

		// Validar capacidad de la sesión
		if (session.getBookings().size() >= session.getCapacity()) {
			throw new DataIntegrityViolationException("The session is full.");
		}

		SessionBooking booking = new SessionBooking();
		booking.setPartner(partner);
		booking.setSession(session);
		booking.setDate(LocalDate.now());

		sessionBookingRepository.save(booking);
	}

	@Override
	public void cancel(Long partnerId, Long sessionId) {
		// Validamos existencia antes de borrar
		if (!sessionBookingRepository.existsByPartner_IdAndSession_Id(partnerId, sessionId)) {
			throw new EntityNotFoundException("No booking found for this partner in the specified session.");
		}

		sessionBookingRepository.deleteByPartnerIdAndSessionId(partnerId, sessionId);
	}
}
