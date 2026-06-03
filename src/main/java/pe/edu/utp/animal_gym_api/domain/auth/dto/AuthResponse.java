package pe.edu.utp.animal_gym_api.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.animal_gym_api.common.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
	private String token;
	private Long id;
	private String dni;
	private String firstName;
	private String lastName;
	private String email;
	private String avatar;
	private Role role;
}
