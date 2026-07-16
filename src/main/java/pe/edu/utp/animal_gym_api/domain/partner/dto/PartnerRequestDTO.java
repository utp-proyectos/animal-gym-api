package pe.edu.utp.animal_gym_api.domain.partner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.utp.animal_gym_api.common.enums.Role;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartnerRequestDTO {
	@NotBlank(message = "El DNI es requerido")
	@Pattern(regexp = "\\d{8}", message = "El DNI debe tener exactamente 8 dígitos")
	private String dni;

	@NotBlank(message = "El nombre es requerido")
	@Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
	private String firstName;

	@NotBlank(message = "El apellido es requerido")
	@Size(max = 100, message = "El apellido no puede superar los 100 caracteres")
	private String lastName;

	@NotBlank(message = "El teléfono es requerido")
	@Pattern(regexp = "\\d{9}", message = "El teléfono debe tener exactamente 9 dígitos")
	private String phoneNumber;

	@NotBlank(message = "El género es requerido")
	private String gender;

	@NotBlank(message = "El correo es requerido")
	@Email(message = "El correo no tiene un formato válido")
	private String email;

	@NotNull(message = "La fecha de nacimiento es requerida")
	@Past(message = "La fecha de nacimiento debe ser anterior a hoy")
	private LocalDate birthDate;

	@NotNull(message = "La fecha de ingreso es requerida")
	@PastOrPresent(message = "La fecha de ingreso no puede ser futura")
	private LocalDate hireDate;
	private LocalDate expirationDate;
	private Boolean status;
	private Integer points;

	@Positive(message = "El peso debe ser mayor a cero")
	private Double weight;

	@Positive(message = "La altura debe ser mayor a cero")
	private Double height;
	private String image;
	private Long membershipId;
	private String password;
	private Role role;
}
