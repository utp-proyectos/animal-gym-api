package pe.edu.utp.animal_gym_api.domain.partner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import pe.edu.utp.animal_gym_api.common.response.ApiResponse;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerDetailDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRequestDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerResponseDTO;
import pe.edu.utp.animal_gym_api.domain.partner.service.PartnerService;

@RestController
@RequestMapping("/api/partners")
public class PartnerController {

	@Autowired
	private PartnerService partnerService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<PartnerResponseDTO>>> findAll() {
		return ResponseEntity.ok(ApiResponse.ok(partnerService.findAll()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<PartnerResponseDTO>> findById(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.ok(partnerService.findById(id)));
	}

	@GetMapping("/{id}/detail")
	public ResponseEntity<ApiResponse<PartnerDetailDTO>> findDetailById(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.ok(partnerService.findDetailById(id)));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<PartnerResponseDTO>> create(
			@RequestBody PartnerRequestDTO requestDTO) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.ok("Partner created successfully",
						partnerService.create(requestDTO)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<PartnerResponseDTO>> update(
			@PathVariable Long id, @RequestBody PartnerRequestDTO requestDTO) {
		return ResponseEntity.ok(ApiResponse.ok("Partner updated successfully",
				partnerService.update(id, requestDTO)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
		partnerService.delete(id);
		return ResponseEntity.ok(ApiResponse.ok("Partner deleted successfully", null));
	}

	// POST /api/partners/{id}/avatar (multipart/form-data, campo: file)
	@PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ApiResponse<String>> uploadAvatar(
			@PathVariable Long id,
			@RequestParam MultipartFile file) throws IOException {
		String url = partnerService.updateAvatar(id, file);
		return ResponseEntity.ok(ApiResponse.ok("Avatar uploaded successfully", url));
	}

	// GET /api/partners/filter?status=true
	@GetMapping("/filter")
	public ResponseEntity<ApiResponse<List<PartnerResponseDTO>>> findByStatus(
			@RequestParam Boolean status) {
		return ResponseEntity.ok(ApiResponse.ok(partnerService.findByStatus(status)));
	}

	// GET /api/partners/filter/expiration?start=2025-01-01&end=2025-12-31
	@GetMapping("/filter/expiration")
	public ResponseEntity<ApiResponse<List<PartnerResponseDTO>>> findByExpirationDateBetween(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
		return ResponseEntity.ok(
				ApiResponse.ok(partnerService.findByExpirationDateBetween(start, end)));
	}

	// GET /api/partners/filter/membership?membershipId=1
	@GetMapping("/filter/membership")
	public ResponseEntity<ApiResponse<List<PartnerResponseDTO>>> findByMembershipId(
			@RequestParam Long membershipId) {
		return ResponseEntity.ok(ApiResponse.ok(partnerService.findByMembershipId(membershipId)));
	}

	// GET /api/partners/search?name=juan
	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<PartnerResponseDTO>>> findByName(
			@RequestParam String name) {
		return ResponseEntity.ok(ApiResponse.ok(partnerService.findByName(name)));
	}
}