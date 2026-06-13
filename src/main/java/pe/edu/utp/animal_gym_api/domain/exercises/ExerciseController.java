package pe.edu.utp.animal_gym_api.domain.exercises;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.utp.animal_gym_api.common.response.ApiResponse;
import pe.edu.utp.animal_gym_api.domain.exercises.service.ExerciseService;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

	@Autowired
	private ExerciseService exerciseService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<Exercise>>> findAll() {
		return ResponseEntity.ok(ApiResponse.ok(exerciseService.findAll()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Exercise>> findById(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponse.ok(exerciseService.findById(id)));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Exercise>> create(@ModelAttribute Exercise exercise) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.ok("Exercise created successfully", exerciseService.create(exercise)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Exercise>> update(@PathVariable Long id,
			@ModelAttribute Exercise exercise) {
		return ResponseEntity.ok(ApiResponse.ok("Exercise updated successfully",
				exerciseService.update(id, exercise)));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
		exerciseService.delete(id);
		return ResponseEntity.ok(ApiResponse.ok("Exercise deleted successfully", null));
	}

	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<Exercise>>> search(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String muscleGroup,
			@RequestParam(required = false) String equipment) {
		return ResponseEntity.ok(ApiResponse.ok(exerciseService.search(name, muscleGroup, equipment)));
	}
}
