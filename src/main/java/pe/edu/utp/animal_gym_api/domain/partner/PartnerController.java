package pe.edu.utp.animal_gym_api.domain.partner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import pe.edu.utp.animal_gym_api.common.response.ApiResponse;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerDetailDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRequestDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerResponseDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRoutinesResponseDTO;
import pe.edu.utp.animal_gym_api.domain.partner.service.PartnerService;
import pe.edu.utp.animal_gym_api.domain.partner.service.dto.PersonProfileRequest;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

	@Autowired
	private PartnerService partnerService;

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<List<PartnerResponseDTO>>> findAll() {
		return ResponseEntity.ok(ApiResponse.ok(partnerService.findAll()));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<PartnerResponseDTO>> findById(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.ok(partnerService.findById(id)));
	}

	@GetMapping("/{id}/detail")
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<PartnerDetailDTO>> findDetailById(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.ok(partnerService.findDetailById(id)));
	}

	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<PartnerResponseDTO>> create(
			@Valid @RequestBody PartnerRequestDTO requestDTO) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.ok("Partner created successfully",
						partnerService.create(requestDTO)));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<PartnerResponseDTO>> update(
			@PathVariable Long id, @Valid @RequestBody PartnerRequestDTO requestDTO) {
		return ResponseEntity.ok(ApiResponse.ok("Partner updated successfully",
				partnerService.update(id, requestDTO)));
	}

	@PutMapping("/{id}/profile")
	public ResponseEntity<ApiResponse<PartnerResponseDTO>> updateProfile(
			@PathVariable Long id,
			@RequestBody PersonProfileRequest dto) {

		return ResponseEntity.ok(
				ApiResponse.ok(
						"Perfil actualizado",
						partnerService.updateProfile(id, dto)));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
		partnerService.delete(id);
		return ResponseEntity.ok(ApiResponse.ok("Partner deleted successfully", null));
	}

	// POST /api/partners/{id}/avatar (multipart/form-data, campo: file)
	@PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<String>> uploadAvatar(
			@PathVariable Long id,
			@RequestParam MultipartFile file) throws IOException {
		String url = partnerService.updateAvatar(id, file);
		return ResponseEntity.ok(ApiResponse.ok("Avatar uploaded successfully", url));
	}

	// GET /api/partners/filter?status=true
	@GetMapping("/filter")
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<List<PartnerResponseDTO>>> findByStatus(
			@RequestParam Boolean status) {
		return ResponseEntity.ok(ApiResponse.ok(partnerService.findByStatus(status)));
	}

	// GET /api/partners/filter/expiration?start=2025-01-01&end=2025-12-31
	@GetMapping("/filter/expiration")
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<List<PartnerResponseDTO>>> findByExpirationDateBetween(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
		return ResponseEntity.ok(
				ApiResponse.ok(partnerService.findByExpirationDateBetween(start, end)));
	}

	// GET /api/partners/filter/membership?membershipId=1
	@GetMapping("/filter/membership")
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<List<PartnerResponseDTO>>> findByMembershipId(
			@RequestParam Long membershipId) {
		return ResponseEntity.ok(ApiResponse.ok(partnerService.findByMembershipId(membershipId)));
	}

	// GET /api/partners/search?name=juan
	@GetMapping("/search")
	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	public ResponseEntity<ApiResponse<List<PartnerResponseDTO>>> findByName(
			@RequestParam String name) {
		return ResponseEntity.ok(ApiResponse.ok(partnerService.findByName(name)));
	}

	@GetMapping("/{id}/routines")
	public ResponseEntity<ApiResponse<PartnerRoutinesResponseDTO>> getPartnerRoutines(@PathVariable Long id) {
		PartnerRoutinesResponseDTO data = partnerService.findRoutinesByPartnerId(id);
		return ResponseEntity.ok(ApiResponse.ok("Partner routines retrieved successfully", data));
	}

	@GetMapping("/routines")
	public ResponseEntity<ApiResponse<List<PartnerRoutinesResponseDTO>>> getAllPartnersWithRoutines() {
		List<PartnerRoutinesResponseDTO> data = partnerService.findAllWithRoutines();
		return ResponseEntity.ok(ApiResponse.ok("All partners with routines retrieved successfully", data));
	}
}
