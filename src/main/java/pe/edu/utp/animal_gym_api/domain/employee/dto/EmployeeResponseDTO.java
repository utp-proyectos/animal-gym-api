package pe.edu.utp.animal_gym_api.domain.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.animal_gym_api.common.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeResponseDTO {
	private Long id;
	private String firstName;
	private String lastName;
	private String image;
	private Role role;
}
