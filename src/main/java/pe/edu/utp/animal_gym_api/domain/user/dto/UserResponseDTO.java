package pe.edu.utp.animal_gym_api.domain.user.dto;

import javax.management.relation.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserResponseDTO {
	private Long id;
	private Role role;
}
