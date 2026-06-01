package pe.edu.utp.animal_gym_api.domain.bill;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.utp.animal_gym_api.domain.bill.dto.BillRequestDTO;
import pe.edu.utp.animal_gym_api.domain.bill.dto.BillResponseDTO;
import pe.edu.utp.animal_gym_api.domain.bill.service.BillPdfService;
import pe.edu.utp.animal_gym_api.domain.bill.service.BillService;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor

public class BillController {

	private final BillService billService;
	private final BillPdfService billPdfService;

	@GetMapping
	public ResponseEntity<List<BillResponseDTO>> findAll() {
		return ResponseEntity.ok(billService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BillResponseDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(billService.findById(id));
	}

	@PostMapping
	public ResponseEntity<BillResponseDTO> save(@RequestBody BillRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(billService.save(dto));
	}

	@GetMapping("/{id}/pdf")
	public ResponseEntity<byte[]> generatePdf(@PathVariable Long id) throws Exception {
		BillResponseDTO bill = billService.findById(id);
		byte[] pdf = billPdfService.generatePdf(bill);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=boleta-" + id + ".pdf")
				.contentType(MediaType.APPLICATION_PDF)
				.body(pdf);
	}
}
