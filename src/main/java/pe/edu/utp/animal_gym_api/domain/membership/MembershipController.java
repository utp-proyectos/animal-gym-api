package pe.edu.utp.animal_gym_api.domain.membership;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import pe.edu.utp.animal_gym_api.common.response.ApiResponse;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipAssignmentRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipAssignmentResponseDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipPurchasePreviewDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipPurchaseRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipPurchaseResponseDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipResponseDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipSelfResponseDTO;
import pe.edu.utp.animal_gym_api.domain.membership.service.MembershipService;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

	@Autowired
	private MembershipService membershipService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<MembershipResponseDTO>>> findAll() {
		return ResponseEntity.ok(ApiResponse.ok(membershipService.findAll()));
	}

	@GetMapping("/me")
	@PreAuthorize("hasRole('SOCIO')")
	public ResponseEntity<ApiResponse<MembershipSelfResponseDTO>> findMyMembership(
			java.security.Principal principal) {
		return ResponseEntity.ok(ApiResponse.ok(membershipService.findCurrentForPartner(principal.getName())));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<MembershipResponseDTO>> findById(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.ok(membershipService.findById(id)));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<MembershipResponseDTO>> create(
			@Valid @ModelAttribute MembershipRequestDTO requestDTO) throws IOException {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.ok("Membership created successfully",
						membershipService.create(requestDTO)));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<MembershipResponseDTO>> update(
			@PathVariable Long id, @Valid @ModelAttribute MembershipRequestDTO requestDTO) throws IOException {
		return ResponseEntity.ok(ApiResponse.ok("Membership updated successfully",
				membershipService.update(id, requestDTO)));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
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

	@GetMapping("/{id}/purchase-preview")
	@PreAuthorize("hasRole('SOCIO')")
	public ResponseEntity<ApiResponse<MembershipPurchasePreviewDTO>> previewPurchase(
			@PathVariable Long id,
			java.security.Principal principal) {
		return ResponseEntity.ok(ApiResponse.ok(membershipService.previewPurchase(id, principal.getName())));
	}

	@PostMapping("/{id}/assign")
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<MembershipAssignmentResponseDTO>> assignToPartner(
			@PathVariable Long id,
			@Valid @RequestBody MembershipAssignmentRequestDTO requestDTO,
			java.security.Principal principal) {
		return ResponseEntity.ok(ApiResponse.ok("Membresía asignada y pago registrado correctamente",
				membershipService.assignToPartner(id, requestDTO, principal.getName())));
	}

	@GetMapping("/{id}/assignment-preview")
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<MembershipPurchasePreviewDTO>> previewAssignment(
			@PathVariable Long id,
			@RequestParam String partnerDni) {
		return ResponseEntity.ok(ApiResponse.ok(membershipService.previewAssignment(id, partnerDni)));
	}

	@PostMapping("/{id}/purchase")
	@PreAuthorize("hasRole('SOCIO')")
	public ResponseEntity<ApiResponse<MembershipPurchaseResponseDTO>> purchaseForCurrentPartner(
			@PathVariable Long id,
			@Valid @RequestBody MembershipPurchaseRequestDTO requestDTO,
			java.security.Principal principal) {
		return ResponseEntity.ok(ApiResponse.ok("Compra de membresía registrada correctamente",
				membershipService.purchaseForCurrentPartner(id, requestDTO, principal.getName())));
	}
}
