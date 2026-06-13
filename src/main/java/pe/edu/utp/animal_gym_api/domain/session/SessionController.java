package pe.edu.utp.animal_gym_api.domain.session;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.utp.animal_gym_api.common.response.ApiResponse;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionCardDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionDetailDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionRequestDTO;
import pe.edu.utp.animal_gym_api.domain.session.service.SessionService;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBooking;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

	@Autowired
	private SessionService sessionService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<SessionCardDTO>>> findAll(
			@RequestParam(required = false) Long partnerId) {
		List<SessionCardDTO> sessions = sessionService.findAll(partnerId);
		return ResponseEntity.ok(ApiResponse.ok("Sessions retrieved successfully", sessions));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<SessionDetailDTO>> findById(
			@PathVariable Long id,
			@RequestParam(required = false) Long partnerId) {
		SessionDetailDTO session = sessionService.findById(id, partnerId);
		return ResponseEntity.ok(ApiResponse.ok("Session found", session));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<SessionDetailDTO>> save(@ModelAttribute SessionRequestDTO dto) throws IOException {
		SessionDetailDTO savedSession = sessionService.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.ok("Session created successfully", savedSession));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<SessionDetailDTO>> update(
			@PathVariable Long id,
			@ModelAttribute SessionRequestDTO dto) throws IOException {
		return ResponseEntity.ok(ApiResponse.ok("Session updated successfully", sessionService.update(id, dto)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id) {
		sessionService.deleteById(id);
		return ResponseEntity.ok(ApiResponse.ok("Session deleted successfully", null));
	}

	/* Bookings (Reservas) */

	@PostMapping("/{sessionId}/bookings")
	public ResponseEntity<ApiResponse<SessionDetailDTO>> addBooking(
			@PathVariable Long sessionId,
			@RequestBody SessionBooking booking) {
		SessionDetailDTO updatedSession = sessionService.addBooking(sessionId, booking);
		return ResponseEntity.ok(ApiResponse.ok("Booking added successfully", updatedSession));
	}

	@DeleteMapping("/{sessionId}/bookings/{bookingId}")
	public ResponseEntity<ApiResponse<SessionDetailDTO>> removeBooking(
			@PathVariable Long sessionId,
			@PathVariable Long bookingId) {
		SessionDetailDTO updatedSession = sessionService.removeBooking(sessionId, bookingId);
		return ResponseEntity.ok(ApiResponse.ok("Booking removed successfully", updatedSession));
	}
}
