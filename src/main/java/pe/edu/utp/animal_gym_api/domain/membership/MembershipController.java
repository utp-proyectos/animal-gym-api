package pe.edu.utp.animal_gym_api.domain.membership;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pe.edu.utp.animal_gym_api.common.response.ApiResponse;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipResponseDTO;
import pe.edu.utp.animal_gym_api.domain.membership.service.MembershipService;

@RestController
@RequestMapping("/api/memberships")
@RequiredArgsConstructor
public class MembershipController {

	private final MembershipService membershipService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<MembershipResponseDTO>>> findAll() {
		return ResponseEntity.ok(ApiResponse.ok(membershipService.findAll()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<MembershipResponseDTO>> findById(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.ok(membershipService.findById(id)));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<MembershipResponseDTO>> create(
			@RequestBody MembershipRequestDTO requestDTO) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.ok("Membership created successfully",
						membershipService.create(requestDTO)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<MembershipResponseDTO>> update(
			@PathVariable Long id, @RequestBody MembershipRequestDTO requestDTO) {
		return ResponseEntity.ok(ApiResponse.ok("Membership updated successfully",
				membershipService.update(id, requestDTO)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
		membershipService.delete(id);
		return ResponseEntity.ok(ApiResponse.ok("Membership deleted successfully", null));
	}

	@GetMapping("/filter")
	public ResponseEntity<ApiResponse<List<MembershipResponseDTO>>> findByStatus(
			@RequestParam Boolean status) {
		return ResponseEntity.ok(ApiResponse.ok(membershipService.findByStatus(status)));
	}

	@GetMapping("/filter/price")
	public ResponseEntity<ApiResponse<List<MembershipResponseDTO>>> findByPriceRange(
			@RequestParam Double min, @RequestParam Double max) {
		return ResponseEntity.ok(ApiResponse.ok(membershipService.findByPriceRange(min, max)));
	}

	@GetMapping("/available")
	public ResponseEntity<ApiResponse<List<MembershipResponseDTO>>> findWithAvailableCapacity() {
		return ResponseEntity.ok(ApiResponse.ok(membershipService.findWithAvailableCapacity()));
	}
}