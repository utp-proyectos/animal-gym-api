package pe.edu.utp.animal_gym_api.domain.session.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.employee.EmployeeRepository;
import pe.edu.utp.animal_gym_api.domain.session.Session;
import pe.edu.utp.animal_gym_api.domain.session.SessionMapper;
import pe.edu.utp.animal_gym_api.domain.session.SessionRepository;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionCardDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionDetailDTO;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBooking;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBookingRepository;

@Service
public class SessionServiceImpl implements SessionService {

	@Autowired
	SessionRepository sessionRepository;

	@Autowired
	SessionBookingRepository sessionBookingRepository;

	@Autowired
	SessionMapper sessionMapper;

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public List<SessionCardDTO> findAll(Long currentPartnerId) {
		return sessionRepository.findAll().stream()
				.map(session -> {
					SessionCardDTO dto = sessionMapper.toCardDTO(session);
					dto.setEnrolled(isPartnerEnrolled(session, currentPartnerId));
					return dto;
				})
				.collect(Collectors.toList());
	}

	@Override
	public SessionDetailDTO findById(Long id, Long currentPartnerId) {
		return sessionRepository.findById(id)
				.map(session -> {
					SessionDetailDTO dto = sessionMapper.toDetailDTO(session);
					dto.setEnrolled(isPartnerEnrolled(session, currentPartnerId));
					return dto;
				})
				.orElseThrow(() -> new EntityNotFoundException("No se encontró la clase con el ID: " + id));
	}

	@Override
	public SessionDetailDTO save(Session session) {
		if (session.getEmployee() != null && session.getEmployee().getId() != null) {
			Employee employee = employeeRepository.findById(session.getEmployee().getId())
					.orElseThrow(() -> new EntityNotFoundException("Employee not found"));
			session.setEmployee(employee);
		}

		// Si es una actualización, rescatar datos que no vienen en el objeto
		if (session.getId() != null) {
			Session existingSession = sessionRepository.findById(session.getId())
					.orElseThrow(() -> new EntityNotFoundException("Session not found"));

			if (session.getBookings() == null || session.getBookings().isEmpty()) {
				session.setBookings(existingSession.getBookings());
			}
		}

		Session savedSession = sessionRepository.save(session);
		return sessionMapper.toDetailDTO(savedSession);
	}

	@Override
	public void deleteById(Long id) {
		if (!sessionRepository.existsById(id)) {
			throw new EntityNotFoundException("No se puede eliminar: No existe la sesión con ID: " + id);
		}
		sessionRepository.deleteById(id);
	}

	@Override
	@Transactional
	public SessionDetailDTO addBooking(Long sessionId, SessionBooking booking) {
		Session session = sessionRepository.findById(sessionId)
				.orElseThrow(() -> new RuntimeException("Session not found"));

		if (session.getBookings().size() >= session.getCapacity()) {
			throw new RuntimeException("Session is full");
		}

		SessionBooking newBooking = sessionBookingRepository.save(booking);
		session.getBookings().add(newBooking);
		Session updatedSession = sessionRepository.save(session);

		SessionDetailDTO dto = sessionMapper.toDetailDTO(updatedSession);
		dto.setEnrolled(true);
		return dto;
	}

	@Override
	@Transactional
	public SessionDetailDTO removeBooking(Long sessionId, Long bookingId) {
		Session session = sessionRepository.findById(sessionId)
				.orElseThrow(() -> new RuntimeException("Session not found"));

		// Buscamos la reserva en la lista de la sesión
		boolean removed = session.getBookings().removeIf(b -> b.getId().equals(bookingId));

		if (!removed) {
			throw new RuntimeException("Booking not found in this session");
		}

		Session updatedSession = sessionRepository.save(session);
		SessionDetailDTO dto = sessionMapper.toDetailDTO(updatedSession);
		dto.setEnrolled(false);
		return dto;
	}

	private Boolean isPartnerEnrolled(Session session, Long partnerId) {
		if (partnerId == null || session.getBookings() == null)
			return false;
		return session.getBookings().stream()
				.anyMatch(b -> b.getPartner() != null && b.getPartner().getId().equals(partnerId));
	}
}
