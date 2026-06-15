package pe.edu.utp.animal_gym_api.domain.employee;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.utp.animal_gym_api.common.response.ApiResponse;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDetailDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeUser;
import pe.edu.utp.animal_gym_api.domain.employee.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<EmployeeResponseDetailDTO>>> findAll() {
		List<EmployeeResponseDetailDTO> employees = employeeService.findAll();
		return ResponseEntity.ok(ApiResponse.ok("Employees retrieved successfully", employees));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<EmployeeResponseDetailDTO>> findById(@PathVariable Long id) {
		EmployeeResponseDetailDTO employee = employeeService.findById(id);
		return ResponseEntity.ok(ApiResponse.ok("Employee found", employee));
	}

	@PostMapping
	public ResponseEntity<ApiResponse<EmployeeResponseDetailDTO>> save(
			@ModelAttribute EmployeeUser dto) throws IOException {

		EmployeeResponseDetailDTO saved = employeeService.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponse.ok("Employee created successfully", saved));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<EmployeeResponseDetailDTO>> update(
			@PathVariable Long id,
			@ModelAttribute EmployeeUser dto) throws IOException {
		EmployeeResponseDetailDTO updated = employeeService.update(id, dto);
		return ResponseEntity.ok(ApiResponse.ok("Employee updated successfully", updated));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
		employeeService.delete(id);
		return ResponseEntity.ok(ApiResponse.ok("Employee deleted successfully", null));
	}
}
