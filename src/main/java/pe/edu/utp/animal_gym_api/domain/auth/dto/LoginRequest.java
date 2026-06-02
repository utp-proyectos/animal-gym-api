package pe.edu.utp.animal_gym_api.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
	@Size(min = 8, max = 8, message = "DNI must be exactly 8 characters")
	@NotBlank(message = "DNI is required")
	@NotNull(message = "DNI cannot be null")
	private String dni;

	@NotBlank(message = "Password is required")
	@NotNull(message = "Password cannot be null")
	private String password;
}
