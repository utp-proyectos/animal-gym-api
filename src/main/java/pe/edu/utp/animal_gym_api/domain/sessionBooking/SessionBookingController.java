package pe.edu.utp.animal_gym_api.domain.sessionBooking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.utp.animal_gym_api.common.response.ApiResponse;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.dto.PartnerEnrolledRequestDTO;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.dto.PartnerEnrolledResponseDTO;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.service.SessionBookingService;

@RestController
@RequestMapping("/api/bookings")
public class SessionBookingController {

	@Autowired
	private SessionBookingService sessionBookingService;

	@GetMapping("/partner/{partnerId}")
	public ResponseEntity<ApiResponse<List<SessionBooking>>> findByPartnerId(@PathVariable Long partnerId) {
		List<SessionBooking> bookings = sessionBookingService.findByPartnerId(partnerId);
		return ResponseEntity.ok(ApiResponse.ok("Partner bookings retrieved successfully", bookings));
	}

	@GetMapping("/session/{sessionId}")
	public ResponseEntity<ApiResponse<List<PartnerEnrolledResponseDTO>>> getEnrolledPartners(
			@PathVariable Long sessionId) {
		List<PartnerEnrolledResponseDTO> enrolled = sessionBookingService.getEnrolledPartnersBySessionId(sessionId);
		return ResponseEntity.ok(ApiResponse.ok("Enrolled partners retrieved successfully", enrolled));
	}

	@PostMapping("/session/{sessionId}")
	public ResponseEntity<ApiResponse<String>> addBooking(
			@PathVariable Long sessionId,
			@RequestBody PartnerEnrolledRequestDTO request) {

		sessionBookingService.addBooking(sessionId, request.getDni());

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.ok("Socio inscrito exitosamente en la sesión."));
	}

	@DeleteMapping("/session/{sessionId}/booking/{bookingId}")
	public ResponseEntity<ApiResponse<String>> removeBooking(
			@PathVariable Long sessionId,
			@PathVariable Long bookingId) {
		sessionBookingService.removeBooking(sessionId, bookingId);
		return ResponseEntity.ok(ApiResponse.ok("Socio dado de baja exitosamente de la sesión."));
	}

	@PostMapping("/subscribe")
	public ResponseEntity<ApiResponse<Void>> subscribe(
			@RequestParam Long partnerId,
			@RequestParam Long sessionId) {

		sessionBookingService.subscribe(partnerId, sessionId);
		return ResponseEntity.ok(ApiResponse.ok("Successfully subscribed to the session", null));
	}

	@DeleteMapping("/cancel")
	public ResponseEntity<ApiResponse<Void>> cancel(
			@RequestParam Long partnerId,
			@RequestParam Long sessionId) {

		sessionBookingService.cancel(partnerId, sessionId);
		return ResponseEntity.ok(ApiResponse.ok("Subscription cancelled successfully", null));
	}
}
