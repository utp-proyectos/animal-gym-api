package pe.edu.utp.animal_gym_api.domain.sessionBooking.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;
import pe.edu.utp.animal_gym_api.domain.partner.PartnerRepository;
import pe.edu.utp.animal_gym_api.domain.session.Session;
import pe.edu.utp.animal_gym_api.domain.session.SessionRepository;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.dto.PartnerEnrolledResponseDTO;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBooking;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBookingMapper;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBookingRepository;

@Service
public class SessionBookingServiceImpl implements SessionBookingService {

	@Autowired
	SessionBookingRepository sessionBookingRepository;

	@Autowired
	SessionRepository sessionRepository;

	@Autowired
	PartnerRepository partnerRepository;

	@Autowired
	SessionBookingMapper sessionBookingMapper;

	@Override
	public List<SessionBooking> findByPartnerId(Long partnerId) {
		if (!partnerRepository.existsById(partnerId)) {
			throw new EntityNotFoundException("Partner not found with ID: " + partnerId);
		}
		return sessionBookingRepository.findByPartner_Id(partnerId);
	}

	@Override
	public List<PartnerEnrolledResponseDTO> getEnrolledPartnersBySessionId(Long sessionId) {
		if (!sessionRepository.existsById(sessionId)) {
			throw new EntityNotFoundException("Session not found with ID: " + sessionId);
		}

		List<SessionBooking> bookings = sessionBookingRepository.findBySession_IdWithPartner(sessionId);

		return sessionBookingMapper.toEnrolledResponseList(bookings);
	}

	@Override
	@Transactional
	public void addBooking(Long sessionId, String dni) {
		Session session = findEntityById(sessionId, sessionRepository, "Sesión");
		Partner partner = partnerRepository.findByDni(dni)
				.orElseThrow(() -> new EntityNotFoundException("No existe ningún socio registrado con el DNI: " + dni));

		if (session.getBookings().size() >= session.getCapacity()) {
			throw new DataIntegrityViolationException("La sesión ha alcanzado su capacidad máxima.");
		}

		if (isPartnerEnrolled(session, partner.getId())) {
			throw new DataIntegrityViolationException("El socio ya se encuentra inscrito en esta sesión.");
		}

		SessionBooking newBooking = new SessionBooking();
		newBooking.setSession(session);
		newBooking.setPartner(partner);
		newBooking.setDate(LocalDate.now());

		session.getBookings().add(newBooking);
		sessionBookingRepository.save(newBooking);
	}

	@Override
	@Transactional
	public void removeBooking(Long sessionId, Long bookingId) {
		if (!sessionRepository.existsById(sessionId)) {
			throw new EntityNotFoundException("Sesión no encontrada con ID: " + sessionId);
		}

		SessionBooking booking = findEntityById(bookingId, sessionBookingRepository, "Reserva");

		if (!booking.getSession().getId().equals(sessionId)) {
			throw new DataIntegrityViolationException("La reserva no corresponde a la sesión especificada.");
		}

		sessionBookingRepository.delete(booking);
	}

	private Boolean isPartnerEnrolled(Session session, Long partnerId) {
		if (partnerId == null || session.getBookings() == null)
			return false;
		return session.getBookings().stream()
				.anyMatch(b -> b.getPartner() != null && b.getPartner().getId().equals(partnerId));
	}

	@Override
	@Transactional
	public void subscribe(Long partnerId, Long sessionId) {
		if (sessionBookingRepository.existsByPartner_IdAndSession_Id(partnerId, sessionId)) {
			throw new DataIntegrityViolationException("El socio ya está inscrito en esta sesión.");
		}

		Partner partner = findEntityById(partnerId, partnerRepository, "Socio");
		Session session = findEntityById(sessionId, sessionRepository, "Sesión");

		if (session.getBookings().size() >= session.getCapacity()) {
			throw new DataIntegrityViolationException("La sesión está llena.");
		}

		SessionBooking booking = new SessionBooking();
		booking.setPartner(partner);
		booking.setSession(session);
		booking.setDate(LocalDate.now());

		session.getBookings().add(booking);
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

	private <T> T findEntityById(Long id, CrudRepository<T, Long> repository, String entityName) {
		return repository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(entityName + " no encontrado con ID: " + id));
	}

}
