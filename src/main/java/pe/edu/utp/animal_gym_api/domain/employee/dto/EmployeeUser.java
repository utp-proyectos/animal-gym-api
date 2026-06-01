package pe.edu.utp.animal_gym_api.domain.employee.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.animal_gym_api.common.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EmployeeUser {
	// datos de Employee
	private String dni;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String gender;
	private String email;
	private LocalDate birthDate;
	private LocalDate hireDate;
	private String image;
	private double salary;
	private String contractType;
	private String specialty;
	// datos de User
	private String password;
	private Role role;
}
