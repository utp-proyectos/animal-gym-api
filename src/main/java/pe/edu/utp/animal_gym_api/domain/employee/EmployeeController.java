package pe.edu.utp.animal_gym_api.domain.employee;

import java.util.List;

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

import lombok.RequiredArgsConstructor;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDetailDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeUser;
import pe.edu.utp.animal_gym_api.domain.employee.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor

public class EmployeeController {

	private final EmployeeService employeeService;

	@GetMapping
	public ResponseEntity<List<EmployeeResponseDTO>> findAll() {
		return ResponseEntity.ok(employeeService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponseDetailDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(employeeService.findById(id));
	}

	@PostMapping
	public ResponseEntity<EmployeeResponseDTO> save(@RequestBody EmployeeUser dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.save(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeResponseDTO> update(@PathVariable Long id, @RequestBody EmployeeUser dto) {
		return ResponseEntity.ok(employeeService.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		employeeService.delete(id);
		return ResponseEntity.noContent().build();
	}
}