package pe.edu.utp.animal_gym_api.domain.membership.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipAssignmentRequestDTO {
	@NotBlank(message = "El DNI del socio es requerido")
	@Pattern(regexp = "\\d{8}", message = "El DNI debe tener exactamente 8 dígitos")
	private String partnerDni;

	@NotBlank(message = "El método de pago es requerido")
	@Pattern(regexp = "EFECTIVO|TARJETA|YAPE|PLIN", message = "El método de pago no es válido")
	private String paymentMethod;
}
