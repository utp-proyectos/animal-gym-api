package pe.edu.utp.animal_gym_api.domain.routine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.utp.animal_gym_api.common.response.ApiResponse;
import pe.edu.utp.animal_gym_api.domain.routine.dto.RoutineResponseDTO;
import pe.edu.utp.animal_gym_api.domain.routine.service.RoutineService;

@RestController
@RequestMapping("/api/routines")
public class RoutineController {

	@Autowired
	RoutineService routineService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<RoutineResponseDTO>>> findAll() {
		List<RoutineResponseDTO> routines = routineService.findAll();
		return ResponseEntity.ok(ApiResponse.ok("Routines retrieved successfully", routines));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<RoutineResponseDTO>> findById(@PathVariable Long id) {
		RoutineResponseDTO routine = routineService.findById(id);
		return ResponseEntity.ok(ApiResponse.ok("Routine found", routine));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<RoutineResponseDTO>> save(@RequestBody Routine routine) {
		RoutineResponseDTO savedRoutine = routineService.save(routine);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.ok("Routine created successfully", savedRoutine));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<RoutineResponseDTO>> update(
			@PathVariable Long id,
			@RequestBody Routine routine) {
		routine.setId(id);
		RoutineResponseDTO updatedRoutine = routineService.save(routine);
		return ResponseEntity.ok(ApiResponse.ok("Routine updated successfully", updatedRoutine));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable Long id) {
		routineService.deleteById(id);
		return ResponseEntity.ok(ApiResponse.ok("Routine deleted successfully", null));
	}

	@PostMapping("/{routineId}/details")
	public ResponseEntity<ApiResponse<RoutineResponseDTO>> addRoutineDetail(
			@PathVariable Long routineId,
			@RequestBody RoutineDetail detail) {
		RoutineResponseDTO updatedRoutine = routineService.addRoutineDetail(routineId, detail);
		return ResponseEntity.ok(ApiResponse.ok("Detail added successfully", updatedRoutine));
	}

	@DeleteMapping("/{routineId}/details/{detailId}")
	public ResponseEntity<ApiResponse<RoutineResponseDTO>> removeRoutineDetail(
			@PathVariable Long routineId,
			@PathVariable Long detailId) {
		RoutineResponseDTO updatedRoutine = routineService.removeRoutineDetail(routineId, detailId);
		return ResponseEntity.ok(ApiResponse.ok("Detail removed successfully", updatedRoutine));
	}
}
