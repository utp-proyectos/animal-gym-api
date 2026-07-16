package pe.edu.utp.animal_gym_api.domain.membership.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipPurchaseRequestDTO {
	@Pattern(regexp = "EFECTIVO|TARJETA|YAPE|PLIN", message = "El método de pago no es válido")
	private String paymentMethod;
}
