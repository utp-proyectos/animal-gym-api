package pe.edu.utp.animal_gym_api.domain.bill;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.utp.animal_gym_api.common.response.ApiResponse;
import pe.edu.utp.animal_gym_api.domain.bill.dto.BillRequestDTO;
import pe.edu.utp.animal_gym_api.domain.bill.dto.BillResponseDTO;
import pe.edu.utp.animal_gym_api.domain.bill.service.BillPdfService;
import pe.edu.utp.animal_gym_api.domain.bill.service.BillService;

@RestController
@RequestMapping("/api/bills")
public class BillController {

	@Autowired
	private BillService billService;

	@Autowired
	private BillPdfService billPdfService;

	@PreAuthorize("hasAnyRole('ADMIN', 'SOCIO', 'RECEPCIONISTA')")
	@GetMapping
	public ResponseEntity<ApiResponse<List<BillResponseDTO>>> findAll(Authentication authentication) {
		List<BillResponseDTO> bills = billService.findAll(authentication);
		return ResponseEntity.ok(ApiResponse.ok("Bills retrieved successfully", bills));
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'SOCIO' , 'RECEPCIONISTA')")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<BillResponseDTO>> findById(@PathVariable Long id, Authentication authentication) {
		BillResponseDTO bill = billService.findById(id, authentication);
		return ResponseEntity.ok(ApiResponse.ok("Bill found", bill));
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'RECEPCIONISTA')")
	@PostMapping
	public ResponseEntity<ApiResponse<BillResponseDTO>> save(@RequestBody BillRequestDTO dto) {
		BillResponseDTO saved = billService.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.ok("Bill created successfully", saved));
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'SOCIO' , 'RECEPCIONISTA')")
	@GetMapping("/{id}/pdf")
	public ResponseEntity<byte[]> generatePdf(@PathVariable Long id, Authentication authentication) throws Exception {
		BillResponseDTO bill = billService.findById(id, authentication);
		byte[] pdf = billPdfService.generatePdf(bill);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=boleta-" + id + ".pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(pdf);
	}
}
