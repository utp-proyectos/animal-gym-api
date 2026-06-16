package pe.edu.utp.animal_gym_api.domain.session.service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.employee.EmployeeRepository;
import pe.edu.utp.animal_gym_api.domain.partner.PartnerRepository;
import pe.edu.utp.animal_gym_api.domain.session.Session;
import pe.edu.utp.animal_gym_api.domain.session.SessionMapper;
import pe.edu.utp.animal_gym_api.domain.session.SessionRepository;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionCardDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionRequestDTO;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBookingRepository;
import pe.edu.utp.animal_gym_api.domain.storage.StorageService;

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

	@Autowired
	PartnerRepository partnerRepository;

	@Autowired
	StorageService storageService;

	@Override
	public List<SessionCardDTO> findAll(Long currentPartnerId) {
		return sessionRepository.findAll().stream()
				.map(session -> {
					SessionCardDTO dto = sessionMapper.toCardDTO(session);
					dto.setStatus(determineStatus(session.getDate(), session.getStartTime(), session.getEndTime()));
					dto.setEnrolled(isPartnerEnrolled(session, currentPartnerId));
					dto.setBookingsCount(session.getBookings().size());
					return dto;
				})
				.collect(Collectors.toList());
	}

	@Override
	public SessionCardDTO findById(Long id, Long currentPartnerId) {
		return sessionRepository.findById(id)
				.map(session -> {
					SessionCardDTO dto = sessionMapper.toCardDTO(session);
					dto.setStatus(determineStatus(session.getDate(), session.getStartTime(), session.getEndTime()));
					dto.setEnrolled(isPartnerEnrolled(session, currentPartnerId));
					dto.setBookingsCount(session.getBookings().size());
					return dto;
				})
				.orElseThrow(() -> new EntityNotFoundException("No se encontró la clase con el ID: " + id));
	}

	@Override
	public SessionCardDTO save(SessionRequestDTO dto) throws IOException {
		Session session = sessionMapper.toEntity(dto);

		if (session.getEmployee() != null && session.getEmployee().getId() != null) {
			Employee employee = employeeRepository.findById(session.getEmployee().getId())
					.orElseThrow(() -> new EntityNotFoundException("Employee not found"));
			session.setEmployee(employee);
		}

		session.setDuration(calculateDuration(dto));
		session.setStatus("PROGRAMADO");

		if (dto.getImage() != null && !dto.getImage().isEmpty()) {
			String imagePath = storageService.upload(dto.getImage(), "sessions");
			session.setImage(imagePath);
		} else {
			session.setImage("../../resource/img/default.png");
		}

		Session savedSession = sessionRepository.save(session);

		SessionCardDTO resultDto = sessionMapper.toCardDTO(savedSession);
		resultDto
				.setStatus(determineStatus(savedSession.getDate(), savedSession.getStartTime(), savedSession.getEndTime()));

		return resultDto;
	}

	@Override
	public SessionCardDTO update(Long id, SessionRequestDTO dto) throws IOException {
		Session existingSession = sessionRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Session not found"));

		Session sessionUpdates = sessionMapper.toEntity(dto);
		sessionUpdates.setId(id);

		if (sessionUpdates.getEmployee() != null && sessionUpdates.getEmployee().getId() != null) {
			Employee employee = employeeRepository.findById(sessionUpdates.getEmployee().getId())
					.orElseThrow(() -> new EntityNotFoundException("Employee not found"));
			sessionUpdates.setEmployee(employee);
		} else {
			sessionUpdates.setEmployee(null);
		}

		sessionUpdates.setDuration(calculateDuration(dto));
		sessionUpdates.setStatus("PROGRAMADO");

		if (dto.getImage() != null && !dto.getImage().isEmpty()) {
			String imagePath = storageService.upload(dto.getImage(), "sessions");
			sessionUpdates.setImage(imagePath);
		} else {
			sessionUpdates.setImage(existingSession.getImage());
		}

		sessionUpdates.setBookings(existingSession.getBookings());
		Session updatedSession = sessionRepository.save(sessionUpdates);
		SessionCardDTO resultDto = sessionMapper.toCardDTO(updatedSession);

		resultDto.setStatus(
				determineStatus(updatedSession.getDate(), updatedSession.getStartTime(), updatedSession.getEndTime()));

		return resultDto;
	}

	@Override
	public void deleteById(Long id) {
		if (!sessionRepository.existsById(id)) {
			throw new EntityNotFoundException("No se puede eliminar: No existe la sesión con ID: " + id);
		}
		sessionRepository.deleteById(id);
	}

	private Boolean isPartnerEnrolled(Session session, Long partnerId) {
		if (partnerId == null || session.getBookings() == null)
			return false;
		return session.getBookings().stream()
				.anyMatch(b -> b.getPartner() != null && b.getPartner().getId().equals(partnerId));
	}

	private int calculateDuration(SessionRequestDTO dto) {
		if (dto.getStartTime() == null || dto.getEndTime() == null) {
			return 60;
		}

		long minutes = Duration.between(dto.getStartTime(), dto.getEndTime()).toMinutes();
		return minutes > 0 ? (int) minutes : 60;
	}

	private String determineStatus(LocalDate date, LocalTime startTime, LocalTime endTime) {
		if (date == null || startTime == null || endTime == null) {
			return "PROGRAMADO";
		}

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime sessionStart = LocalDateTime.of(date, startTime);
		LocalDateTime sessionEnd = LocalDateTime.of(date, endTime);

		if (now.isAfter(sessionEnd)) {
			return "FINALIZADO";
		} else if (now.isBefore(sessionStart)) {
			return "PROGRAMADO";
		} else {
			return "ACTIVO";
		}
	}
}
