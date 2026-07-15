package pe.edu.utp.animal_gym_api.domain.routine;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pe.edu.utp.animal_gym_api.common.response.ApiResponse;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRoutinesResponseDTO;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineDetailRequestDTO;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineRequestDTO;
import pe.edu.utp.animal_gym_api.domain.routine.service.RoutineService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routines")
public class RoutineController {

	private final RoutineService routineService;

	@PostMapping
	public ResponseEntity<ApiResponse<PartnerRoutinesResponseDTO>> save(
			@Valid @RequestBody RoutineRequestDTO requestDTO) {

		PartnerRoutinesResponseDTO updatedPartnerRoutines = routineService.save(requestDTO);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.ok("Rutina creada y asignada al socio con éxito", updatedPartnerRoutines));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<PartnerRoutinesResponseDTO>> update(
			@PathVariable Long id,
			@Valid @RequestBody RoutineRequestDTO requestDTO) {
		PartnerRoutinesResponseDTO updatedPartnerRoutines = routineService.update(id, requestDTO);

		return ResponseEntity.ok(
				ApiResponse.ok("Rutina actualizada con éxito", updatedPartnerRoutines));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<PartnerRoutinesResponseDTO>> delete(
			@PathVariable Long id,
			@RequestParam Long partnerId) {
		PartnerRoutinesResponseDTO updatedPartnerRoutines = routineService.delete(partnerId, id);

		return ResponseEntity.ok(
				ApiResponse.ok("Rutina removida del socio con éxito", updatedPartnerRoutines));
	}

	@PostMapping("/details")
	public ResponseEntity<ApiResponse<PartnerRoutinesResponseDTO>> saveDetail(
			@Valid @RequestBody RoutineDetailRequestDTO detailRequestDTO) {

		PartnerRoutinesResponseDTO updatedPartnerRoutines = routineService.saveDetail(detailRequestDTO);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.ok("Ejercicio agregado a la rutina con éxito", updatedPartnerRoutines));
	}

	@PutMapping("/details/{detailId}")
	public ResponseEntity<ApiResponse<PartnerRoutinesResponseDTO>> updateDetail(
			@PathVariable Long detailId,
			@Valid @RequestBody RoutineDetailRequestDTO detailRequestDTO) {

		PartnerRoutinesResponseDTO updatedPartnerRoutines = routineService.updateDetail(detailId, detailRequestDTO);

		return ResponseEntity.ok(
				ApiResponse.ok("Detalle de ejercicio actualizado con éxito", updatedPartnerRoutines));
	}

	@DeleteMapping("/{routineId}/details/{detailId}")
	public ResponseEntity<ApiResponse<PartnerRoutinesResponseDTO>> deleteDetail(
			@PathVariable Long routineId,
			@PathVariable Long detailId,
			@RequestParam Long partnerId) {

		PartnerRoutinesResponseDTO updatedPartnerRoutines = routineService.deleteDetail(partnerId, routineId, detailId);

		return ResponseEntity.ok(
				ApiResponse.ok("Ejercicio removido de la rutina con éxito", updatedPartnerRoutines));
	}

}
